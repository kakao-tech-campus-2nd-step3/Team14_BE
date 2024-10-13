package com.ordertogether.team14_be.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	private final SecretKey key;
	private final int expireTime;

	public JwtUtil(
			@Value("${key.jwt.secret-key}") String secretKey,
			@Value("${jwt.expire-time}") int expireTime) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.expireTime = expireTime;
	}

	public String generateToken(Long data) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + Duration.ofHours(expireTime).toMillis());

		return Jwts.builder()
				.setSubject(data.toString())
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public Claims decodeJwt(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
