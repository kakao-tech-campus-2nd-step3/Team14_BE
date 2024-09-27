package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.domain.Product;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.ProductEntity;
import com.ordertogether.team14_be.payment.persistence.jpa.mapper.ProductMapper;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {

	private final SimpleJpaProductRepository simpleJpaProductRepository;

	@Override
	public Product save(Product product) {
		ProductEntity savedProduct =
				simpleJpaProductRepository.save(ProductMapper.mapToEntity(product));
		return ProductMapper.mapToDomain(savedProduct);
	}

	@Override
	public List<Product> saveAll(List<Product> products) {
		return simpleJpaProductRepository
				.saveAll(products.stream().map(ProductMapper::mapToEntity).toList())
				.stream()
				.map(ProductMapper::mapToDomain)
				.toList();
	}

	@Override
	public Optional<Product> findById(Long id) {
		return simpleJpaProductRepository.findById(id).map(ProductMapper::mapToDomain);
	}

	@Override
	public List<Product> findByIdIn(List<Long> ids) {
		return simpleJpaProductRepository.findByIdIn(ids).stream()
				.map(ProductMapper::mapToDomain)
				.toList();
	}
}
