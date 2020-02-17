package com.oauth.mode.authentication.username.config;

import com.oauth.mode.authentication.username.UsernameAuthenticationDetailsSource;
import com.oauth.mode.authentication.username.UsernameAuthenticationFilter;
import com.oauth.mode.authentication.username.UsernameAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 *
 * 将自定义的登录操作加入过滤器链
 * @author chenling
 * @date 2020/2/13 16:30
 * @since V1.0.0
 */
@Component
public class UsernameSecurityConfigurer  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UsernameAuthenticationProvider usernameAuthenticationProvider;

    @Autowired
    private UsernameAuthenticationDetailsSource  usernameAuthenticationDetailsSource;

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;


    @Override
    public void configure(HttpSecurity builder) throws Exception {

        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        //自定义过滤器
        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter(authenticationManager, usernameAuthenticationDetailsSource,successHandler,failureHandler);
        builder.authenticationProvider(usernameAuthenticationProvider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        super.configure(builder);
    }
}
