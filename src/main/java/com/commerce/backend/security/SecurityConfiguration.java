package com.commerce.backend.security;

import com.commerce.backend.constants.SecurityConstants;
import com.commerce.backend.dao.userAuth.UserPrincipalDetails;
import com.commerce.backend.dao.userAuth.UserPrincipalDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder; // for SSO

    @Autowired
    private SecurityConstants securityConstants;
    
    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

  /*  @Bean
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
*/
   @Override
    public void configure(final HttpSecurity http) throws Exception {
         http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(securityConstants.getPublicUrl()).permitAll()
                .antMatchers(securityConstants.getAuthUrl()).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
            /*   .and()
               .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueToken -> opaqueToken
                                .introspector(introspector())
                        )
                ).oauth2ResourceServer();*/
    }
  
   
   @Bean
   DaoAuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	   provider.setPasswordEncoder(this.passwordEncoder());
	   provider.setUserDetailsService(this.userPrincipalDetailsService);
	   return provider;
   }
   
   protected void configure(AuthenticationManagerBuilder auth) {
	   auth.authenticationProvider(this.authenticationProvider());
   }
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
