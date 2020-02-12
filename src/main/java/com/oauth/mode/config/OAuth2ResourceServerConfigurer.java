package com.oauth.mode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器
 * @author chenling
 * @date 2020/2/5 19:56
 * @since V1.0.0
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfigurer extends ResourceServerConfigurerAdapter {


    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;


    /**
     * 添加特定于资源服务器的属性（例如资源ID）。默认值适用于许多应用程序，但是您可能至少要更改资源ID。
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }


    /**
     * 使用此配置安全资源的访问规则。默认情况下，“ / oauth / **” 中的所有资源不受保护
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .antMatchers("/oauth/**","/auth/**")
                .permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/api/**")
                .and().csrf().disable()
                .apply(springSocialConfigurer);

    }
}
