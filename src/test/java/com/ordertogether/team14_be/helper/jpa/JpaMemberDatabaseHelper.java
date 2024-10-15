package com.ordertogether.team14_be.helper.jpa;

import com.ordertogether.team14_be.helper.MemberDatabaseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaMemberDatabaseHelper implements MemberDatabaseHelper {

	private final JpaDatabaseCleanup jpaDatabaseCleanup;

	@Override
	public void clean() {
		jpaDatabaseCleanup.execute();
	}
}
