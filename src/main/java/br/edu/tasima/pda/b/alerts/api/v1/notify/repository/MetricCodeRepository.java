package br.edu.tasima.pda.b.alerts.api.v1.notify.repository;

import br.edu.tasima.pda.b.alerts.api.v1.notify.model.MetricCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository about MetricCode.
 *
 * @author Lucas_Gentile
 * @since 20/06/2018 07:05:00
 */
@Repository
public interface MetricCodeRepository extends JpaRepository<MetricCode, String> {
}
