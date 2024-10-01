package com.ordertogether.team14_be.common.util;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class IdempotentKeyGenerator {

	public static String generate(String seed) {
		return UUID.nameUUIDFromBytes(seed.getBytes()).toString();
	}
}
