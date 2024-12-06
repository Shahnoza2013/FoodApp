package uz.pdp.fastfoodapp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_HS256_KEY = "uQ8V4Yc0OgrUAf6KQVBoeV4Ny5ntmzOtPrCKJ9pU9Es=";
    private final Long EXPIRED_DATE_ACCESS = 15 * 60 * 1000L; // 15 minutes
    private final Long EXPIRED_DATE_REFRESH = 7 * 24 * 60 * 60 * 1000L; // 7 days
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_HS256_KEY.getBytes());

    public String generateTokenAccess(String username) {
        return createToken(username, new HashMap<>(), EXPIRED_DATE_ACCESS);
    }

    public String generateTokenRefresh(String username) {
        return createToken(username, new HashMap<>(), EXPIRED_DATE_REFRESH);
    }

    private String createToken(String subject, Map<String, Object> claims, Long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        Claims jwtBody = getJWTBody(token);
        return jwtBody.getSubject();
    }

    public Claims getJWTBody(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getJWTBody(token).getExpiration();
        return expiration.before(new Date());
    }
}

