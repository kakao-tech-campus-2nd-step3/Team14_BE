package com.ordertogether.team14_be.auth.persistence;

import com.ordertogether.team14_be.auth.persistence.exception.InvalidToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
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

	public String getSubject(String token) {
		return decodeJwt(token).getSubject();
	}

	public boolean vaildToken(String token) throws InvalidToken {
		try {
			Claims claims =
					Jwts.parser()
							.setSigningKey(key)
							.parseClaimsJws(token) // 토큰 파싱
							.getBody();
			return true; // 유효하다면 true 반환
		} catch (MalformedJwtException e) {
			throw new InvalidToken();
		}
	}
}
