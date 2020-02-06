package com.oauth.mode.security.authentication.social.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenling
 * @date 2020/2/6 15:25
 * @since V1.0.0
 */
@AllArgsConstructor
public enum WechatLangEnum {

    ZH_CN("zh-CN"),

    EN("en");

    @Getter
    private String value;
}
