package br.edu.tasima.pda.b.alerts.api.notify.business;

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

    public void sendAlertFromMonitor(MetricDTO metricPayload) throws Exception {
        try {
            LocalDateTime metricInstantDateTime = LocalDateTime.now();
            DayOfWeek metricInstantDayOfWeek = DayOfWeek.from(metricInstantDateTime);
            LocalTime metricInstantTime = LocalTime.from(metricInstantDateTime);

            Team team = teamBO.getByMetricCode(metricPayload.getMetric());

            for (Engineer engineer : team.getEngineers()) {
                List<Agenda> engineerAgenda = engineer.getEngineerAgenda();
                for (Agenda agenda : engineerAgenda) {
                    if (agenda.getWeekDay().equals(metricInstantDayOfWeek)) {
                        if (isEngineerOnCall(agenda, metricInstantTime)) {
                            System.out.println(engineer);
                        }
                    }
                }
            }

        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
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
