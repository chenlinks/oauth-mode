package com.oauth.mode.authentication.social.wechat.template;

import com.oauth.mode.authentication.social.wechat.WechatLangEnum;
import com.oauth.mode.authentication.social.wechat.model.WeChatUserInfo;
import com.oauth.mode.constants.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

/**
 * @author chenling
 * @date 2020/2/6 15:28
 * @since V1.0.0
 */
@AllArgsConstructor
public class UserTemplate  implements UserOperations {

    private RestOperations restOperations;

    private String accessToken;

    @Override
    public WeChatUserInfo getUserProfile(String openId) {
        return getUserProfile(openId, WechatLangEnum.EN);
    }

    @Override
    public WeChatUserInfo getUserProfile(String openId, WechatLangEnum lang) {
        Assert.notNull(openId, "The openId cannot be null");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(3);
        params.add("openid", openId);
        params.add("lang", lang.getValue());
        params.add("access_token", accessToken);
        return restOperations.postForObject(UrlConstants.WECHAT_USERINFO_API_URL, params, WeChatUserInfo.class);
    }

}
