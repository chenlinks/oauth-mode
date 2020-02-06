package com.oauth.mode.properties;

import lombok.Data;

/**
 * 短信验证码配置相关
 * @author chenling
 * @date 2020/2/6 13:28
 * @since V1.0.0
 */
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length;
    /**
     * 验证码过期时间
     */
    private long expireIn;
    /**
     * 消息中心 事件code
     */
    private String eventCode;
    /**
     * 消息中心 备注
     */
    private String remark;
    /**
     * 消息中心 推送方式 1即时推送   2定时推送
     */
    private Integer pushType;
    /**
     * 消息中心 回调地址
     */
    private String backUrl;
    /**
     * 消息中心 预留字段，业务系统使用
     */
    private String outId;
    /**
     * 消息中心 应用id
     */
    private String appId;
    /**
     * 消息中心 实例id
     */
    private String caseId;
    /**
     * 消息中心 租户id
     */
    private String tenantId;
    /**
     * 消息中心发送短信接口url
     */
    private String messageCenterUrl;
}
