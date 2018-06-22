package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.repository.EngineerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@AllArgsConstructor
public class EngineerBO {
    private final EngineerRepository engineerRepository;

    public Engineer save(Engineer engineer) {
        return this.engineerRepository.save(engineer);
    }

    public void delete(Long engineerId) {
        this.engineerRepository.deleteById(engineerId);
    }

    public Engineer getById(Long engineerId) {
        Engineer engineer;
        try {
            engineer = this.engineerRepository.getOne(engineerId);
        } catch (EntityNotFoundException exception) {
            engineer = null;
        }

        return engineer;
    }

    public Engineer getByName(String engineerName) {
        return this.engineerRepository.findEngineerByNameIgnoreCase(engineerName);
    }
}
