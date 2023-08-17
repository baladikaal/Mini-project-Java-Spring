package com.baladika.baladikaAPI.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final JWT jwt;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWT jwt) {
        this.jwt = jwt;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = request.getHeader("Authorization");
        logger.info("Received token: {}", token); // Logging the token
        // ... validasi token JWT dan periksa otorisasi di sini ...
        // Jika token valid, kembalikan objek Authentication
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Proses setelah otentikasi berhasil
        // ... Anda dapat melakukan sesuatu seperti menambahkan objek Principal ke SecurityContext ...
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

