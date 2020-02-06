package com.oauth.mode.token;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt token 增强器
 * @author chenling
 * @date 2020/2/6 9:41
 * @since V1.0.0
 */
@Component
@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Authentication userAuthentication = authentication.getUserAuthentication();
        String name = userAuthentication.getName();
        if(StrUtil.isNotBlank(name)){
            final Map<String, Object> additionalInformation = new HashMap<>();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("name",name);
            userInfo.put("password","chenling");
            userInfo.put("sex","男");
            userInfo.put("age","27");
            additionalInformation.put("userInfo",userInfo);
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInformation);
        }

        return accessToken;
    }
}
