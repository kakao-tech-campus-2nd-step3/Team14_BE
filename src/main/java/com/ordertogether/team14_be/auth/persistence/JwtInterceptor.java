package com.ordertogether.team14_be.auth.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordertogether.team14_be.member.application.exception.NotFoundMember;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
	private final JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			return true;
		}

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = authorization.replaceAll("Bearer ", "");

		if (token != null && token.length() > 10) {
			log.debug("토큰 상태:: " + token);

			if (jwtUtil.vaildToken(token)) {
				ObjectMapper objectMapper = new ObjectMapper();

				String member = objectMapper.writeValueAsString(jwtUtil.decodeJwt(token).get("member"));
				Member accessMember = objectMapper.readValue(member, Member.class);

				request.setAttribute("member", accessMember);
				return true;
			}
		} else {
			throw new NotFoundMember();
		}
		return false;
	}
}
