package com.oauth.mode.properties;

import lombok.Data;

/**
 * @author chenling
 * @date 2020/2/6 13:26
 * @since V1.0.0
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
}
