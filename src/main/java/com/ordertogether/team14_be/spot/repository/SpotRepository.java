package com.ordertogether.team14_be.spot.repository;

import com.ordertogether.team14_be.spot.entity.Spot;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
	List<Spot> findByLatAndLng(BigDecimal lat, BigDecimal lng);
}
