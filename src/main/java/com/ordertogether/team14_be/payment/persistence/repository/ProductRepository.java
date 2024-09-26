package com.ordertogether.team14_be.payment.persistence.repository;

import com.ordertogether.team14_be.payment.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

	Product save(Product product);

	List<Product> saveAll(List<Product> products);

	Optional<Product> findById(Long id);

	List<Product> findByIdIn(List<Long> ids);
}
