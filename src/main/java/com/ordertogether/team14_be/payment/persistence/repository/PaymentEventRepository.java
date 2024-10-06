package com.ordertogether.team14_be.payment.persistence.repository;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import java.util.Optional;

public interface PaymentEventRepository {

	PaymentEvent save(PaymentEvent paymentEvent);

	Optional<PaymentEvent> findById(Long id);

	Optional<PaymentEvent> findByOrderId(String orderId);
}
