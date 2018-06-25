package br.edu.tasima.pda.b.alerts.api.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository about Audit.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findAllByEngineer_Team_MetricCode_Code(String metricCode);
    List<Audit> findAllByEngineer_EngineerId(Long engineerId);
}
