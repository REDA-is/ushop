// src/main/java/org/reda/ushop/repo/WebhookEventLogRepository.java
package org.reda.ushop.repo;

import org.reda.ushop.entities.WebhookEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLog, String> {}

