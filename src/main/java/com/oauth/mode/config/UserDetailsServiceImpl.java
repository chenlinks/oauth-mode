package com.oauth.mode.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author chenling
 * @date 2020/2/6 0:32
 * @since V1.0.0
 */
@Service("userService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("验证用户信息：{}",username);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
        return new User("chenling",passwordEncoder.encode("chenling"), Lists.newArrayList(authority));
    }
}
