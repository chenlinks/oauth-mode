package com.oauth.mode.authentication.social.wechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author chenling
 * @date 2020/2/14 12:45
 * @since V1.0.0
 */
@Data
@AllArgsConstructor
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
