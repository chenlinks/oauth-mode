package com.oauth.mode.authentication.social.wechat.template;

import com.oauth.mode.authentication.social.wechat.WechatLangEnum;
import com.oauth.mode.authentication.social.wechat.model.WeChatUserInfo;

/**
 * @author chenling
 * @date 2020/2/14 12:44
 * @since V1.0.0
 */
public interface UserOperations {

    WeChatUserInfo getUserProfile(String openId);

    WeChatUserInfo getUserProfile(String openId, WechatLangEnum lang);
}
