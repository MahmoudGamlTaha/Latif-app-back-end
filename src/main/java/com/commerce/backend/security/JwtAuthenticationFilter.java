package com.commerce.backend.security;

import com.auth0.jwt.JWT;
import com.commerce.backend.dao.VerificationTokenRepository;
import com.commerce.backend.model.dto.LoginCredentialDto;
import com.commerce.backend.model.entity.VerificationToken;
import com.commerce.backend.service.TokenServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
    private  final int EXPIRY_DATE = 60 * 24;
    LoginCredentialDto credentials = null;
    
    VerificationTokenRepository verificationTokenRepository;
     
    
   
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, VerificationTokenRepository verificationTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.verificationTokenRepository = verificationTokenRepository;
    }
   
   
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // Grab credentials and map them to login LoginCredentialDto
       
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginCredentialDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            
            return null;
        }

        assert credentials != null;

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getMobile(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);
       
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl principal = (UserDetailsImpl) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getMobileNum())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setFirebaseToken(credentials.getToken());
        verificationToken.setUser(principal.getCurrentUser());
        verificationToken.setExpiryDate(calculateExpiryDate());
        verificationToken.setDevice(credentials.getDevice());
        verificationTokenRepository.save(verificationToken);
        // Add token in response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"" +JwtProperties.HEADER_STRING+ "\":\"" + token + "\"}");
    }
    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRY_DATE);
        return new Date(cal.getTime().getTime());
    }
}

