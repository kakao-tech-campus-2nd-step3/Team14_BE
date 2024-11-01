package com.ordertogether.team14_be.payment.web.request;

import lombok.Builder;

@Builder
public record PaymentConfirmRequest(String orderId, String paymentKey, Long amount) {}
