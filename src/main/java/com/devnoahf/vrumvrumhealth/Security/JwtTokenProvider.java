package com.devnoahf.vrumvrumhealth.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    // Chave segura de 512 bits gerada automaticamente
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private Key getSigningKey() {
        return secretKey;
    }

    /**
     * Gera token JWT incluindo roles do usuário
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        // Pega as roles como lista de strings
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Retorna o username (email) do token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Retorna as roles do token
     */
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object rolesObject = claims.get("roles");
        if (rolesObject instanceof List<?> rolesList) {
            return rolesList.stream()
                    .map(o -> {
                        if (o instanceof java.util.Map<?, ?> m && m.containsKey("authority")) {
                            return m.get("authority").toString();
                        } else {
                            return o.toString();
                        }
                    })
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    /**
     * Valida token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
