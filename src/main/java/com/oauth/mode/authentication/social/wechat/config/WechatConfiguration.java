package com.oauth.mode.authentication.social.wechat.config;

import com.oauth.mode.authentication.social.wechat.factory.WechatConnectionFactory;
import com.oauth.mode.properties.SecurityProperties;
import com.oauth.mode.properties.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * @author chenling
 * @date 2020/2/7 14:35
 * @since V1.0.0
 */
@Configuration
@Slf4j
@EnableSocial
public class WechatConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        WeChatProperties weChatProperties = securityProperties.getSocial().getWeChat();
        log.info("配置文件信息：{}",weChatProperties);
        WechatConnectionFactory wechatConnectionFactory = new WechatConnectionFactory(weChatProperties.getAppId(), weChatProperties.getAppSecret(),weChatProperties.getProviderId());
        wechatConnectionFactory.setScope(weChatProperties.getScope());

        //会根据加入的ConnectionFactory，加入ConnectionFactory Map集合中，key是ProviderId，后续生成 注册 SocialAuthenticationService 会从这个 map 中取值，在 SocialAuthenticationServiceRegistry 处理
        //这个很关键，他牵连后续你能不能根据URL {filterProcessesUrl}/{ProviderId} 访问服务
        connectionFactoryConfigurer.addConnectionFactory(wechatConnectionFactory);
    }
}
