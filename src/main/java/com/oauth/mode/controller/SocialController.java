package com.oauth.mode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author chenling
 * @date 2020/2/6 23:54
 * @since V1.0.0
 */
@RestController
public class SocialController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    @GetMapping("/register")
    public String   socialRegister(ServletWebRequest request){
        //通过request 获取到connect
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(request);

        //执行 connect 进行持久化
        providerSignInUtils.doPostSignUp(connectionFromSession.getKey().getProviderUserId(),request);
        //执行绑定账号等其他逻辑
        //最后应该跳转页面
        return "redirect:/";
    }
}
