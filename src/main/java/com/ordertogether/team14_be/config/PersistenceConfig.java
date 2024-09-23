package com.ordertogether.team14_be.config;

import com.ordertogether.team14_be.common.persistence.auditing.AuditorProvider;
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
}
