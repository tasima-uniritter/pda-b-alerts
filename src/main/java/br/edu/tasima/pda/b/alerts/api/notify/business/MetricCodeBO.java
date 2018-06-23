package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.repository.MetricCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class MetricCodeBO {
    private final MetricCodeRepository metricCodeRepository;

    public MetricCode save(MetricCode metricCode) {
        return this.metricCodeRepository.save(metricCode);
    }

    public void delete(String code) {
        this.metricCodeRepository.deleteById(code);
    }

    public MetricCode getByCode(String code) throws EntityNotFoundException {
        return this.metricCodeRepository.getOne(code);
    }

    public List<MetricCode> findAll() {
        return metricCodeRepository.findAll();
    }

}
