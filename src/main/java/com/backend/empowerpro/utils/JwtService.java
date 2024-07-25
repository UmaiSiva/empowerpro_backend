package com.backend.empowerpro.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final String SECRET = "638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired at " + e.getClaims().getExpiration() + ". Current time: " + Instant.now());
        } catch (MalformedJwtException | SignatureException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token parsing error: " + e.getMessage());
        }
        return null;
    }

    private Claims getClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired at " + e.getClaims().getExpiration() + ". Current time: " + Instant.now());
            throw e;
        } catch (MalformedJwtException | SignatureException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Token parsing error: " + e.getMessage());
            throw e;
        }
    }

    public boolean isTokenValid(String jwt) {
        try{
            Claims claims = getClaims(jwt);
            return claims.getExpiration().after(Date.from(Instant.now()));
        }catch (ExpiredJwtException e) {
            return false;
        }catch (Exception e) {
            return false;
        }
    }
}