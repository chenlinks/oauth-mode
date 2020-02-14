package com.oauth.mode.authentication.social.wechat.template;

import lombok.Data;

/**
 * @author chenling
 * @date 2020/2/6 15:31
 * @since V1.0.0
 */
@Data
public class AccessTokenResponse {

    private String accessToken;

    private String expiresIn;

    private String refreshToken;

    private String openid;

    private String scope;

    private String unionid;
}
