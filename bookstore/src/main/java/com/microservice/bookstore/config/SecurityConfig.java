package com.microservice.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;
    private final String USER_ROLE = "USER";

    @Value("${spring.security.user.name}")
    private String securityUserName;

    @Value("${spring.security.user.password}")
    private String securityUserPassword;

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(securityUserName)
                .password("{noop}" + securityUserPassword)
                .roles(USER_ROLE);
    }

    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated() // only these APIs need users to be authenticated
                .antMatchers(HttpMethod.PATCH, "/api/v1/**").authenticated() // only these APIs need users to be authenticated
                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated() // only these APIs need users to be authenticated
                .anyRequest().permitAll() // the remaining can access without authentication
                .and()
                .httpBasic()
                .and()
                .csrf().disable(); // Cross-site request forgery not required for a stateless REST API.
    }
}
