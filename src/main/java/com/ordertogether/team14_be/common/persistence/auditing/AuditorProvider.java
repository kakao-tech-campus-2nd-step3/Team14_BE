package com.ordertogether.team14_be.common.persistence.auditing;

import com.ordertogether.team14_be.auth.token.TokenContext;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorProvider implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return TokenContext.getCurrentMemberId();
	}
}
