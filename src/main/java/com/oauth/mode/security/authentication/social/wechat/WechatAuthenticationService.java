package com.oauth.mode.security.authentication.social.wechat;

import com.oauth.mode.properties.WeChatProperties;
import com.oauth.mode.security.authentication.social.wechat.api.Wechat;
import com.oauth.mode.security.authentication.social.wechat.connect.WechatConnectionFactory;
import com.oauth.mode.utils.SpringContextUtil;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

/**
 *
 * OAuth2AuthenticationService 是 SocialAuthenticationService 接口的 OAuth 实现，
 * 主要围绕 OAuth2ConnectionFactory 来实现与 OAuth 授权服务器的交互。OAuth2ConnectionFactory
 * 是Spring Social的连接工厂类，由两个关键部件组成：
 * ◎ ApiAdapter
 * ◎ OAuth2ServiceProvider
 * OAuth2AuthenticationService的核心工作由getAuthToken方法完成
 * @author chenling
 * @date 2020/2/6 15:43
 * @since V1.0.0
 */
public class WechatAuthenticationService extends OAuth2AuthenticationService<Wechat> {

    public WechatAuthenticationService(final String apiKey, final String appSecret) {
        super(new WechatConnectionFactory(apiKey, appSecret,SpringContextUtil.getBean(WeChatProperties.class).getProviderId()));
    }

}
