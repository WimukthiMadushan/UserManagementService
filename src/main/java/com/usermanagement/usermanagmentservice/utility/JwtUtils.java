package com.usermanagement.usermanagmentservice.utility;

import com.usermanagement.usermanagmentservice.dto.organizer.Organizer;
import com.usermanagement.usermanagmentservice.dto.traveller.Traveller;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {
    private static final String SECRET_KEY_STRING = "your-very-strong-secret-key-which-is-at-least-32-characters";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    private static final long TOKEN_HOURS = 6;

    public static String generateTravellerToken(Traveller user) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600_000*TOKEN_HOURS;
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(expMillis);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("id", user.getId())
                .claim("status", "Traveller")
                .claim("firstName", user.getFirstName())
                .claim("email", user.getEmail())
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String generateOrganizerToken(Organizer user) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600_000*TOKEN_HOURS;
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(expMillis);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("id", user.getId())
                .claim("status", "Organizer")
                .claim("organizationName", user.getOrganizationName())
                .claim("ownerName", user.getOwnerName())
                .claim("email", user.getEmail())
                .signWith(SECRET_KEY)
                .compact();
    }
}
