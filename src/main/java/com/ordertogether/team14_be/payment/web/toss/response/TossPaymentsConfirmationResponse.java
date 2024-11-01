package com.ordertogether.team14_be.payment.web.toss.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record TossPaymentsConfirmationResponse(
		String version,
		String paymentKey,
		String type,
		String orderId,
		String orderName,
		String mId,
		String currency,
		String method,
		BigDecimal totalAmount,
		BigDecimal balanceAmount,
		String status,
		String requestedAt,
		String approvedAt,
		boolean useEscrow,
		String lastTransactionKey,
		BigDecimal suppliedAmount,
		BigDecimal vat,
		boolean cultureExpense,
		BigDecimal taxFreeAmount,
		int taxExemptionAmount,
		List<Cancel> cancels,
		boolean expired,
		boolean isPartialCancelable,
		Card card,
		VirtualAccount virtualAccount,
		Transfer transfer,
		CashReceipt cashReceipt,
		List<CashReceipt> cashReceipts,
		Metadata metadata,
		String receiptUrl,
		EasyPay easyPay,
		String country,
		Failure failure,
		RefundReceiveAccount refundReceiveAccount) {

	public static record Card(
			BigDecimal amount,
			String issuerCode,
			String acquirerCode,
			String number,
			int installmentPlanMonths,
			String approveNo,
			boolean useCardPoint,
			String cardType,
			String ownerType,
			String acquireStatus,
			boolean isInterestFree,
			String interestPayer) {}

	public static record Cancel(
			BigDecimal cancelAmount,
			String cancelReason,
			BigDecimal taxFreeAmount,
			int taxExemptionAmount,
			BigDecimal refundableAmount,
			BigDecimal easyPayDiscountAmount,
			String canceledAt,
			String transactionKey,
			String receiptKey,
			String cancelStatus,
			String cancelRequestId) {}

	public static record CashReceipt(
			String type,
			String receiptKey,
			String issueNumber,
			String receiptUrl,
			BigDecimal amount,
			BigDecimal taxFreeAmount,
			String businessNumber,
			String transactionType,
			String issueStatus,
			String customerIdentityNumber,
			String requestedAt) {}

	public static record EasyPay(String provider, BigDecimal amount, BigDecimal discountAmount) {}

	public static record Failure(String code, String message) {}

	public static record Metadata(Map<String, String> metadata) {}

	public static record RefundReceiveAccount(
			String bankCode, String accountNumber, String holderName) {}

	public static record Transfer(String bankCode, String settlementStatus) {}

	public static record VirtualAccount(
			String accountType,
			String accountNumber,
			String bankCode,
			String customerName,
			String dueDate,
			String refundStatus,
			boolean expired) {}
}
