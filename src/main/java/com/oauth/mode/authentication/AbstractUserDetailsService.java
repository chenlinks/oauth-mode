package com.oauth.mode.authentication;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import com.oauth.mode.expection.MyAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author chenling
 * @date 2020/2/13 16:53
 * @since V1.0.0
 */
@Slf4j
public abstract  class AbstractUserDetailsService<AuthenticationDetail extends AbstractMyAuthenticationDetails, UserDetail extends UserDetails>  {


    /**
     * 根据 认证信息 查询并返回 用户信息
     * @param authenticationDetails
     * @return
     */
    abstract public UserDetail loadUser(AuthenticationDetail authenticationDetails) throws MyAuthenticationException;



    /**
     * 校验用户信息，用来判断用户密码是否正确，账号是否被禁用等
     * @param authenticationDetails
     * @param userDetails
     */
    public void checkUser(AuthenticationDetail authenticationDetails, UserDetail userDetails) throws MyAuthenticationException {

       log.info("------------------检查用户信息------------------{}-",userDetails);

        if (!userDetails.isAccountNonLocked()) {
        }

        if (!userDetails.isEnabled()) {
        }

        if (!userDetails.isAccountNonExpired()) {
        }
    }


}
