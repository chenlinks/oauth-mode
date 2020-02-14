package com.oauth.mode.expection;

import org.springframework.security.core.AuthenticationException;

/**
 * @author chenling
 * @date 2020/2/13 16:54
 * @since V1.0.0
 */
public class MyAuthenticationException extends AuthenticationException {


    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
