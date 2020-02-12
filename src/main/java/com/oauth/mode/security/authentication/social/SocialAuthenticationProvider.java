package com.oauth.mode.security.authentication.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author chenling
 * @date 2020/2/12 23:03
 * @since V1.0.0
 */
@Component
public class SocialAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //TODO 自定义实现登录部分
        SocialAuthenticationToken socialAuthenticationToken = (SocialAuthenticationToken) authentication;
//        AbstractAuthenticationDetails authenticationDetails = (AbstractAuthenticationDetails) authentication.getDetails();
        Connection<?> connection = socialAuthenticationToken.getConnection();
        String openId = connection.fetchUserProfile().getId();
//        authenticationDetails.getExtraData().put("openId", openId);
//        UserDetails userDetails = userDetailsService.loadUser(authenticationDetails);
        SocialUserDetails userDetails = userDetailsService.loadUserByUserId(authentication.getName());

//        userDetailsService.checkUser(authenticationDetails, userDetails);
        return new SocialAuthenticationToken(connection, userDetails, socialAuthenticationToken.getProviderAccountData(), userDetails.getAuthorities());
//          return null;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }

}