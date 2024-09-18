package com.ordertogether.team14_be.Service;

import com.ordertogether.team14_be.DTO.MemberDto;
import com.ordertogether.team14_be.DTO.SpotDto;
import com.ordertogether.team14_be.Entity.Spot;
import com.ordertogether.team14_be.Repository.SpotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotService {

    private final SpotRepository spotRepository;
    private final MemberService memberService;

    @Autowired
    public SpotService(SpotRepository spotRepository, MemberService memberService) {
        this.spotRepository = spotRepository;
        this.memberService = memberService;
    }

    //Spot 전체 조회하기
    public List<SpotDto> getSpot(BigDecimal lat, BigDecimal lng) {
        return spotRepository.findByLatAndLng(lat, lng)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SpotDto createSpot(SpotDto spotDto) {
        Spot spot = spotDto.toEntity();
        return toDto(spotRepository.save(spot));
    }

    //Spot 상세 조회하기
    public SpotDto getSpot(Long id) {
        Spot spot = spotRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Spot not found"));
        return toDto(spot);
    }

    public SpotDto updateSpot(SpotDto spotDto) {
        Spot spot = spotRepository.save(spotDto.toEntity());
        return toDto(spot);
    }

    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }

    //Service Layer에서 toDto만들어서 매핑시키기
    public SpotDto toDto(Spot spotInStream) {
        Spot spot = spotRepository.findById(spotInStream.getId()).orElseThrow(() -> new EntityNotFoundException("Spot not found"));
        MemberDto memberDto = memberService.getMemberDto(spot.getMember().getId());

        return SpotDto.builder()
                .id(spot.getId())
                .lat(spot.getLat())
                .lng(spot.getLng())
                .memberDto(memberDto)
                .category(spot.getCategory())
                .store_name(spot.getStore_name())
                .minimum_order_amount(spot.getMinimum_order_amount())
                .together_order_link(spot.getTogether_order_link())
                .pick_up_location(spot.getPick_up_location())
                .delivery_status(spot.getDelivery_status())
                .build();

    }
}
