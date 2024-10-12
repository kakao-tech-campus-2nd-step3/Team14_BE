package com.ordertogether.team14_be.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;

public final class JwtUtil {
	private static final SecretKey KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
	private static final int EXPIRE_TIME = 1;

	public static String generateToken(Long data) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + Duration.ofHours(EXPIRE_TIME).toMillis());

		return Jwts.builder()
				.setSubject(data.toString())
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(KEY)
				.compact();
	}
}
