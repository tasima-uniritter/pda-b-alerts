package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import br.edu.tasima.pda.b.alerts.api.notify.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class TeamBO {
    private final TeamRepository teamRepository;

    public Team save(Team team) {
        return this.teamRepository.save(team);
    }

    public void delete(Long id) {
        this.teamRepository.deleteById(id);
    }

    public Team getById(Long id) throws EntityNotFoundException {
        return this.teamRepository.getOne(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team getByMetricCode(String metricCode) {
        Team team = teamRepository.findByMetricCode_Code(metricCode);

        if (team == null) {
            throw new EntityNotFoundException("Cannot find a team responsible for metric \"" + metricCode + "\"");
        }
        return team;
    }
}
