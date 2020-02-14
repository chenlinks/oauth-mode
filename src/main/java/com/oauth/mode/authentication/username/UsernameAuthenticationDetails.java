package com.oauth.mode.authentication.username;

import com.oauth.mode.authentication.entity.AbstractMyAuthenticationDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenling
 * @date 2020/2/13 17:10
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameAuthenticationDetails extends AbstractMyAuthenticationDetails {

    private static final long serialVersionUID = 1801418541472037442L;
    /**
      * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * app id
     */
    private String appId;
    /**
     * 账号类型
     */
    private String accountTypeId;
}
