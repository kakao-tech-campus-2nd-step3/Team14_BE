package com.ordertogether.team14_be.helper.jpa;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaPaymentDatabaseHelper implements PaymentDatabaseHelper {

	private final JpaDatabaseCleanup jpaDatabaseCleanup;

	@Override
	public void clean() {
		jpaDatabaseCleanup.execute();
	}
}
