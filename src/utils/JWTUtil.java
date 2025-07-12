package utils;

import io.jsonwebtoken.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class JWTUtil {
    private static final String SECRET;
    private static final long EXPIRY_MS;

    static {
        try (FileInputStream fis = new FileInputStream("config/config.properties")) {
            Properties props = new Properties();
            props.load(fis);

            SECRET    = props.getProperty("jwt.secret");
            EXPIRY_MS = Long.parseLong(props.getProperty("jwt.expiry"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load JWT config", e);
        }
    }

    // Generate a token containing userId, email, role
    public static String generateToken(int userId, String email, String role) {
        Date now    = new Date();
        Date expire = new Date(now.getTime() + EXPIRY_MS);

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("email", email)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expire)
            .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
            .compact();
    }

    // Parse and validate token, returning the Jws<Claims> if valid
    public static Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parser()
            .setSigningKey(SECRET.getBytes())
            .parseClaimsJws(token);
    }
}
