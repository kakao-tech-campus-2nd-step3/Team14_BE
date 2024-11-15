package com.ordertogether.team14_be.order.details.repository;

import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.spot.entity.Spot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	@EntityGraph(attributePaths = {"spot"})
	Page<OrderDetail> findByMember(Member member, Pageable pageable);

	Optional<OrderDetail> findBySpotAndMember(Spot spot, Member member);

	List<OrderDetail> findAllBySpot(Spot spot);
}
