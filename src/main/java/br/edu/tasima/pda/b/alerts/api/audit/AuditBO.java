package br.edu.tasima.pda.b.alerts.api.audit;

import br.edu.tasima.pda.b.alerts.api.audit.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class AuditBO {
    private final AuditRepository auditRepository;

    public void save(Engineer engineer, MetricDTO metricContent, NotifyStatus success, String message) {
        Audit audit = new Audit();
        audit.setEngineer(engineer);
        audit.setMetricContent(metricContent.toString());
        audit.setStatus(success);
        audit.setTriggerTimestamp(LocalDateTime.now());
        audit.setMessage(message);

        this.auditRepository.save(audit);
    }

    List<Audit> getByMetricCode(String metricCode) {
        return auditRepository.findAllByEngineer_Team_MetricCode_Code(metricCode);
    }

    List<Audit> getByEngineer(Long engineerId) {
        return auditRepository.findAllByEngineer_EngineerId(engineerId);
    }

    List<Audit> findAll() {
        return auditRepository.findAll();
    }


}