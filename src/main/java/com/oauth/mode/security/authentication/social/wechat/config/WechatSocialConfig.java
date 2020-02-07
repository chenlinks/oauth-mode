package com.oauth.mode.security.authentication.social.wechat.config;

import com.oauth.mode.properties.SecurityProperties;
import com.oauth.mode.properties.WeChatProperties;
import com.oauth.mode.security.authentication.social.wechat.connect.WechatConnectionFactory;
import com.oauth.mode.security.config.CommonSpringSocialConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * SocialConfiguration实际上是Spring Social的配置入口类，
 * 它与@EnableSocial注解配合，实现了对 Spring Social的基本配置。
 *
 * @author chenling
 * @date 2020/2/6 18:02
 * @since V1.0.0
 */
@Configuration
@EnableSocial
@Slf4j
public class WechatSocialConfig extends SocialConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        WeChatProperties properties = securityProperties.getSocial().getWeChat();
        log.info("配置文件信息：{}",properties);
        WechatConnectionFactory connectionFactory = new WechatConnectionFactory(properties.getAppId(), securityProperties.getSocial().getWeChat().getProviderId(),properties.getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //声明一个基于内存的用户连接管理器
        //TODO 基于内存的实现方案缺陷较 多，比如，Connections在服务重启之后便会丢失，或者当长久运行时会占用过多内存空间等
        //建议采用JDBC 方式
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        //使用隐式模式
        if(this.connectionSignUp != null){
            repository.setConnectionSignUp(this.connectionSignUp);
        }
        return repository;

    }

    //生命自定义的 SpringSocialConfigurer
    @Bean
    public SpringSocialConfigurer springSocialConfigurer(){
        return new CommonSpringSocialConfigurer(securityProperties.getSocial().getWeChat().getFilterProcessesUrl(),securityProperties.getSocial().getWeChat().getSignUpUrl());
    }

    //配置 spring  social 提供的 oauth 登录工具
    //ProviderSignInUtils是一个用于处理OAuth登录逻辑的工具，主要通过doPostSignUp执行Connection 持久化逻辑
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator){
        return new ProviderSignInUtils(factoryLocator,getUsersConnectionRepository(factoryLocator));
    }
}
