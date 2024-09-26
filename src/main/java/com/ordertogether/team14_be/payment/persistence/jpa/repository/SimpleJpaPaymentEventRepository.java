package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleJpaPaymentEventRepository extends JpaRepository<PaymentEventEntity, Long> {

	Optional<PaymentEventEntity> findByOrderId(String orderId);
}
