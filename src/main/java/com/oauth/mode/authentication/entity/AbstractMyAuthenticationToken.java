package com.oauth.mode.authentication.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author chenling
 * @date 2020/2/13 16:42
 * @since V1.0.0
 */
public  abstract  class AbstractMyAuthenticationToken extends AbstractAuthenticationToken {

    protected UserDetails userDetails;

    public AbstractMyAuthenticationToken(Object authenticationDetails) {
        super(null);
        setDetails(authenticationDetails);
        setAuthenticated(false);
    }

    public AbstractMyAuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

}
