package com.oauth.mode.security.authentication.social.wechat.template;

import com.oauth.mode.security.authentication.social.wechat.WechatLangEnum;
import com.oauth.mode.security.authentication.social.wechat.model.WeChatUserInfo;

/**
 * @author chenling
 * @date 2020/2/6 15:24
 * @since V1.0.0
 */
public interface UserOperations {

    WeChatUserInfo getUserProfile(String openId);

    WeChatUserInfo getUserProfile(String openId, WechatLangEnum lang);

}
