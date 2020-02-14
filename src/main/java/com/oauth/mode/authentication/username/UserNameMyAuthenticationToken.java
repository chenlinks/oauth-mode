package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author chenling
 * @date 2020/2/13 16:46
 * @since V1.0.0
 */
public class UserNameMyAuthenticationToken extends AbstractMyAuthenticationToken {

    public UserNameMyAuthenticationToken(Object authenticationDetails) {
        super(authenticationDetails);
    }

    public UserNameMyAuthenticationToken(UserDetails userDetails) {
        super(userDetails);
    }
}
