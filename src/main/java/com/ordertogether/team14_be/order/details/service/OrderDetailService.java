package com.ordertogether.team14_be.order.details.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRequestDto;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailResponseDto;
import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.order.details.repository.OrderDetailRepository;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

	private final OrderDetailRepository orderDetailRepository;
	private final MemberRepository memberRepository;
	private final SpotRepository spotRepository;

	// 주문 상세 정보 생성 메서드
	public CreateOrderDetailResponseDto createOrderDetail(
			CreateOrderDetailRequestDto createOrderDetailRequestDto) {

		// 참여자 본인 정보 설정
		Member member =
				memberRepository
						.findById(createOrderDetailRequestDto.getParticipantId())
						.orElseThrow(() -> new IllegalArgumentException("참여자 정보가 없습니다."));

		// 스팟 정보 설정
		SpotDto spot =
				spotRepository.findByIdAndIsDeletedFalse(createOrderDetailRequestDto.getSpotId());

		OrderDetail orderDetail =
				OrderDetail.builder()
						.member(member)
						//						.spot(spot)
						.price(createOrderDetailRequestDto.getPrice())
						.isPayed(createOrderDetailRequestDto.isPayed())
						.build();

		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

		return CreateOrderDetailResponseDto.builder()
				.id(savedOrderDetail.getId())
				.price(savedOrderDetail.getPrice())
				.isPayed(savedOrderDetail.isPayed())
				.participantName(savedOrderDetail.getMember().getDeliveryName())
				.spotName(spot.getStoreName())
				.build();
	}
}
