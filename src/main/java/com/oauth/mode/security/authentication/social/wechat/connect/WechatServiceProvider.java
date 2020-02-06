package com.oauth.mode.security.authentication.social.wechat.connect;

import com.oauth.mode.constants.UrlConstants;
import com.oauth.mode.security.authentication.social.wechat.api.Wechat;
import com.oauth.mode.security.authentication.social.wechat.api.impl.WechatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.Assert;

/**
 *  OAuth2ServiceProvider  包含了一个 OAuth2Template 类和一个 ApiBinding 接口，
 *  OAuth2Template 定义了客户端如何向授权服务器换取 accessToken，以及实现自动刷新 accessToken的逻辑。
 *
 * SocialAuthenticationProvider实现了AuthenticationProvider 接口，遵守的是Spring Security的认证逻 辑。
 * 在Spring Security中，用户信息的交互是通过UserDetailsService实现的，Spring Social定义了一个类 似的接
 * 口SocialUserDetailsService，我们需要实现loadUserByUserId方法，从而加载到该OAuth用户的权 限等信息
 *
 * @author chenling
 * @date 2020/2/6 15:41
 * @since V1.0.0
 */
public class WechatServiceProvider <T extends Wechat> extends AbstractOAuth2ServiceProvider<T> {

    public WechatServiceProvider(String appId, String appSecret) {
        this(appId, appSecret, UrlConstants.WECHAT_QRCONNECT_API_URL);
    }

    public WechatServiceProvider(String appId, String appSecret, String authorizeUrl) {
        super(getOAuth2Template(appId, appSecret, authorizeUrl));
    }

    private static OAuth2Template getOAuth2Template(String appId, String appSecret, String authorizeUrl) {
        OAuth2Template oauth2Template = new WechatOAuth2Template(appId, appSecret, authorizeUrl,
                UrlConstants.WECHAT_ACCESS_TOKEN_API_URL);
        oauth2Template.setUseParametersForClientAuthentication(true);
        return oauth2Template;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getApi(String accessToken) {
        Assert.notNull(accessToken, "The accessToken cannot be null");
        return (T) new WechatImpl(accessToken);
    }

}
