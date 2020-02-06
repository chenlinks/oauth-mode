package com.oauth.mode.security.authentication.social.wechat.api;

import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestOperations;

/**
 * 微信
 * @author chenling
 * @date 2020/2/6 15:23
 * @since V1.0.0
 */
public interface Wechat extends ApiBinding {

    UserOperations userOperations();

    RestOperations restOperations();
}
