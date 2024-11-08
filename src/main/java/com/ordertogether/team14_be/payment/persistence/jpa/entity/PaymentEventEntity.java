package com.ordertogether.team14_be.payment.persistence.jpa.entity;

import com.ordertogether.team14_be.common.persistence.entity.BaseTimeEntity;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@ToString
@Table(name = "payment_event")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEventEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long buyerId; // 구매자 식별자

	@Column(nullable = false)
	private String orderId;

	@Column(nullable = false)
	private String orderName;

	private String paymentKey; // PSP 결제 식별자

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus = PaymentStatus.READY;
}
