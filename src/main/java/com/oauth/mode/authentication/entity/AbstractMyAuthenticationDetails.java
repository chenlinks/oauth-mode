package com.oauth.mode.authentication.entity;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chenling
 * @date 2020/2/13 16:49
 * @since V1.0.0
 */
@Data
public class AbstractMyAuthenticationDetails  implements Serializable {


    private static final long serialVersionUID = 4093674380283386791L;
    /**
     * 额外信息
     */
    protected Map<String, Object> extraData = Maps.newHashMap();
}
