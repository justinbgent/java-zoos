package com.schoolwork.zoos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        // http.anonymous().disable(); // since we allow anonymous users to access Swagger
        // and create a user account

        // Don't list an endpoint more than once
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/createnewuser").permitAll()
                .antMatchers("/oauth/revoke-token",
                        "/logout", "/admin/**", "/animals/**", "/zoos/**").hasAnyRole("ADMIN", "MGR")
                .antMatchers("/zoos/**",
                        "/actuator/**")
                .hasAnyRole("ZOODATA")
                .antMatchers("/animals/**")
                .hasAnyRole("ADMIN", "ANIMALDATA")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());


        // http.requiresChannel().anyRequest().requiresSecure(); // required for https
        http.csrf()
                .disable();
        http.headers()
                .frameOptions()
                .disable();
        http.logout()
                .disable();
    }
}
