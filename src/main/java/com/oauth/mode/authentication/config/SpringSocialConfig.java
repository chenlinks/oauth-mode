package com.oauth.mode.authentication.config;

import cn.hutool.core.util.StrUtil;
import com.oauth.mode.authentication.social.SocialAuthenticationProvider;
import com.oauth.mode.authentication.social.SocialAuthenticationFilterPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * 配置Spring Social的公共行为
 * 主要内容有：配置Spring Social过滤器（SocialAuthenticationFilter）关注的一些URL
 * 包括：
 * 1.访问哪些URL会触发 OAuth 逻辑
 * 2.触发 OAuth 逻辑之后的一些跳转
 *
 * 默认情况下，SocialAuthenticationFilter只关注/auth/{providerId}，当匹配正确时，会尝试获取
 * providerId，并执行一系列OAuth流程。如果OAuth认证后的用户在本地的用户数据库中无法找到，则 认为这是一个需要注册的新用户，默认会跳转到/signup这个路由下进行处理。
 *如果需要定制这些URL，则可以通过继承SpringSocialConfigurer来实现。
 * @author chenling
 * @date 2020/2/6 17:53
 * @since V1.0.0
 */
@Component
@Slf4j
public class SpringSocialConfig extends SpringSocialConfigurer {


    @Value("${demo.security.social.filterProcessesUrl}")
    private String filterProcessesUrl;

    @Value("${demo.security.social.signUpUrl}")
    private String signUpUrl;

    @Autowired
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private SocialAuthenticationProvider provider;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private SocialAuthenticationServiceLocator authServiceLocator;



    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter)super.postProcess(object);
        //为social 设置自定义URL
        if(StrUtil.isNotBlank(this.filterProcessesUrl)){
            socialAuthenticationFilter.setFilterProcessesUrl(this.filterProcessesUrl);
        }
        if(StrUtil.isNotBlank(this.signUpUrl)){
            socialAuthenticationFilter.setSignupUrl(this.signUpUrl);
        }
        if(null != socialAuthenticationFilterPostProcessor){
            socialAuthenticationFilterPostProcessor.process(socialAuthenticationFilter);
        }
        return (T) socialAuthenticationFilter;
    }


    /**
     * 可以配置一些关于 social 的一些自定义功能
     * 核心 是配置SocialAuthenticationFilter
     * 参考父类的配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //社交账号登陆认证过滤器
        SocialAuthenticationFilter filter = new SocialAuthenticationFilter(
                http.getSharedObject(AuthenticationManager.class),
                new AuthenticationNameUserIdSource(),
                usersConnectionRepository,
                authServiceLocator);

        RememberMeServices rememberMe = http.getSharedObject(RememberMeServices.class);
        if (rememberMe != null) {
            filter.setRememberMeServices(rememberMe);
        }

//        filter.setAuthenticationDetailsSource(authenticationDetailsSource);
//        filter.setAuthenticationSuccessHandler(successHandler);
//        filter.setAuthenticationFailureHandler(failureHandler);

        http.authenticationProvider(provider)
                .addFilterBefore(postProcess(filter), AbstractPreAuthenticatedProcessingFilter.class);
    }
}
