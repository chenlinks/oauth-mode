package com.oauth.mode.security.authentication.social.wechat;

import com.oauth.mode.security.authentication.social.wechat.api.WechatMp;
import com.oauth.mode.security.authentication.social.wechat.connect.WechatMpConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

/**
 * @author chenling
 * @date 2020/2/6 15:43
 * @since V1.0.0
 */
public class WechatMpAuthenticationService extends OAuth2AuthenticationService<WechatMp> {

    public WechatMpAuthenticationService(final String apiKey, final String appSecret) {
        super(new WechatMpConnectionFactory(apiKey, appSecret));
    }
}
