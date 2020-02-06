package com.oauth.mode.security.authentication.social.wechat.connect;

import com.oauth.mode.security.authentication.social.wechat.api.UserOperations;
import com.oauth.mode.security.authentication.social.wechat.api.WeChatUserInfo;
import com.oauth.mode.security.authentication.social.wechat.api.Wechat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 是让API接口能够适配OAuth的标准验证流程。在ApiAdapter中，
 * 最为 重要的一个方法是 setConnectionValues，该方法期望客户端用自行实现的 API
 * 来填充Spring Social的 Connect对象
 * @author chenling
 * @date 2020/2/6 16:04
 * @since V1.0.0
 */
@Data
public class WechatAdapter  implements ApiAdapter<Wechat> {


    private String openId;
    private UserProfile userProfile;

    public WechatAdapter(String openId) {
        this.openId = openId;
    }


    @Override
    public boolean test(Wechat api) {
        return false;
    }


    /**
     * 通过API获取用户信息，并填充到Connection对象中
     * @param api
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(Wechat api, ConnectionValues connectionValues) {

        UserOperations userOperations = api.userOperations();
        WeChatUserInfo weChatUserInfo = userOperations.getUserProfile(openId);
        connectionValues.setDisplayName(weChatUserInfo.getNickname());
        connectionValues.setImageUrl(weChatUserInfo.getHeadimgurl());
        connectionValues.setProviderUserId(weChatUserInfo.getOpenid());

        if(this.userProfile == null) {
            this.userProfile = new UserProfile(openId, weChatUserInfo.getNickname(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        }
    }

    @Override
    public UserProfile fetchUserProfile(Wechat api) {
        if(this.userProfile == null) {
            WeChatUserInfo weChatUserInfo = api.userOperations().getUserProfile(openId);
            this.userProfile = new UserProfile(openId, weChatUserInfo.getNickname(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        }
        return this.userProfile;
    }

    @Override
    public void updateStatus(Wechat api, String message) {

    }
}
