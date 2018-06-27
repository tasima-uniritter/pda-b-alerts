package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.audit.AuditBO;
import br.edu.tasima.pda.b.alerts.api.audit.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Agenda;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

//    TODO Refactor this method due to some changes in the code
    @Test
    public void sendAlertFromMonitorSuccessTest() throws Exception {
        MetricDTO metricDTO = new MetricDTO();
        metricDTO.setMetric("memory");
        metricDTO.setThreshold(90);
        metricDTO.setTimestamp("2018-06-27T03:47:55.038Z");
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
        doNothing().when(auditBO).save(any(Engineer.class), any(MetricDTO.class), NotifyStatus.SUCCESS, any(String.class));

        alertBO.sendAlertFromMonitor(metricDTO);
    }
}
