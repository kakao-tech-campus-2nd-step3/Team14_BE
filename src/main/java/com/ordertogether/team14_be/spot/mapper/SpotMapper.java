package com.ordertogether.team14_be.spot.mapper;

import com.ordertogether.team14_be.spot.dto.controllerdto.*;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.enums.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
		componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE) // Spring Bean으로 등록
public interface SpotMapper {
	SpotMapper INSTANCE = Mappers.getMapper(SpotMapper.class); // 객체 생성해서 INSTANCE에 할당

	SpotDto toDto(Spot spot);

	@Mapping(target = "category", ignore = true) // category는 무시
	SpotDto toSpotDto(SpotCreationRequest spotCreationRequest);

	Spot toEntity(SpotDto spotDto);

	Spot toEntity(SpotDto spotDto, @MappingTarget Spot spot); // 생성 또는 수정할 때 사용

	@BeanMapping(ignoreByDefault = false) // 자동 매핑 활성화
	@Mapping(target = "category", expression = "java(spotDto.getCategory().getStringCategory())")
	SpotCreationResponse toSpotCreationResponse(SpotDto spotDto);

	@BeanMapping(ignoreByDefault = false) // 자동 매핑 활성화
	@Mapping(target = "category", expression = "java(spotDto.getCategory().getStringCategory())")
	SpotDetailResponse toSpotDetailResponse(SpotDto spotDto);

	@BeanMapping(ignoreByDefault = false) // 자동 매핑 활성화
	@Mapping(target = "category", expression = "java(spotDto.getCategory().getStringCategory())")
	SpotViewedResponse toSpotViewedResponse(SpotDto spotDto);

	SpotModifyRequest toSpotModifyRequest(SpotDto spotDto);

	@Mapping(target = "category", ignore = true) // category는 무시
	SpotDto toSpotDto(SpotModifyRequest spotModifyRequest);

	@AfterMapping
	default void mapToCategory(
			SpotCreationRequest spotCreationRequest, @MappingTarget SpotDto spotDto) {
		spotDto.setCategory(
				Category.fromStringToEnum(spotCreationRequest.category())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")));
	}
}
