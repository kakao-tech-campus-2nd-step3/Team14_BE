package com.ordertogether.team14_be.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentOrder extends BaseEntity {

  @Column(nullable = false)
  private Long sellerId; // 판매자 식별자

  @ManyToOne(fetch = FetchType.LAZY)
  private Product productId;

  @Column(nullable = false)
  private String orderId;

  private BigDecimal amount; // 결제 금액

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.READY;

  @Builder.Default private Byte retryCount = 0; // 재시도 횟수

  @Builder.Default private Byte retryThreshold = 5; // 재시도 허용 임계값
}
