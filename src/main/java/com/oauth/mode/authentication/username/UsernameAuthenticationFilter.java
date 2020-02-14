package com.oauth.mode.authentication.username;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenling
 * @date 2020/2/13 17:35
 * @since V1.0.0
 */
@Slf4j
public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UsernameAuthenticationFilter(AuthenticationManager authenticationManager,
                                        UsernameAuthenticationDetailsSource authenticationDetailsSource,
                                        AuthenticationSuccessHandler successHandler,
                                        AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher("/user/login", HttpMethod.POST.name()));
        setAuthenticationManager(authenticationManager);
        setAuthenticationDetailsSource(authenticationDetailsSource);
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
    }

//    public UsernameAuthenticationFilter(AuthenticationManager authenticationManager,
//                                        UsernameAuthenticationDetailsSource authenticationDetailsSource){
//        super(new AntPathRequestMatcher("/login", "POST"));
//        setAuthenticationManager(authenticationManager);
//        setAuthenticationDetailsSource(authenticationDetailsSource);
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Object authenticationDetails = authenticationDetailsSource.buildDetails(request);

        log.info("source --- build --- detail :{}",authenticationDetails);
        UserNameMyAuthenticationToken authenticationToken = new UserNameMyAuthenticationToken(authenticationDetails);
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
