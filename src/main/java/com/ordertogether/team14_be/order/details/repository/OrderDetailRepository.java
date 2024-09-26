package com.ordertogether.team14_be.order.details.repository;

import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {}
