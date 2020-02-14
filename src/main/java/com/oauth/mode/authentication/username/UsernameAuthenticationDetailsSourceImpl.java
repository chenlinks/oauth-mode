package com.oauth.mode.authentication.username;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author chenling
 * @date 2020/2/13 17:39
 * @since V1.0.0
 */
@Component
@Slf4j
public class UsernameAuthenticationDetailsSourceImpl   implements  UsernameAuthenticationDetailsSource<UsernameAuthenticationDetails> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UsernameAuthenticationDetails buildDetails(HttpServletRequest context) {
        log.info("-------------------获取用户参数---------{}----------", ToStringBuilder.reflectionToString(context.getParameterMap(), ToStringStyle.JSON_STYLE));
        UsernameAuthenticationDetails authenticationDetails = null;
        BufferedReader reader = null;
        try {
            reader = context.getReader();
            authenticationDetails = objectMapper.convertValue(reader, UsernameAuthenticationDetails.class);
        } catch (IOException e) {
            log.error("获取请求参数时异常:",e);
        }
        return authenticationDetails;
    }
}
