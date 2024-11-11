package com.ordertogether.team14_be.spot.repository;

import com.ordertogether.team14_be.spot.entity.Spot;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleSpotRepository extends JpaRepository<Spot, Long> {
	List<Spot> findByLatAndLngAndIsDeletedFalse(BigDecimal lat, BigDecimal lng);

	Optional<Spot> findByIdAndIsDeletedFalse(Long id);

	List<Spot> findByGeoHash(String geoHash);
}
