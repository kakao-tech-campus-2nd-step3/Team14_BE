INSERT INTO member (id, email, point, phone_number, delivery_name, platform) VALUES (1, 'member1@example.com', 1000000, '010-0000-0001', 'Member01', 'Kakao');
INSERT INTO member (id, email, point, phone_number, delivery_name, platform) VALUES (2, 'member2@example.com', 1000000, '010-0000-0002', 'Member02', 'Kakao');

INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (1, 'Product 1', 10000, now(), now(), 1, 1);
INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (2, 'Product 2', 20000, now(), now(), 1, 1);
INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (3, 'Product 3', 30000, now(), now(), 1, 1);

INSERT INTO payment_event (id, buyer_id, created_at, modified_at, order_id, order_name, payment_key, payment_status) VALUES (1, 1, now(), now(), 'test-order-id-1', 'Product 1, Product 2, Product 3', 'test-payment-key-1', 'SUCCESS');
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (1, 1, 'test-order-id-1', 'Product 1', 10000, now(), now());
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (2, 2, 'test-order-id-1', 'Product 2', 20000, now(), now());
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (3, 3, 'test-order-id-1', 'Product 3', 30000, now(), now());

INSERT INTO payment_event (id, buyer_id, created_at, modified_at, order_id, order_name, payment_key, payment_status) VALUES (2, 1, now(), now(), 'test-order-id-2', 'Product 1, Product 2', 'test-payment-key-2', 'SUCCESS');
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (4, 1, 'test-order-id-2', 'Product 1', 10000, now(), now());
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (5, 2, 'test-order-id-2', 'Product 2', 20000, now(), now());

INSERT INTO payment_event (id, buyer_id, created_at, modified_at, order_id, order_name, payment_key, payment_status) VALUES (3, 1, now(), now(), 'test-order-id-3', 'Product 1, Product 3', 'test-payment-key-3', 'FAIL');
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (6, 1, 'test-order-id-3', 'Product 1', 10000, now(), now());
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (7, 3, 'test-order-id-3', 'Product 3', 30000, now(), now());

INSERT INTO payment_event (id, buyer_id, created_at, modified_at, order_id, order_name, payment_key, payment_status) VALUES (4, 2, now(), now(), 'test-order-id-4', 'Product 1', 'test-payment-key-4', 'SUCCESS');
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (8, 1, 'test-order-id-4', 'Product 1', 10000, now(), now());

INSERT INTO payment_event (id, buyer_id, created_at, modified_at, order_id, order_name, payment_key, payment_status) VALUES (5, 2, now(), now(), 'test-order-id-5', 'Product 1, Product 2', 'test-payment-key-5', 'SUCCESS');
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (9, 1, 'test-order-id-5', 'Product 1', 10000, now(), now());
INSERT INTO payment_order (id, product_id, order_id, order_name, amount, created_at, modified_at) VALUES (10, 2, 'test-order-id-5', 'Product 2', 20000, now(), now());
