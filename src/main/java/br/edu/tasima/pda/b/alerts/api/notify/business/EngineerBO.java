package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import br.edu.tasima.pda.b.alerts.api.notify.repository.EngineerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class EngineerBO {

    private final EngineerRepository engineerRepository;
    private final TeamBO teamBO;

    public Engineer save(Engineer engineer) {
        return this.engineerRepository.save(engineer);
    }

    public void delete(Long id) {
        this.engineerRepository.deleteById(id);
    }

    public Engineer getById(Long id) throws EntityNotFoundException {
        return this.engineerRepository.getOne(id);
    }

    public Engineer getByName(String engineerName) {
        return this.engineerRepository.findEngineerByNameIgnoreCase(engineerName);
    }

    public List<Engineer> findEgineersByTeam(Long teamId) {
        return this.engineerRepository.findAllByTeam_TeamId(teamId);
    }

    public Engineer assignEngineerTeam(Long engineerId, Long teamId) {
        Engineer engineerAssignedToTeam;
        try {
            Engineer engineer = getById(engineerId);
            Team team = teamBO.getById(teamId);

            engineer.setTeam(team);

            engineerAssignedToTeam = save(engineer);

        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(exception.getMessage());
        }

        return engineerAssignedToTeam;
    }
}
