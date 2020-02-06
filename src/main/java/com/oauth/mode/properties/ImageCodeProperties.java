package com.oauth.mode.properties;

import lombok.Data;

/**
 * 图片验证码相关
 * @author chenling
 * @date 2020/2/6 13:27
 * @since V1.0.0
 */
@Data
public class ImageCodeProperties {

    /**
     * 验证码长度
     */
    private int length;
    /**
     * 图形宽度
     */
    private int width;
    /**
     * 图形高度
     */
    private int height;
    /**
     * 验证码过期时间
     */
    private long expireIn;

}
