package com.oauth.mode.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author chenling
 * @date 2020/2/6 13:25
 * @since V1.0.0
 */
@ConfigurationProperties(prefix = "demo.security")
@Configuration
@Data
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    private SocialProperties social = new SocialProperties();
}
