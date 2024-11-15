package com.ordertogether.team14_be.payment.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentHistory(
		BigDecimal amount,
		@JsonFormat(
						shape = JsonFormat.Shape.STRING,
						pattern = "yyyy-MM-dd HH:mm:ss",
						timezone = "Asia/Seoul")
				LocalDateTime date) {

	public PaymentHistory(
			BigDecimal amount,
			@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
					LocalDateTime date) {
		this.amount = amount;
		this.date = date.plusHours(9);
	}
}
