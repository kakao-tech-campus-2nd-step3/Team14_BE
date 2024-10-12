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
			"SELECT sp FROM Spot sp WHERE sp.lat <= :maxlat and sp.lat >= :minlat and sp.lng <= :maxlng and sp.lng >= :minlng and sp.isDeleted = false")
	List<Spot> findAroundSpotAndIsDeletedFalse(
			@Param("maxlat") BigDecimal maxlat,
			@Param("maxlng") BigDecimal maxlng,
			@Param("minlat") BigDecimal minlat,
			@Param("minlng") BigDecimal minlng);
}
