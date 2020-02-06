package com.oauth.mode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenling
 * @date 2020/2/5 23:18
 * @since V1.0.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/userinfo")
    public String  user(){
        Map map = new HashMap<>();
        map.put("name","chenling");
        map.put("password","chenlign");
        return JSONObject.toJSONString(map);
    }
}
