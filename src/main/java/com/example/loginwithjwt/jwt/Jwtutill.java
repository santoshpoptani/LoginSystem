package com.example.loginwithjwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class Jwtutill {
    @Value("${jwt.key.secretkey}")
    private String secretKey;

    public String generateToken(String UserName) {
        String tokenId = String.valueOf(new Random().nextInt(1000));
        return Jwts.builder()
                .setId(tokenId)
                .setSubject(UserName)
                .setIssuer("The Ghost")
                .setAudience("The Ghost Auidaince")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String Token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(Token)
                .getBody();
    }

    public boolean isValidToken(String Token, String Username) {
        String tokenUsername = getSubject(Token);
        return (Username.equals(tokenUsername) && !isTokenExpired(Token));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    public Date getExpirationDate(String Token) {
        return getClaims(Token).getExpiration();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }
}
