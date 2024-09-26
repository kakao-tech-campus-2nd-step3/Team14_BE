package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.persistence.jpa.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleJpaProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByIdIn(List<Long> ids);
}
