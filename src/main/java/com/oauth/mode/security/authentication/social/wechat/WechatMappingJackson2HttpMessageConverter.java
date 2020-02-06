package com.oauth.mode.security.authentication.social.wechat;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenling
 * @date 2020/2/6 15:34
 * @since V1.0.0
 */
public class WechatMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public WechatMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>(1);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }

}
