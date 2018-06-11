package br.edu.tasima.pda.b.alerts.repositories;

import br.edu.tasima.pda.b.alerts.entities.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository about Agenda.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 */
@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
