package com.ordertogether.team14_be.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class PaymentEvent extends BaseEntity {

  @Column(nullable = false)
  private Long buyerId; // 구매자 식별자

  @Column(nullable = false)
  private String orderId;

  private String orderName;

  @Column(nullable = false)
  private String paymentKey; // PSP 결제 식별자

  @Builder.Default private Boolean isPaymentDone = false;
}
