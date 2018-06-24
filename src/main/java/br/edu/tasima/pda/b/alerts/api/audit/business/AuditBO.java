package br.edu.tasima.pda.b.alerts.api.audit.business;

import br.edu.tasima.pda.b.alerts.api.audit.model.Audit;
import br.edu.tasima.pda.b.alerts.api.audit.model.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.audit.repository.AuditRepository;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import br.edu.tasima.pda.b.alerts.helper.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Business about Audit.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 24/06/2018 12:05:00
 */
@Component
public class AuditBO {

    private final AuditRepository repository;

    public AuditBO(AuditRepository repository) {
        this.repository = repository;
    }

    public void saveAudit(MetricDTO metricPayload, Team team, Engineer engineer, NotifyStatus status)
            throws JsonProcessingException {
        Audit audit = new Audit();
        audit.setEngineer(engineer);
        audit.setStatus(status);
        audit.setMetricContent(JsonConverter.objectToJson(metricPayload));
        audit.setTeam(team);
        audit.setTriggerTimestamp(LocalDateTime.now());
        repository.save(audit);
    }

    public void saveAuditFailure(MetricDTO metricPayload) throws JsonProcessingException {
        Audit audit = new Audit();
        audit.setStatus(NotifyStatus.FAILURE);
        audit.setMetricContent(JsonConverter.objectToJson(metricPayload));
        audit.setTriggerTimestamp(LocalDateTime.now());
        repository.save(audit);
    }
}
