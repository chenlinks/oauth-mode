package com.oauth.mode.security.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问拒绝的自定义处理器 AccessDeniedHander默认实现类是AccessDeniedHandlerImpl
 * @author yangxi
 */
@Component("simpleAuthenticationAccessDeniedHandler")
public class DefaultAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		response.setHeader("Content-type", "application/json");
		response.setCharacterEncoding("utf-8");

		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("code", "403");
		returnMap.put("msg", "您没有权限，访问被拒绝，请联系管理员" + accessDeniedException.getMessage());

		PrintWriter writer = response.getWriter();
		// 实际开发框架的话，肯定是组装一个统一的对象，然后使用fastjson转换成json串，然后输出给客户端（返回403状态）
		writer.println(JSONObject.toJSONString(returnMap));

	}

}