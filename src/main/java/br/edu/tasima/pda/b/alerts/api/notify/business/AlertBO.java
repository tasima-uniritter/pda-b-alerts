package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.audit.business.AuditBO;
import br.edu.tasima.pda.b.alerts.api.audit.model.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Agenda;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class AlertBO {

    private TeamBO teamBO;
    private AuditBO auditBO;

    /**
     * Send alert from monitor.
     *
     * @param metricPayload
     * @throws Exception
     */
    public void sendAlertFromMonitor(MetricDTO metricPayload) throws Exception {
        try {
            LocalDateTime metricInstantDateTime = LocalDateTime.now();
            DayOfWeek metricInstantDayOfWeek = DayOfWeek.from(metricInstantDateTime);
            LocalTime metricInstantTime = LocalTime.from(metricInstantDateTime);

            Team team = teamBO.getByMetricCode(metricPayload.getMetric());
            boolean hasNotify = Boolean.FALSE;

            for (Engineer engineer : team.getEngineers()) {
                List<Agenda> engineerAgenda = engineer.getEngineerAgenda();
                for (Agenda agenda : engineerAgenda) {
                    if (metricInstantDayOfWeek.equals(agenda.getWeekDay())) {
                        if (isEngineerOnCall(agenda, metricInstantTime)) {
                            NotifyStatus status = notify(engineer);
                            auditBO.saveAudit(metricPayload, team, engineer, status);
                            hasNotify = Boolean.TRUE;
                        }
                    }
                }
            }

            if(!hasNotify){
                auditBO.saveAuditFailure(metricPayload);
            }

        } catch (Exception exception) {
            auditBO.saveAuditFailure(metricPayload);
        }
    }

    // TODO: if error, return NotifyStatus.FAILURE
    private NotifyStatus notify(Engineer engineer) {
        System.out.println(engineer);
        return NotifyStatus.SUCCESS;
    }

    private boolean isEngineerOnCall(Agenda dailyAgenda, LocalTime metricInstantTime) {
        LocalTime startOnCall = dailyAgenda.getStartTime();
        LocalTime endOnCall = dailyAgenda.getEndTime();
        if (startOnCall.isAfter(endOnCall)) {
            return !metricInstantTime.isBefore(startOnCall) || !metricInstantTime.isAfter(endOnCall);
        } else {
            return !metricInstantTime.isBefore(startOnCall) && !metricInstantTime.isAfter(endOnCall);
        }
    }
}
