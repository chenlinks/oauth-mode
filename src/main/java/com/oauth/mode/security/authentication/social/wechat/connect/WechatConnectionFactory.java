package com.oauth.mode.security.authentication.social.wechat.connect;

import com.oauth.mode.security.authentication.social.wechat.api.Wechat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 *。OAuth2ConnectionFactory 是Spring Social的连接工厂类，
 * 由两个关键部件组成：
 * ◎ ApiAdapter
 * ◎ OAuth2ServiceProvider
 * 实现与 OAuth 授权服务器的交互
 *
 * 每个已登录用户都会维护一个OAuth2Connection对象，OAuth2Connection通过构造函数初始化 accessToken和providerUserId等信息。
 * @author chenling
 * @date 2020/2/6 15:38
 * @since V1.0.0
 */
public class WechatConnectionFactory extends OAuth2ConnectionFactory<Wechat> {




    public WechatConnectionFactory(String appId, String appSecret,String providerId) {
        this(appId, appSecret, providerId, null);
    }

    public WechatConnectionFactory(String appId, String appSecret, String providerId,ApiAdapter<Wechat> apiAdapter) {
        super(providerId, new WechatServiceProvider<Wechat>(appId, appSecret), apiAdapter);
    }




    @Override
    public Connection<Wechat> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<Wechat>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<Wechat> createConnection(ConnectionData data) {
        return super.createConnection(data);
    }


    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WechatAccessGrant) {
            WechatAccessGrant weChatAccessGrant = (WechatAccessGrant)accessGrant;
            return weChatAccessGrant.getOpenid();
        }
        return StringUtils.EMPTY;
    }

    protected ApiAdapter<Wechat> getApiAdapter(String openId) {
        return new WechatAdapter(openId);
    }

    @Deprecated
    @Override
    protected ApiAdapter<Wechat> getApiAdapter() {
        return super.getApiAdapter();
    }

    private OAuth2ServiceProvider<Wechat> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Wechat>) getServiceProvider();
    }
}
