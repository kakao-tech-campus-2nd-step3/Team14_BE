package com.ordertogether.team14_be.spot.repository;

import com.ordertogether.team14_be.spot.entity.Spot;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleSpotRepository extends JpaRepository<Spot, Long> {
	List<Spot> findByLatAndLngAndIsDeletedFalse(BigDecimal lat, BigDecimal lng);

	Optional<Spot> findByIdAndIsDeletedFalse(Long id);

	@Query(
			value =
					"SELECT * FROM spot WHERE SUBSTRING(geo_hash, 1, 5) = :geoHash AND is_deleted = false",
			nativeQuery = true)
	List<Spot> findByGeoHash(@Param("geoHash") String geoHash);

	List<Spot> findByMemberIdAndIsDeletedFalse(Long memberId);
}
