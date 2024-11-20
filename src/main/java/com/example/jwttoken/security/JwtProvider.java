package com.example.jwttoken.security;

import com.example.jwttoken.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRole())
                .claim("firstName", user.getFirstName())
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) throws AuthException {
        return validateToken(accessToken, jwtAccessSecret);
    }


    private boolean validateToken(@NonNull String token, @NonNull Key secret) throws AuthException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
            throw new AuthException("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
            throw new AuthException("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
            throw new AuthException("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
            throw new AuthException("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token", e);
            throw new AuthException("invalid token");
        }
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }


    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
