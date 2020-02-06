package com.oauth.mode.security.authentication.social.wechat.connect;

import com.oauth.mode.constants.UrlConstants;
import com.oauth.mode.security.authentication.social.wechat.api.WechatMp;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author chenling
 * @date 2020/2/6 15:40
 * @since V1.0.0
 */
public class WechatMpConnectionFactory extends OAuth2ConnectionFactory<WechatMp> {

    public WechatMpConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    public WechatMpConnectionFactory(String appId, String appSecret, ApiAdapter<WechatMp> apiAdapter) {
        super("wechatmp", new WechatServiceProvider<WechatMp>(appId, appSecret, UrlConstants.WECHAT_AUTHORIZE_API_URL),
                apiAdapter);
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return ((WechatAccessGrant) accessGrant).getOpenid();
    }
}
