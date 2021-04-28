package com.commerce.backend.security;

import com.commerce.backend.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private SecurityConstants securityConstants;

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new CustomAuthoritiesOpaqueTokenIntrospector(
                restTemplateBuilder,
                securityConstants.getClientId(),
                securityConstants.getClientPassword(),
                securityConstants.getConnectionTimeout(),
                securityConstants.getReadTimeout(),
                securityConstants.getAuthUrl(),
                securityConstants.getAuthUsername()
        );
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(securityConstants.getWhitelist()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueToken -> opaqueToken
                                .introspector(introspector())
                        )
                ).oauth2ResourceServer();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER")
                .and()
                .withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
