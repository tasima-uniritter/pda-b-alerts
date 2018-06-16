package br.edu.tasima.pda.b.alerts.repository;

import br.edu.tasima.pda.b.alerts.domain.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository about Engineer.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 */
@Repository
public interface EngineerRepository extends JpaRepository<Engineer, Integer> {
}
