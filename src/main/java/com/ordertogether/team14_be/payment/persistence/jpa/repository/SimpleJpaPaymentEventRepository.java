package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleJpaPaymentEventRepository extends JpaRepository<PaymentEventEntity, Long> {

	Optional<PaymentEventEntity> findByOrderId(String orderId);

	@Modifying
	@Query(
			"UPDATE PaymentEventEntity pee"
					+ " SET pee.paymentStatus = :afterStatus"
					+ " WHERE pee.paymentKey = :paymentKey"
					+ "  AND pee.orderId = :orderId")
	Integer updatePaymentStatus(String paymentKey, String orderId, PaymentStatus afterStatus);

	@Modifying
	@Query(
			"UPDATE PaymentEventEntity pee"
					+ " SET pee.paymentKey = :paymentKey"
					+ " WHERE pee.orderId = :orderId")
	Integer updatePaymentKey(String paymentKey, String orderId);
}
