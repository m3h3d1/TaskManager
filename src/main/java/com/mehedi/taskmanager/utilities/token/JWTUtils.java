package com.mehedi.taskmanager.utilities.token;

import com.mehedi.taskmanager.utilities.constants.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class JWTUtils {
    public static Boolean hasTokenExpired(String token){
        Claims claims = Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();
        return tokenExpirationDate.before(today);
    }

    public static String generateToken(String id, List<String> roles){
        return Jwts.builder()
                .setSubject(id)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis()+ TokenConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, TokenConstants.TOKEN_SECRET)
                .compact();
    }

    public static String extractUser(String token) {
        return Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static List<String> extractUserRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        return  (List<String>) claims.get("roles");
    }
}