package com.oauth.mode.security.authentication.social.wechat;

import lombok.Getter;
import org.springframework.social.oauth2.AccessGrant;

/**
 *
 * 对微信access_token信息的封装
 * 与标准的OAuth2协议不同，微信在获取access_token时会同时返回openId，
 * 并没有单独的通过accessToke换取openId的服务
 * 在此处继承标准AccessGrant（Spring提供的令牌封装类），添加openId字段
 * @date 2020/2/6 15:37
 * @since V1.0.0
 */
@Getter
public class WechatAccessGrant  extends AccessGrant {

    private static final long serialVersionUID = 1L;

    private String openid;

    private String unionid;

    /**
     *
     * @param accessToken token
     * @param scope scope
     * @param refreshToken refreshToken
     * @param expiresIn
     * @param openId
     * @param unionId
     */
    public WechatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openId,
                             String unionId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openId;
        this.unionid = unionId;
    }
}
