package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.web.dto.PaymentHistory;
import java.util.List;
import lombok.Builder;

@Builder
public record PaymentHistoryResponse(List<PaymentHistory> histories) {}
