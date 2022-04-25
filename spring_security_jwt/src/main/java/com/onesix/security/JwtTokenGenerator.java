package com.onesix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenGenerator {

    private static final String secretKey = "secretKey";
    private int valid_duration = 10 * 60 * 1000;

    public static Boolean isTokenExpired(String token, UserDetails userDetails) {
//        Claims claims = Jwts.parser().setSigningKey(token).parseClaimsJws(userDetails.getUsername()).getBody();
//        String username = getSubjectName(token);
//        Boolean expireTime = claims.getExpiration().before(new Date());
//        return (username.equals(userDetails.getUsername()) && !expireTime);
        String username = getSubjectName(token);
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);

    }

    public static String getSubjectName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(UserDetails userDetails) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("issuer", "clove");
        claims.put("name", userDetails.getUsername().toString());

        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setHeaderParam("typ", Header.JWT_TYPE)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + valid_duration))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

}
