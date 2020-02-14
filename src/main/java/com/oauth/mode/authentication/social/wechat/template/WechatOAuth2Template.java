package com.oauth.mode.authentication.social.wechat.template;


import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.mode.authentication.social.wechat.WechatAccessGrant;
import com.oauth.mode.authentication.social.wechat.handler.WechatErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * OAuth2Template 的实现是通用的，如果 OAuth 授权服务器提供的交互方式与OAuth2Template的 不同，
 * 则可以通过继承OAuth2Template并覆写postForAccessGrant的方式来实现定制化的逻辑。
 *
 * OAuth2Template实现了OAuth2Operations接口，并定义了一系列与accessToken相关的逻辑。
 *
 * @author chenling
 * @date 2020/2/6 15:41
 * @since V1.0.0
 */
@Slf4j
public class WechatOAuth2Template extends OAuth2Template {


    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;


    public WechatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    public WechatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl,
                                String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }


    /**
     * 获取access_token
     *
     * @param authorizationCode
     * @param redirectUri
     * @param parameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
                                         MultiValueMap<String, String> parameters) {

        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid=" + clientId);
        accessTokenRequestUrl.append("&secret=" + clientSecret);
        accessTokenRequestUrl.append("&code=" + authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri=" + redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

    @SuppressWarnings("unchecked")
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

        log.info("获取access_token, 请求URL: " + accessTokenRequestUrl.toString());
        //发送获取token
        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
        log.info("获取access_token, 响应内容: " + response);

        Map<String, Object> result = null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误码时直接返回空
        if (StringUtils.isNotBlank(MapUtil.getStr(result, "errcode"))) {
            String errcode = MapUtil.getStr(result, "errcode");
            String errmsg = MapUtil.getStr(result, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:" + errcode + ", errmsg:" + errmsg);
        }

        //获取token的时候，会返回openid，保存
        WechatAccessGrant  accessToken = new WechatAccessGrant(
                MapUtil.getStr(result, "access_token"),
                MapUtil.getStr(result, "scope"),
                MapUtil.getStr(result, "refresh_token"),
                MapUtil.getLong(result, "expires_in"),
                MapUtil.getStr(result, "openid"),
                MapUtil.getStr(result, "unionid")
        );
        return accessToken;
    }



    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        if ("authorization_code".equals(parameters.getFirst("grant_type"))) {
            parameters.set("appid", parameters.getFirst("client_id"));
            parameters.remove("client_id");
            parameters.set("secret", parameters.getFirst("client_secret"));
            parameters.remove("client_secret");
        }
        return super.postForAccessGrant(accessTokenUrl, parameters);
    }

    @Override
    protected WechatAccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn,
                                                  Map<String, Object> response) {
        return new WechatAccessGrant(accessToken, scope, refreshToken, expiresIn, (String) response.get("openid"),
                (String) response.get("unionid"));
    }


    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setErrorHandler(new WechatErrorHandler());
        return restTemplate;
    }


    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        String authorizeUrl = super.buildAuthorizeUrl(parameters);
        log.info("授权码请求地址：{}",authorizeUrl);
        return replaceParamKey(super.buildAuthorizeUrl(parameters));
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        String authorizeUrl = super.buildAuthorizeUrl(grantType, parameters);
        log.info("授权码请求地址：{}",authorizeUrl);
        return replaceParamKey(authorizeUrl);
    }

    protected String replaceParamKey(String url) {
        return  url + "&appid=" + clientId + "&scope=snsapi_login";
    }
}
