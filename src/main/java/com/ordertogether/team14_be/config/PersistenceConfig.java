package com.ordertogether.team14_be.config;

import com.ordertogether.team14_be.common.persistence.auditing.AuditorProvider;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.JpaPaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.JpaPaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.JpaProductRepository;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.SimpleJpaPaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.SimpleJpaPaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.jpa.repository.SimpleJpaProductRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
/** 영속성과 관련된 설정을 담당하는 클래스 */
public class PersistenceConfig {

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new AuditorProvider();
	}

	@Bean
	public PaymentEventRepository paymentEventRepository(
			SimpleJpaPaymentEventRepository simpleJpaPaymentEventRepository,
			SimpleJpaPaymentOrderRepository simpleJpaPaymentOrderRepository,
			SimpleJpaProductRepository simpleJpaProductRepository) {
		return new JpaPaymentEventRepository(
				simpleJpaPaymentEventRepository,
				paymentOrderRepository(simpleJpaPaymentOrderRepository, simpleJpaProductRepository));
	}

	@Bean
	public PaymentOrderRepository paymentOrderRepository(
			SimpleJpaPaymentOrderRepository simpleJpaPaymentOrderRepository,
			SimpleJpaProductRepository simpleJpaProductRepository) {
		return new JpaPaymentOrderRepository(
				simpleJpaPaymentOrderRepository, simpleJpaProductRepository);
	}

	@Bean
	public ProductRepository productRepository(
			SimpleJpaProductRepository simpleJpaProductRepository) {
		return new JpaProductRepository(simpleJpaProductRepository);
	}
}
