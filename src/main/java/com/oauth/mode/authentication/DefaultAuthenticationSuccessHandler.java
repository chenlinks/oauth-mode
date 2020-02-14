package com.oauth.mode.authentication;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security认证（账号密码/手机号码+短信验证码登录）成功后的处理器
 * @author yangxi
 *
 */
@Component("simpleAuthenticationSuccessHandler")
@Slf4j
public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	

	@Autowired
	private ObjectMapper objectMapper;


	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		log.info("登录成功");

		// appId
		String clientId = null;
		// appSecret
		String clientSecret = null;
		ClientDetails clientDetails = null;

		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Basic ")) {
			// 进一步判断从请求参数里面获取
			clientId = request.getParameter("clientId");
			clientSecret = request.getParameter("clientSecret");
			if(clientId == null || clientSecret == null) {
				throw new UnapprovedClientAuthenticationException("请求信息中无client信息");
			}
		} else {
			String[] tokens = extractAndDecodeHeader(header, request);
			assert tokens.length == 2;

			clientId = tokens[0];
			clientSecret = tokens[1];
		}
		clientDetails = clientDetailsService.loadClientByClientId(clientId);

		// 验证第三方应用传递的appId、appSecret是否正确
		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
		}

		if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}

		// 走标准的oauth2协议生成jwt token返回给第三方客户端
		@SuppressWarnings("unchecked")
		TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), "custom");

		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

//		Payload<OAuth2AccessToken> payload = new Payload<>(token);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(token));




//		log.info("----------登录成功,处理其他业务-----------");
//		String type = request.getHeader("Accept");
//		if(!type.contains("text/html")){
//
//			String clientId = "app";
//			String clientSecret = "app";
//
//			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//			if (null == clientDetails) {
//				throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
//			} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
//				throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
//			}
//
//			TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), "custom");
//
//			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//
//			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
//
//			OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//
//			response.setContentType("application/json;charset=UTF-8");
//
//			response.getWriter().write(objectMapper.writeValueAsString(token));
//		}else {
//			super.onAuthenticationSuccess(request, response, authentication);
//		}


	}
	
	/**
	 * 解析Basic加密的 authentication token
	 * @param header
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}


	/**
	 * 访问拒绝的自定义处理器 AccessDeniedHander默认实现类是AccessDeniedHandlerImpl
	 * @author yangxi
	 */
	@Component("simpleAuthenticationAccessDeniedHandler")
	public static class DefaultAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

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
}