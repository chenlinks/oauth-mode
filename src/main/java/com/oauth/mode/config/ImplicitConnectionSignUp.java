package com.oauth.mode.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 当用户通过未在系统中使用过的第三方账号进行登录时，则可以“静默注册”，实现 ConnectionSignUp接口
 * 当用户再次登录时便不再跳转到 signup页面，而是将 Connections 信息自动持久化到数据库中，
 * 并跳转回原访问页面
 * @author chenling
 * @date 2020/2/7 0:04
 * @since V1.0.0
 */
@Component
@Slf4j
public class ImplicitConnectionSignUp  implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        //用户openId
        log.info("系统静默注册用户数据");
        //在“静默注册”的同时生成用户记录，并赋予用户一个合适的 角色。
        User user = new User("chenling", "chenling", AuthorityUtils.createAuthorityList("admin"));
        //持久化用户数据，再返回用户Id
        log.info("用户数据：{}",user);
        //save(user);
        return connection.getKey().getProviderUserId();
    }
}
