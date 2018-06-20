package br.edu.tasima.pda.b.alerts.api.v1.notify.repository;

import br.edu.tasima.pda.b.alerts.api.v1.notify.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository about Team.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
