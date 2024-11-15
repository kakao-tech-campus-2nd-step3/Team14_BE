package com.ordertogether.team14_be.helper.jpa;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
@RequiredArgsConstructor
public class JdbcPaymentDatabaseHelper implements PaymentDatabaseHelper {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void clean() {
		jdbcTemplate.execute("DELETE FROM payment_order");
		jdbcTemplate.execute("DELETE FROM payment_event");
		jdbcTemplate.execute("DELETE FROM product");
		jdbcTemplate.execute("DELETE FROM member");
	}

	@Override
	public void saveTestData() {
		saveMembers();
		saveProducts();
		savePaymentOrdersAndEvents();
	}

	private void saveMembers() {
		String sql =
				"INSERT INTO member (id, email, point, phone_number, delivery_name, platform) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(
				sql,
				new BatchPreparedStatementSetter() {
					private final List<Object[]> members =
							List.of(
									new Object[] {
										1L, "member1@example.com", 1000000, "010-0000-0001", "Member01", "Kakao"
									},
									new Object[] {
										2L, "member2@example.com", 1000000, "010-0000-0002", "Member02", "Kakao"
									});

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						for (int j = 0; j < members.get(i).length; j++) {
							ps.setObject(j + 1, members.get(i)[j]);
						}
					}

					@Override
					public int getBatchSize() {
						return members.size();
					}
				});
	}

	private void saveProducts() {
		String sql =
				"INSERT INTO product (id, name, price, created_by, created_at, modified_by, modified_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(
				sql,
				new BatchPreparedStatementSetter() {
					private final List<Object[]> products =
							List.of(
									new Object[] {
										1L,
										"Product 1",
										BigDecimal.valueOf(10000),
										1L,
										Timestamp.valueOf(LocalDateTime.now()),
										1L,
										Timestamp.valueOf(LocalDateTime.now())
									},
									new Object[] {
										2L,
										"Product 2",
										BigDecimal.valueOf(20000),
										1L,
										Timestamp.valueOf(LocalDateTime.now()),
										1L,
										Timestamp.valueOf(LocalDateTime.now())
									},
									new Object[] {
										3L,
										"Product 3",
										BigDecimal.valueOf(30000),
										1L,
										Timestamp.valueOf(LocalDateTime.now()),
										1L,
										Timestamp.valueOf(LocalDateTime.now())
									});

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						for (int j = 0; j < products.get(i).length; j++) {
							ps.setObject(j + 1, products.get(i)[j]);
						}
					}

					@Override
					public int getBatchSize() {
						return products.size();
					}
				});
	}

	private void savePaymentOrdersAndEvents() {
		String orderSql =
				"INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String eventSql =
				"INSERT INTO payment_event (id, buyer_id, order_id, order_name, payment_status, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

		savePaymentEvent(
				eventSql,
				List.of(
						new Object[] {
							1L,
							1L,
							"test-order-id-1",
							"Product 1, Product 2, Product 3",
							"READY",
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							2L,
							1L,
							"test-order-id-2",
							"Product 1, Product 2",
							"SUCCESS",
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							3L,
							1L,
							"test-order-id-3",
							"Product 1, Product 3",
							"FAIL",
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							4L,
							2L,
							"test-order-id-4",
							"Product 1",
							"SUCCESS",
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							5L,
							2L,
							"test-order-id-5",
							"Product 1, Product 2",
							"SUCCESS",
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						}));

		savePaymentOrders(
				orderSql,
				List.of(
						new Object[] {
							1L,
							1L,
							"test-order-id-1",
							"Product 1",
							BigDecimal.valueOf(10000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							2L,
							2L,
							"test-order-id-1",
							"Product 2",
							BigDecimal.valueOf(20000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							3L,
							3L,
							"test-order-id-1",
							"Product 3",
							BigDecimal.valueOf(30000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							4L,
							1L,
							"test-order-id-2",
							"Product 1",
							BigDecimal.valueOf(10000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							5L,
							2L,
							"test-order-id-2",
							"Product 2",
							BigDecimal.valueOf(20000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							6L,
							1L,
							"test-order-id-3",
							"Product 1",
							BigDecimal.valueOf(10000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							7L,
							3L,
							"test-order-id-3",
							"Product 3",
							BigDecimal.valueOf(30000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							8L,
							1L,
							"test-order-id-4",
							"Product 1",
							BigDecimal.valueOf(10000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							9L,
							1L,
							"test-order-id-5",
							"Product 1",
							BigDecimal.valueOf(10000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						},
						new Object[] {
							10L,
							2L,
							"test-order-id-5",
							"Product 2",
							BigDecimal.valueOf(20000),
							Timestamp.valueOf(LocalDateTime.now()),
							Timestamp.valueOf(LocalDateTime.now())
						}));
	}

	private void savePaymentOrders(String sql, List<Object[]> paymentOrders) {
		jdbcTemplate.batchUpdate(
				sql,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						for (int j = 0; j < paymentOrders.get(i).length; j++) {
							ps.setObject(j + 1, paymentOrders.get(i)[j]);
						}
					}

					@Override
					public int getBatchSize() {
						return paymentOrders.size();
					}
				});
	}

	private void savePaymentEvent(String sql, List<Object[]> paymentEvents) {
		jdbcTemplate.batchUpdate(
				sql,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						for (int j = 0; j < paymentEvents.get(i).length; j++) {
							ps.setObject(j + 1, paymentEvents.get(i)[j]);
						}
					}

					@Override
					public int getBatchSize() {
						return paymentEvents.size();
					}
				});
	}
}
