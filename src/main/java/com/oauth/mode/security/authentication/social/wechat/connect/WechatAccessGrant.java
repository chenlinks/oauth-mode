package com.oauth.mode.security.authentication.social.wechat.connect;

import lombok.Getter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author chenling
 * @date 2020/2/6 15:37
 * @since V1.0.0
 */
@Getter
public class WechatAccessGrant  extends AccessGrant {

    private static final long serialVersionUID = 1L;

    private String openid;

    private String unionid;

    public WechatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openId,
                             String unionId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openId;
        this.unionid = unionId;
    }
}
