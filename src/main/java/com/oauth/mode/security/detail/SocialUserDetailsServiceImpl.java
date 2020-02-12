package com.oauth.mode.security.detail;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 由于Spring Security中有用户角色的概念，而默认情况下通过第三方登录的账号是没有用户 角色的，
 * 所以要想真正进行整合，还应当让Spring Social的Connection与Spring Security的用户体系建立 关系。
 *
 * 1.实现SocialUserDetailsService，允许Spring Social通过Connection的userId加载Spring Security 体系下的用户信息。
 * 2.重新构建ConnectionSignUp，在“静默注册”的同时生成用户记录，并赋予用户一个合适的 角色。
 * @author chenling
 * @date 2020/2/7 0:23
 * @since V1.0.0
 */
@Component
@Slf4j
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
       //此处应该是从数据库查询，为了测试先写死，后面根据实际情况再进行修改
        log.info("验证用户信息：{}",userId);
        //设置权限
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("admin");
        return new SocialUser("chenling",passwordEncoder.encode("chenling"), authorityList);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("验证用户信息：{}",username);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
        return new User("chenling",passwordEncoder.encode("chenling"), Lists.newArrayList(authority));
    }
}
