package com.oauth.mode.authentication.entity;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author chenling
 * @date 2020/2/13 16:49
 * @since V1.0.0
 */
@Data
public class AbstractMyAuthenticationDetails {
    /**
     * 额外信息
     */
    protected Map<String, Object> extraData = Maps.newHashMap();
}
