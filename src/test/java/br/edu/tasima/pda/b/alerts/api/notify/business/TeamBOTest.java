package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import br.edu.tasima.pda.b.alerts.api.notify.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TeamBOTest {
    @Autowired
    private TeamBO teamBO;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void getByMetricCodeSuccessTest(){
        MetricCode metricCode = new MetricCode("memory", "80");
        Team team = new Team(1L, metricCode, "Suport", null);
        given(teamRepository.findByMetricCode_Code("memory")).willReturn(team);

        Team found = teamBO.getByMetricCode("memory");
        assertNotNull(found);
        assertEquals(1L, team.getTeamId().longValue());
        assertEquals(metricCode.getCode(), team.getMetricCode().getCode());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByMetricCodeFailTest(){
        given(teamRepository.findByMetricCode_Code("memory")).willReturn(null);
        Team found = teamBO.getByMetricCode("memory");
    }
}
