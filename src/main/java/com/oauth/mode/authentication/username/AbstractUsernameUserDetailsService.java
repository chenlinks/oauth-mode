package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.AbstractUserDetailsService;
import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import com.oauth.mode.authentication.entity.MyUserDetails;

/**
 * @author chenling
 * @date 2020/2/13 16:48
 * @since V1.0.0
 */
public abstract class AbstractUsernameUserDetailsService<AuthenticationDetail extends AbstractMyAuthenticationDetails, UserDetail extends MyUserDetails> extends AbstractUserDetailsService {
}
