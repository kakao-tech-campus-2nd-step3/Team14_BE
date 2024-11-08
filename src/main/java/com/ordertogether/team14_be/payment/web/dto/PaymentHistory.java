package com.ordertogether.team14_be.payment.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentHistory(BigDecimal amount, LocalDateTime date) {}
