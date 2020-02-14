package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenling
 * @date 2020/2/13 17:37
 * @since V1.0.0
 */
public interface UsernameAuthenticationDetailsSource<UsernameAuthenticationDetail extends AbstractMyAuthenticationDetails> extends AuthenticationDetailsSource<HttpServletRequest, UsernameAuthenticationDetail> {
}
