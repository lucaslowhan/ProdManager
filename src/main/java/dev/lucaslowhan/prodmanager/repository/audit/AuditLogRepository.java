package dev.lucaslowhan.prodmanager.repository.audit;

import dev.lucaslowhan.prodmanager.domain.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
