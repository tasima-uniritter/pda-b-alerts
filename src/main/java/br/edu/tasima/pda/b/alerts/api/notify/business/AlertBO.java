package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.audit.AuditBO;
import br.edu.tasima.pda.b.alerts.api.audit.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Agenda;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class AlertBO {
    private TeamBO teamBO;
    private JavaMailSender mailSender;
    private AuditBO auditBO;

    public void sendAlertFromMonitor(MetricDTO metricPayload) throws Exception {
        try {
            LocalDateTime metricInstantDateTime = LocalDateTime.now();
            DayOfWeek metricInstantDayOfWeek = DayOfWeek.from(metricInstantDateTime);
            LocalTime metricInstantTime = LocalTime.from(metricInstantDateTime);

            boolean isEngineerOnCallAvailable = false;

            Team team = teamBO.getByMetricCode(metricPayload.getMetric());

            for (Engineer engineer : team.getEngineers()) {
                List<Agenda> engineerAgenda = engineer.getEngineerAgenda();
                for (Agenda agenda : engineerAgenda) {
                    if (agenda.getWeekDay().equals(metricInstantDayOfWeek)) {
                        if (isEngineerOnCall(agenda, metricInstantTime)) {
                            sendAlertEmail(metricPayload, engineer);
                            isEngineerOnCallAvailable = true;
                        }
                    }
                }
            }

            if (!isEngineerOnCallAvailable) {
                auditBO.save(null, metricPayload, NotifyStatus.FAILURE, "No Engineers available at this moment!.");
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

    private void sendAlertEmail(MetricDTO metricContent, Engineer engineerOnCall) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("[TDA-B-ALERT] Container " + metricContent.getOrigin() + " needs your attention!");
            message.setText("Dear Engineer \"" + engineerOnCall.getName() + "\" from Team \"" + engineerOnCall.getTeam().getName() + "\" please check the following alert: \n\n" + metricContent.toString());
            message.setTo(engineerOnCall.getEmail());
            message.setFrom("TDA-B-ALERT@gmail.com");

            mailSender.send(message);

            auditBO.save(engineerOnCall, metricContent, NotifyStatus.SUCCESS, "Email sent.");
        } catch (Exception exception) {
            auditBO.save(engineerOnCall, metricContent, NotifyStatus.FAILURE, exception.getMessage());
        }
    }
}
