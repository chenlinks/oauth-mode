//package com.oauth.mode.security.config;
//
//import com.deepexi.domain.auth.core.enums.PayloadExceptionType;
//import com.deepexi.domain.auth.core.exception.DeepexiAuthenticationException;
//import com.deepexi.util.config.Payload;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Spring Security认证失败后的处理器
// * @author yangxi
// *
// */
//@Component("simpleAuthenctiationFailureHandler")
//public class DefaultAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//
//	/* (non-Javadoc)
//	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
//	 */
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//
//		logger.info("登录失败");
//
//		response.setStatus(HttpStatus.BAD_REQUEST.value());
//		response.setContentType("application/json;charset=UTF-8");
//
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		Payload<Map<String, Object>> payload = new Payload<>(returnMap);
//		if(exception instanceof DeepexiAuthenticationException) {
//			DeepexiAuthenticationException e = (DeepexiAuthenticationException)exception;
//			payload.setCode(e.getType().getCode());
//			payload.setMsg(e.getType().getMsg());
//		} else {
//			payload.setCode(PayloadExceptionType.AUTHENTICATION_AUTH_EXCEPTION.getCode());
//			payload.setMsg(exception.getMessage());
//		}
//
//		response.getWriter().write(objectMapper.writeValueAsString(payload));
//	}
//}