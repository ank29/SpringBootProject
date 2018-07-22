package com.springBoot.oauth;

import com.springBoot.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.springBoot.service.AccountUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;



@EnableAuthorizationServer
@Configuration
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    Logger logger = LogManager.getLogger(OAuth2AuthorizationServer.class);

    private AppConfig appConfig;

    private AuthenticationManager authenticationManager;

    private AccountUserDetailsService accountUserDetailsService;

    @Autowired
    public OAuth2AuthorizationServer(AppConfig appConfig,AuthenticationManager authenticationManager,AccountUserDetailsService accountUserDetailsService) {
        this.appConfig = appConfig;
        this.accountUserDetailsService = accountUserDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @Value("${signingKey}")
    String signingKey;
    //1. Key to be saved in properties file
    //2. Symmetric key encryption
    //3. implements AccessTokenConverter and TokenEnhancer
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);//Sets the JWT signing key. It can be either a simple MAC key or an RSA key.
        return converter;
    }
    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }



    //ClientDetailsServiceConfigurer
    //1. jdbc is the function of JdbcClientDetailsServiceBuilder class
                    //where it defines clientdetails, passwordencoder & datasource
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .jdbc(appConfig.dataSource())
                .passwordEncoder(appConfig.passwordEncoder());
        logger.debug(clients);
    }



    //AuthorizationServerSecurityConfigurer
    //1. AuthorizationServerConfigurerAdapter- Configure the security of the Authorization Server,
                        //which means in practical terms the /oauth/token endpoint.
    //2. allowFormAuthenticationForClients, authenticating the client using the form parameters instead of basic auth is enabled using this
    //3. checkTokenAccess, the endpoint is enabled (/oauth/check_token) which by default has value denyAll(). This endpoint is not exposed byDefault
    @Override
    public void configure (AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
        logger.debug(security);

    }


    //AuthorizationServerEndpointsConfigurer
    //1. to enable password grant type we need to pass authenticationManager
    //2. to enable tokenStore()
    //3. token customizations
    //4. features of the Authorization Server endpoints
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter()).authenticationManager(authenticationManager).userDetailsService(accountUserDetailsService);
        logger.debug(endpoints);

    }
}
