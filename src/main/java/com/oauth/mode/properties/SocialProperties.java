package com.oauth.mode.properties;

import lombok.Data;

/**
 * 社交平台配置相关
 * @author chenling
 * @date 2020/2/6 13:30
 * @since V1.0.0
 */
@Data
public class SocialProperties {

    private WeChatProperties weChat;
    private QQProperties qq;
}
