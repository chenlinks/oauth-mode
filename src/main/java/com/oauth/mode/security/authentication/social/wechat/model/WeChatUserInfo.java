package com.oauth.mode.security.authentication.social.wechat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 微信用户信息
 * @author chenling
 * @date 2020/2/6 15:25
 * @since V1.0.0
 */
@Data
@NoArgsConstructor
@ToString
public class WeChatUserInfo {

    private String openid;

    private String nickname;

    private Integer sex;

    private String language;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private List<String> privilege;

    private String unionid;
}
