package com.oauth.mode.authentication.username;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
        log.info("-------------------获取用户参数-------------------");
        //, ToStringBuilder.reflectionToString(context.getParameterMap(), ToStringStyle.JSON_STYLE)
        UsernameAuthenticationDetails authenticationDetails = new UsernameAuthenticationDetails();

        String tenantId = context.getParameter("tenantId");
        String username = context.getParameter("username");
        String password = context.getParameter("password");
        String appId = context.getParameter("appId");
        String accountTypeId = context.getParameter("accountTypeId");
        String extraData = context.getParameter("extraData");
        authenticationDetails.setTenantId(tenantId);
        authenticationDetails.setAppId(appId);
        authenticationDetails.setAccountTypeId(accountTypeId);
        authenticationDetails.setUsername(username);
        authenticationDetails.setPassword(password);

        log.info("user-info -source {}",authenticationDetails);

        return authenticationDetails;
    }
}
