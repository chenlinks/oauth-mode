package com.oauth.mode.properties;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * OAuth2 相关信息的配置
 * @author chenling
 * @date 2020/2/6 13:29
 * @since V1.0.0
 */
@Data
public class OAuth2Properties {

    /**
     * 用于jwt令牌签名的key
     */
    private String jwtSigningKey;
    /**
     * 允许的认证类型
     */
    private List<String> authorizedGrantTypes = Lists.newArrayList();
    /**
     * access token 有效期（秒）
     */
    private int accessTokenValiditySeconds;
    /**
     * refresh token 有效期（秒）
     */
    private int refreshTokenValiditySeconds;
}
