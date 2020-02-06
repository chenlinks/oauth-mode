package com.oauth.mode.security.authentication.social.wechat.api.impl;

import com.oauth.mode.security.authentication.social.wechat.WechatErrorHandler;
import com.oauth.mode.security.authentication.social.wechat.WechatMappingJackson2HttpMessageConverter;
import com.oauth.mode.security.authentication.social.wechat.api.UserOperations;
import com.oauth.mode.security.authentication.social.wechat.api.UserTemplate;
import com.oauth.mode.security.authentication.social.wechat.api.Wechat;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * ApiBinding主要用于描述资源提供商提供的API列表
 *
 * @author chenling
 * @date 2020/2/6 15:26
 * @since V1.0.0
 */
public class WechatImpl extends AbstractOAuth2ApiBinding implements Wechat {

    private UserOperations userOperations;

    public WechatImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        userOperations = new UserTemplate(restOperations(), accessToken);
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public RestOperations restOperations() {
        return getRestTemplate();
    }

    @Override
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
    }

    @Override
    protected OAuth2Version getOAuth2Version() {
        return OAuth2Version.BEARER_DRAFT_2;
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new WechatErrorHandler());
        super.configureRestTemplate(restTemplate);
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(3);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new WechatMappingJackson2HttpMessageConverter());
        return converters;
    }

}
