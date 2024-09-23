package com.ordertogether.team14_be.auth;

import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.Jwts;


@Component
public class JwtUtil {
  private static final SecretKey key = SIG.HS256.key().build();
  private static final int EXPIRE_TIME = 1;

  public static String generateToken(String email) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + Duration.ofHours(EXPIRE_TIME).toMillis());

    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(key)
            .compact();
  }
}