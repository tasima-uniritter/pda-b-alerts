package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.audit.business.AuditBO;
import br.edu.tasima.pda.b.alerts.api.audit.model.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Agenda;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlertBOTest {
    @Autowired
    private AlertBO alertBO;

    @MockBean
    private TeamBO teamBO;
    @MockBean
    private AuditBO auditBO;

    @Test
    public void sendAlertFromMonitorSuccessTest() throws Exception {
        MetricDTO metricDTO = new MetricDTO();
        metricDTO.setMetric("memory");
        metricDTO.setMetricThreshold(90);
        metricDTO.setMetricTimestamp(LocalDateTime.now());
        metricDTO.setOrigin("ip-origin");
        metricDTO.setRule("memory-full");
        metricDTO.setValue(90);

        MetricCode metricCode = new MetricCode("memory", "memory-full");

        Agenda agenda = new Agenda(1L, null, LocalDateTime.now().getDayOfWeek(), LocalTime.MIN, LocalTime.MAX);

        ArrayList<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);

        Engineer engineer = new Engineer(1L, null, "John Johnes", "jj@gmail.com", agendaList);

        ArrayList<Engineer> engineerList = new ArrayList<>();
        engineerList.add(engineer);

        Team team = new Team(1L, metricCode, "Suport", engineerList);


        given(teamBO.getByMetricCode(any())).willReturn(team);
        doNothing().when(auditBO).saveAudit(any(MetricDTO.class), any(Team.class), any(Engineer.class), NotifyStatus.SUCCESS);

        alertBO.sendAlertFromMonitor(metricDTO);
    }
}
