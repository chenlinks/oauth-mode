package com.oauth.mode.security.authentication.social.wechat.template;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chenling
 * @date 2020/2/6 15:30
 * @since V1.0.0
 */
@AllArgsConstructor
@Data
public class AuthRequest {

    private String accessToken;

    private String openid;
}
