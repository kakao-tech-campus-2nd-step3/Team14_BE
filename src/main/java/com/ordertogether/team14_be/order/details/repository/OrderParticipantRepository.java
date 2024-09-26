package com.ordertogether.team14_be.order.details.repository;

import com.ordertogether.team14_be.order.details.entity.OrderParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderParticipantRepository extends JpaRepository<OrderParticipant, Long> {}
