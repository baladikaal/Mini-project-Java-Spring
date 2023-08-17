package com.baladika.baladikaAPI.jwt;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt.property")
@Data
public class JWT {
    private String secretKey;

    private Long accessTokenExpired;

    private Long refreshTokenExpired;


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getAccessTokenExpired() {
        return accessTokenExpired;
    }

    public void setAccessTokenExpired(Long accessTokenExpired) {
        this.accessTokenExpired = accessTokenExpired;
    }

    public Long getRefreshTokenExpired() {
        return refreshTokenExpired;
    }

    public void setRefreshTokenExpired(Long refreshTokenExpired) {
        this.refreshTokenExpired = refreshTokenExpired;
    }
}
