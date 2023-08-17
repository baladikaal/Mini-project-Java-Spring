package com.baladika.baladikaAPI.repository;

import com.baladika.baladikaAPI.resolver.UserRepository;
import com.baladika.baladikaAPI.entity.UserEntity;
import com.baladika.baladikaAPI.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private final UserRepository userRepository;

    private final JWT jwt;

    public UserArgumentResolver(UserRepository userRepository, JWT jwt) {
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserEntity.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = servletRequest.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        }

        try {
            String jwtToken = token.substring(7);
            String userId = extractUserIdFromToken(jwtToken);

            Optional<UserEntity> user = Optional.ofNullable(userRepository.findByIdAndIsActiveTrue(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized")));
            UserEntity newUser = user.orElseGet(() -> null);

            return newUser;
        } catch (ExpiredJwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token expired");
        }
    }

    private String extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwt.getSecretKey()).parseClaimsJws(token).getBody();

        return claims.get("userId", String.class);
    }
}
