package com.oauth.mode.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信配置属性
 * @author chenling
 * @date 2020/2/6 13:30
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeChatProperties extends SocialBasicProperties  {

    private String scope = "snsapi_login";

    private String  signUpUrl;


}
