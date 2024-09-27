package com.ordertogether.team14_be.order.details.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRequestDto;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailResponseDto;
import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.order.details.repository.OrderDetailRepository;
import com.ordertogether.team14_be.order.details.repository.OrderParticipantRepository;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

	private final OrderDetailRepository orderDetailRepository;
	private final OrderParticipantRepository orderParticipantRepository;
	private final MemberRepository MemberRepository;
	private final SpotRepository spotRepository;

	public OrderDetailService(
			OrderDetailRepository orderDetailRepository,
			OrderParticipantRepository orderParticipantRepository,
			com.ordertogether.team14_be.member.persistence.MemberRepository memberRepository,
			SpotRepository spotRepository) {
		this.orderDetailRepository = orderDetailRepository;
		this.orderParticipantRepository = orderParticipantRepository;
		MemberRepository = memberRepository;
		this.spotRepository = spotRepository;
	}

	// 주문 상세 정보 생성 메서드
	public CreateOrderDetailResponseDto createOrderDetail(
			CreateOrderDetailRequestDto createOrderDetailRequestDto) {
		// 방장 정보 설정
		Member leader =
				MemberRepository.findById(createOrderDetailRequestDto.getLeaderId())
						.orElseThrow(() -> new IllegalArgumentException("방장 정보가 없습니다."));

		// 스팟 정보 설정
		Spot spot =
				spotRepository
						.findById(createOrderDetailRequestDto.getSpotId())
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보를 찾을 수 없습니다."));

		OrderDetail orderDetail =
				OrderDetail.builder()
						.leader(leader)
						.spot(spot)
						.price(createOrderDetailRequestDto.getPrice())
						.isPayed(createOrderDetailRequestDto.isPayed())
						.build();

		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

		return CreateOrderDetailResponseDto.builder()
				.id(savedOrderDetail.getId())
				.price(savedOrderDetail.getPrice())
				.isPayed(savedOrderDetail.isPayed())
				.leaderName(leader.getDeliveryName())
				.spotName(spot.getStoreName())
				.build();
	}
}
