package org.reda.ushop.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String secretKey = "mySecretKey"; //  à externaliser via application.properties

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // subject = username
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
