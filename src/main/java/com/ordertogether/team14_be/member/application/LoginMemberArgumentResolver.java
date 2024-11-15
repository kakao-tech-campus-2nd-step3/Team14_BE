package com.ordertogether.team14_be.member.application;

import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.presentation.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hashLoginUserAnnotation = parameter.hasParameterAnnotation(LoginMember.class);
		return hashLoginUserAnnotation;
	}

	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory)
			throws Exception {

		String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
		String memberIdString = jwtUtil.decodeJwt(token).getSubject();
		Long memberId = Long.parseLong(memberIdString);

		return memberService.findMember(memberId);
	}
}
