package com.oauth.mode.constants;

/**
 * @author chenling
 * @date 2020/2/6 14:53
 * @since V1.0.0
 */
public class UrlConstants {

    //**************************微信相关URL begin ***************************************//

    /**
     * 检查access_token有效性
     */
    public static final String WECHAT_AUTH_API_URL = "https://api.weixin.qq.com/sns/auth";

    /**
     * 通过code换取access_token、refresh_token和已授权scope
     */
    public static final String WECHAT_ACCESS_TOKEN_API_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 刷新或续期access_token使用
     */
    public static final String WECHAT_REFRESH_TOKEN_API_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /**
     * 获取用户个人信息
     */
    public static final String WECHAT_USERINFO_API_URL = "https://api.weixin.qq.com/sns/userinfo";


    /**
     * 用户同意授权，获取code,微信快捷登录的页面
     */
    public static final String WECHAT_AUTHORIZE_API_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 请求CODE
     */
    public static final String WECHAT_QRCONNECT_API_URL = "https://open.weixin.qq.com/connect/qrconnect";

    //**************************微信相关URL end ***************************************//

}
