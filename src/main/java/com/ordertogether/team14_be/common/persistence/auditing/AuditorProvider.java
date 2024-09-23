package com.ordertogether.team14_be.common.persistence.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorProvider implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		// todo: 토큰 정보를 가지고 회원의 식별자를 반환하도록 수정
		return Optional.of(1L);
	}
}
