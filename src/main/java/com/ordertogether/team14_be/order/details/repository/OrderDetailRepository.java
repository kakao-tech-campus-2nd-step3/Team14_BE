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
	Page<OrderDetail> findAllByMember(Member member, Pageable pageable);

	Optional<OrderDetail> findFirstBySpotAndMember(
			Spot spot, Member member); // 한 스팟에 여러 멤버가 못들어가는지 확인하고 First빼야 함

	List<OrderDetail> findAllBySpot(Spot spot);
}
