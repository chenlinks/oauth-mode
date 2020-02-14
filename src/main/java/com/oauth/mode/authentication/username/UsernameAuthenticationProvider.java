package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import com.oauth.mode.authentication.entity.AbstractMyAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author chenling
 * @date 2020/2/13 16:36
 * @since V1.0.0
 */
@Slf4j
@Component
public class UsernameAuthenticationProvider  implements  AuthenticationProvider {

    @Autowired
    private  AbstractUsernameUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("------AuthenticationProvider-------- :{}",authentication);
        AbstractMyAuthenticationDetails authenticationDetails = (AbstractMyAuthenticationDetails)authentication.getDetails();
        UserDetails userDetails = userDetailsService.loadUser(authenticationDetails);

        log.info("------Provider----userDetailsService-{}",userDetails);
        userDetailsService.checkUser(authenticationDetails, userDetails);
        return new UserNameMyAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AbstractMyAuthenticationToken.class.isAssignableFrom(authentication);

    }
}
