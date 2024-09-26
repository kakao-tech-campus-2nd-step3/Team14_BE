package com.ordertogether.team14_be.payment.persistence.repository;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import java.util.List;
import java.util.Optional;

public interface PaymentOrderRepository {

	PaymentOrder save(PaymentOrder paymentOrder);

	List<PaymentOrder> saveAll(List<PaymentOrder> paymentOrders);

	Optional<PaymentOrder> findById(Long id);
}
