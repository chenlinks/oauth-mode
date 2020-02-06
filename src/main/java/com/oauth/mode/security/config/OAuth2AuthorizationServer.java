package com.oauth.mode.security.config;

import com.oauth.mode.token.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器
 * @author chenling
 * @date 2020/2/5 19:37
 * @since V1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  SocialUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     *
     * 用来配置令牌端点(Token Endpoint)的安全约束，实际上是指/oauth/token端点。/oauth/authorize端点也需要安全，
     * 但这是一个普通的面向用户的端点，并且应与您的UI其余部分以相同的方式进行安全保护，
     * 因此这里不做介绍。默认设置满足最常见的要求，并遵循OAuth2规范的建议，
     * 因此您无需在此处进行任何操作即可启动基本服务器并运行
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
        security.passwordEncoder(passwordEncoder);
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * 请注意，除非为{@link #configure（AuthorizationServerEndpointsConfigurer）}提供了{@link AuthenticationManager}，
     * 否则不会启用密码授予（即使允许某些客户端）。必须声明至少一个客户端或完整的定制{@link ClientDetails ​​ervice}，否则服务器将无法启动
     * @param clients 客户端详细信息配置器
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        builder
                //授权码授权模式
                .withClient("client-code")
                .secret(new BCryptPasswordEncoder().encode("123123"))
                .redirectUris("http://localhost:9001/callback")
                //授权模式 refresh_token
                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("read_userinfo", "read_contacts");

                //密码模式
        builder
                .withClient("client-password")
                .secret(new BCryptPasswordEncoder().encode("123123"))
                //授权模式 refresh_token
                .authorizedGrantTypes("password","refresh_token")
                .scopes("admin", "visitor");

                //简易模式
        builder
                .withClient("client-implicit")
                .secret(new BCryptPasswordEncoder().encode("123123"))
                .redirectUris("http://localhost:9001/implicit/getToken")
                //授权模式 refresh_token
                .authorizedGrantTypes("implicit")
                .scopes("read_userinfo", "read_contacts");

                //客户端凭证模式
        builder
                .withClient("client-client")
                .secret(new BCryptPasswordEncoder().encode("123123"))
                //授权模式 refresh_token
                .authorizedGrantTypes("client_credentials")
                .scopes("read_userinfo", "read_contacts");
    }


    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * 自定义，用户批准和授予类型。
     * 默认情况下，您不需要执行任何操作，除非需要密码授予，在这种情况下，
     * 您需要提供{@link AuthenticationManager}。
     * @param endpoints  端点配置器
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        // 配置jwt内容增强器
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(delegates);

        //token 存储器可以改为redis
        endpoints.tokenStore(tokenStore);
        endpoints.tokenEnhancer(tokenEnhancerChain);
        endpoints.userDetailsService(userDetailsService);
        endpoints.authenticationManager(authenticationManager);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


}
