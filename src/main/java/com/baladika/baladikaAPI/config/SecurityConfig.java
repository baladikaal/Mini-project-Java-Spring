package com.baladika.baladikaAPI.config;

import com.baladika.baladikaAPI.jwt.JWT;
import com.baladika.baladikaAPI.jwt.JwtFilter;
import com.baladika.baladikaAPI.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWT jwt;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWT jwt) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwt = jwt;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/register").permitAll()
                .antMatchers("/api/recruitment/positions").authenticated()
                .antMatchers("/api/recruitment/positions/{id}").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwt), UsernamePasswordAuthenticationFilter.class);
    }
}


