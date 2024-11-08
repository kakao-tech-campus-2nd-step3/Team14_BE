package com.ordertogether.team14_be.payment.persistence.repository;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.web.dto.PaymentHistory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentOrderRepository {

	PaymentOrder save(PaymentOrder paymentOrder);

	List<PaymentOrder> saveAll(List<PaymentOrder> paymentOrders);

	Optional<PaymentOrder> findById(Long id);

	List<PaymentOrder> findByOrderId(String orderId);

	/**
	 * 주문 번호에 해당하는 주문에 대하여 총 결제 금액을 반환한다.
	 *
	 * @param orderId 주문번호
	 * @return 총 결제 금액
	 */
	BigDecimal getPaymentTotalAmount(String orderId);

	List<PaymentHistory> getChargeHistory(Long memberId, PaymentStatus paymentStatus);
}
