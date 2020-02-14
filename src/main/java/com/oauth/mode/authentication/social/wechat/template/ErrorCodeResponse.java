package com.oauth.mode.authentication.social.wechat.template;

import lombok.Data;

/**
 * @author chenling
 * @date 2020/2/6 15:30
 * @since V1.0.0
 */
@Data
public class ErrorCodeResponse {

    private Integer errcode;

    private String errmsg;
}
