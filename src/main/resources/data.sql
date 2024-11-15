INSERT INTO member (id, email, point, phone_number, delivery_name, platform) VALUES (1, 'member1@example.com', 1000000, '010-0000-0001', 'Member01', 'Kakao');
INSERT INTO member (id, email, point, phone_number, delivery_name, platform) VALUES (2, 'member2@example.com', 1000000, '010-0000-0002', 'Member02', 'Kakao');

INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (1, 'Product 1', 5000, now(), now(), 1, 1);
INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (2, 'Product 1', 10000, now(), now(), 1, 1);
INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (3, 'Product 2', 20000, now(), now(), 1, 1);
INSERT INTO product (id, name, price, created_at, modified_at, created_by, modified_by) VALUES (4, 'Product 3', 30000, now(), now(), 1, 1);

INSERT INTO spot (master_id, lat, lng, category, store_name, minimum_order_amount, together_order_link, pick_up_location, deadline_time, geo_hash, is_deleted, created_at, modified_at, created_by, modified_by)
VALUES (1, 35.1766787, 126.9054188, '001', '떡볶이 천국', 10000, 'https://example.com/order/1', '정문 앞', '21:30:00', 'wy60pg9xn84v', false, now(), now(), 1, 2);

INSERT INTO spot (master_id, lat, lng, category, store_name, minimum_order_amount, together_order_link, pick_up_location, deadline_time, geo_hash, is_deleted, created_at, modified_at, created_by, modified_by)
VALUES (2, 35.1766787, 126.9054188, '001', '김밥의 집', 8000, 'https://example.com/order/2', '후문 앞', '20:00:00', 'wy60pg9xn84v', false, now(), now(), 2, 3);

INSERT INTO spot (master_id, lat, lng, category, store_name, minimum_order_amount, together_order_link, pick_up_location, deadline_time, geo_hash, is_deleted, created_at, modified_at, created_by, modified_by)
VALUES (1, 35.1766787, 126.9054188, '001', '순대나라', 5000, 'https://example.com/order/3', '주차장', '22:00:00', 'wy60pg9xn84v', true, now(), now(), 3, 1);

INSERT INTO spot (master_id, lat, lng, category, store_name, minimum_order_amount, together_order_link, pick_up_location, deadline_time, geo_hash, is_deleted, created_at, modified_at, created_by, modified_by)
VALUES (2, 35.1766787, 126.9054188, '001', '튀김천국', 12000, 'https://example.com/order/4', '엘리베이터 앞', '23:00:00', 'wy60pg9xn84v', false, now(), now(), 4, 5);
