package com.oauth.mode.authentication.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author chenling
 * @date 2020/2/7 13:47
 * @since V1.0.0
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
