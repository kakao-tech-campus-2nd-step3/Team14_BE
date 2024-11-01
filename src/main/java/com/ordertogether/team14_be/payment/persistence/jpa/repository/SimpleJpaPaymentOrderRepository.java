package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentOrderEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleJpaPaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

	@Query("SELECT SUM(po.amount) FROM PaymentOrderEntity po WHERE po.orderId = :orderId")
	Optional<BigDecimal> getPaymentTotalAmount(String orderId);

	List<PaymentOrderEntity> findByOrderId(String orderId);
}
