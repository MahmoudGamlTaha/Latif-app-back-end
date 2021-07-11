package com.commerce.backend.security;

import com.commerce.backend.dao.UserPermissionsRepository;
import com.commerce.backend.model.entity.UserPermissions;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.dao.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final UserPermissionsRepository userPermissionsRepository;
    
    @Autowired 
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, UserPermissionsRepository userPermissionsRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userPermissionsRepository = userPermissionsRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<UserPermissions> matchers= userPermissionsRepository.findAll();
        for(UserPermissions m : matchers) {
            http
                    .csrf().disable()
                    .cors()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .logout()
                    .logoutUrl("/account/logout")
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager(), this.verificationTokenRepository))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                    .authorizeRequests()
                    
                    .antMatchers(HttpMethod.valueOf(m.getHttpMethod()), m.getHttpPath())
                    .hasAuthority(m.getName());
        }
               // .antMatchers("/api/public/blogs/**").hasAnyRole("USER", "ADMIN", "MANAGER")
               // .antMatchers("/api/public/account/**").hasAnyRole("USER", "ADMIN")
               // .antMatchers("/api/public/reportedAds/**").hasAnyRole("USER", "ADMIN", "MANAGER")
              //  .antMatchers("/api/public/userSubscription/**").hasAnyRole("USER", "ADMIN")
           //     .antMatchers("/api/public/subscriptionTypes/**").hasRole("ADMIN")
           //     .antMatchers("/api/public/reasons/**").hasAnyRole("USER", "ADMIN")
              //  .antMatchers("/api/public/userRole/**").hasRole("ADMIN");
               // .antMatchers("/api/public/account/**").hasAnyRole("USER", "ADMIN")
               // .antMatchers("/api/public/reportedAds/**").hasAnyRole("USER", "ADMIN", "MANAGER")
               // .antMatchers("/api/public/userSubscription/**").hasAnyRole("USER", "ADMIN")
           //     .antMatchers("/api/public/subscriptionTypes/**").hasRole("ADMIN")
               // .antMatchers("/api/public/reasons/**").hasAnyRole("USER", "ADMIN")
               // .antMatchers("/api/public/userRole/**").hasRole("ADMIN");

//                .antMatchers("/api/public/blogCategory/**").hasRole("ADMIN");
                //.antMatchers("/api/public/**").hasAuthority("ACCESS_TEST2")
                //.anyRequest().authenticated()
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
