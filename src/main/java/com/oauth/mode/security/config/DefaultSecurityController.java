//package com.oauth.mode.security.config;
//
//import com.deepexi.domain.auth.core.properties.SecurityConstants;
//import com.deepexi.util.config.Payload;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 默认的安全相关的请求地址配置
// * @author yangxi
// *
// */
//@RestController
//public class DefaultSecurityController {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	private RequestCache requestCache = new HttpSessionRequestCache();
//
//	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//	public Payload<Map<String, Object>> defaultLoginPage(HttpServletRequest request, HttpServletResponse response) {
//
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//		if (savedRequest != null) {
//			String targetUrl = savedRequest.getRedirectUrl();
//			logger.info("引发跳转的请求是:" + targetUrl);
//		}
//
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("msg", "访问的是受保护的地址，请先登录后在访问");
//
//		return new Payload<Map<String,Object>>(map);
//	}
//}
