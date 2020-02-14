package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import com.oauth.mode.authentication.entity.MyUserDetails;
import com.oauth.mode.expection.MyAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author chenling
 * @date 2020/2/13 17:06
 * @since V1.0.0
 */
@Slf4j
@Component
public class UserNameDetailServiceImpl extends  AbstractUsernameUserDetailsService <UsernameAuthenticationDetails,MyUserDetails> {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUser(AbstractMyAuthenticationDetails authenticationDetails) throws MyAuthenticationException {

        log.info("自定义 username  登录：{}",authenticationDetails);
        //测试写死
        MyUserDetails userDetails = new MyUserDetails(
                "1",
                "chenling",
                passwordEncoder.encode("chenling"),
                "chenling",
        true,
       true,
        true,
       true,
                AuthorityUtils.createAuthorityList("admin"));

        return userDetails;
    }
}
