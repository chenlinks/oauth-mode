package com.oauth.mode.authentication.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * @author chenling
 * @date 2020/2/13 17:07
 * @since V1.0.0
 */
public class MyUserDetails extends User implements SocialUserDetails {


    /**
     * 用户主键ID
     */
    private String id;
    /**
     * 租户ID
     */
    @Getter
    private String tenantId;
    /**
     * 用户名
     */
    @Getter
    private String name;



    public MyUserDetails(String id, String username, String password, String tenantId,
                              boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                              boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(id, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.tenantId = tenantId;
        this.name = username;
    }

    @Override
    public String getUserId() {
        return id;
    }

}
