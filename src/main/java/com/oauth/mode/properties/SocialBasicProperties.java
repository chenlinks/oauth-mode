package com.oauth.mode.properties;

import lombok.Data;

/**
 * 第三方社交账号基础信息
 * @author chenling
 * @date 2020/2/6 13:22
 * @since V1.0.0
 */
@Data
public abstract class SocialBasicProperties {

     /**
      * 提供商id，如 wechat
      */
    private String providerId;
    /**
     * app id
     */
    private String appId;
    /**
     * app secret
     */
    private String appSecret;
    /**
     * 向第三方请求 authorize code 的url
     */
    private String authorizeUrl;
    /**
     * 向第三方请求 access token 的url
     */
    private String accessTokenUrl;

    /**
     * 自定义配置客户端请求授权地址
     */
    private String filterProcessesUrl;


}
