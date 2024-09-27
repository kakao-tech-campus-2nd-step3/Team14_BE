package com.ordertogether.team14_be.helper.jpa;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("test")
public class JpaDatabaseCleanup implements InitializingBean {

	@PersistenceContext private EntityManager entityManager;

	private List<String> tableNames;

	@Override
	public void afterPropertiesSet() {
		tableNames =
				entityManager.getMetamodel().getEntities().stream()
						.filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
						.map(e -> removePostfix(e.getName(), "Entity"))
						.map(SnakeCaseStrategy.INSTANCE::translate)
						.toList();
	}

	private String removePostfix(String entityName, String postfix) {
		if (entityName.endsWith(postfix)) {
			return entityName.substring(0, entityName.length() - postfix.length());
		}
		return entityName;
	}

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
			entityManager
					.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")
					.executeUpdate();
		}
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
	}
}
