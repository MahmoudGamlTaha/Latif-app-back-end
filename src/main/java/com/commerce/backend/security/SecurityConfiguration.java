package com.commerce.backend.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.commerce.backend.advice.CustomGlobalExceptionHandler;
import com.commerce.backend.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
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
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
               // .and().exceptionHandling(CustomGlobalExceptionHandler.class)
                .antMatchers("/api/public/account/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
               // .antMatchers("/api/public/blogs/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                .antMatchers("/api/public/account/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/public/reportedAds/**").hasAnyRole("USER", "ADMIN", "MANAGER")
              //  .antMatchers("/api/public/userSubscription/**").hasAnyRole("USER", "ADMIN")
           //     .antMatchers("/api/public/subscriptionTypes/**").hasRole("ADMIN")
           //     .antMatchers("/api/public/reasons/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/public/userRole/**").hasRole("ADMIN");
//                .antMatchers("/api/public/blogCategory/**").hasRole("ADMIN");
                //.antMatchers("/api/public/**").hasAuthority("ACCESS_TEST2")
                //.anyRequest().authenticated()
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
