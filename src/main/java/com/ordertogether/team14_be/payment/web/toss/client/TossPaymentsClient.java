package com.ordertogether.team14_be.payment.web.toss.client;

import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.web.request.PaymentConfirmRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentConfirmationFailure;
import com.ordertogether.team14_be.payment.web.response.PaymentConfirmationResponse;
import com.ordertogether.team14_be.payment.web.toss.response.TossPaymentsConfirmationResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class TossPaymentsClient {

	private final RestClient tossRestClient;
	private static final String URI = "/v1/payments/confirm";
	private static final String IDEMPOTENCY_HEADER_KEY = "Idempotency-Key";

	public PaymentConfirmationResponse confirmPayment(PaymentConfirmRequest request) {
		return tossRestClient
				.post()
				.uri(URI)
				.header(IDEMPOTENCY_HEADER_KEY, request.paymentKey())
				.body(request)
				.exchange(
						(req, res) -> {
							TossPaymentsConfirmationResponse tossResponse =
									res.bodyTo(TossPaymentsConfirmationResponse.class);

							return new PaymentConfirmationResponse(
									getPaymentStatus(res), getFailure(tossResponse));
						});
	}

	private static PaymentStatus getPaymentStatus(ConvertibleClientHttpResponse res)
			throws IOException {
		return res.getStatusCode().isError() ? PaymentStatus.FAIL : PaymentStatus.SUCCESS;
	}

	private static PaymentConfirmationFailure getFailure(
			TossPaymentsConfirmationResponse tossResponse) {
		return Objects.isNull(tossResponse.failure())
				? null
				: new PaymentConfirmationFailure(
						tossResponse.failure().code(), tossResponse.failure().message());
	}
}
