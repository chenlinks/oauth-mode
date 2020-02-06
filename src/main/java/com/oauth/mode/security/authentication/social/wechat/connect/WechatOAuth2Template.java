package com.oauth.mode.security.authentication.social.wechat.connect;

import com.oauth.mode.security.authentication.social.wechat.WechatErrorHandler;
import com.oauth.mode.security.authentication.social.wechat.WechatMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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
public class WechatOAuth2Template extends OAuth2Template {

    public WechatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    public WechatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl,
                                String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
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

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(3);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new WechatMappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new WechatErrorHandler());
        return restTemplate;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return replaceParamKey(super.buildAuthorizeUrl(parameters));
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        return replaceParamKey(super.buildAuthorizeUrl(grantType, parameters));
    }

    protected String replaceParamKey(String url) {
        return url.replace("client_id", "appid").replace("client_secret", "secret");
    }





}
