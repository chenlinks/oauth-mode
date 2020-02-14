package com.oauth.mode.authentication.config;

import com.oauth.mode.properties.SecurityProperties;
import com.oauth.mode.authentication.social.SocialAuthenticationFilterPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

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
public class SocialConfig extends SocialConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

//    @Autoired
//    private wConnectionSignUp connectionSignUp;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;


    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        //声明一个基于内存的用户连接管理器
//        //TODO 基于内存的实现方案缺陷较 多，比如，Connections在服务重启之后便会丢失，或者当长久运行时会占用过多内存空间等
//        //建议采用JDBC 方式
//        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//        //使用隐式模式
//        if(this.connectionSignUp != null){
//            repository.setConnectionSignUp(this.connectionSignUp);
//        }
//        return repository;

        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        //如果不为空
//        if (connectionSignUp != null) {
//            repository.setConnectionSignUp(connectionSignUp);
//        }
        return repository;

    }


    //配置 spring  social 提供的 oauth 登录工具
    //ProviderSignInUtils是一个用于处理OAuth登录逻辑的工具，主要通过doPostSignUp执行Connection 持久化逻辑
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator){
        return new ProviderSignInUtils(factoryLocator,getUsersConnectionRepository(factoryLocator));
    }
}
