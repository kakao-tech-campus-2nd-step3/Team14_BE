package com.ordertogether.team14_be.auth.token;

import java.util.Optional;

public abstract class TokenContext {

	private static final ThreadLocal<Long> currentMember = new ThreadLocal<>();

	public static void addCurrentMemberId(Long memberId) {
		currentMember.set(memberId);
	}

	public static Optional<Long> getCurrentMemberId() {
		return Optional.ofNullable(currentMember.get());
	}

	public static void clear() {
		currentMember.remove();
	}
}
