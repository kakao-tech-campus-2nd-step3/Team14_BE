package com.ordertogether.team14_be.auth.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordertogether.team14_be.auth.token.TokenContext;
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
		log.info("JwtInterceptor 실행");
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			log.info("OPTIONS 요청");
			return true;
		}

		logRequestDetails(request);

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = authorization.replaceAll("Bearer ", "");

		if (token != null && token.length() > 10) {
			log.debug("토큰 상태:: " + token);

			if (jwtUtil.vaildToken(token)) {
				ObjectMapper objectMapper = new ObjectMapper();

				String member = objectMapper.writeValueAsString(jwtUtil.decodeJwt(token).get("member"));
				log.info("member = " + member);
				Member accessMember = objectMapper.readValue(member, Member.class);
				log.info("accessMember = " + accessMember);

				request.setAttribute("member", accessMember);
				Long memberId = Long.valueOf(jwtUtil.getSubject(token));
				log.info("memberId = " + memberId);
				TokenContext.addCurrentMemberId(memberId);
				return true;
			}
		} else {
			throw new NotFoundMember();
		}
		return false;
	}

	private static void logRequestDetails(HttpServletRequest request) {
		String clientIp = request.getHeader("X-Forwarded-For");
		if (clientIp == null || clientIp.isEmpty()) {
			clientIp = request.getRemoteAddr();
		}
		log.info("Request URI = " + request.getRequestURI());
		log.info("Client IP = " + clientIp);
	}
}
