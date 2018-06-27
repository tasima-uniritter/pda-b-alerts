package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import br.edu.tasima.pda.b.alerts.api.notify.repository.EngineerRepository;
import br.edu.tasima.pda.b.alerts.api.notify.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EngineerBOTest {
    @Autowired
    private EngineerBO engineerBO;
    @Autowired
    private TeamBO teamBO;

    @MockBean
    private EngineerRepository engineerRepository;
    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void assignEngineerTeamSucessTest(){
        Engineer engineer = new Engineer(1L, null, "Hector Martinez", "hmartinez@gmail.com", null);
        Team team = new Team(1L, new MetricCode("disk", "80"), "Suport", null);

        given(engineerRepository.getOne(1L)).willReturn(engineer);
        given(teamRepository.getOne(1L)).willReturn(team);
        given(engineerRepository.save(any(Engineer.class))).will(AdditionalAnswers.returnsFirstArg());

        Engineer result = engineerBO.assignEngineerTeam(1L, 1L);
        assertNotNull(result);
        assertEquals(result.getEngineerId().longValue(), engineer.getEngineerId().longValue());
        assertNotNull(result.getTeam());
        assertEquals(result.getTeam().getTeamId().longValue(), team.getTeamId().longValue());
    }

    @Test
    public void assignEngineerTeamNotFoundTeamTest(){
        Engineer engineer = new Engineer(1L, null, "Hector Martinez", "hmartinez@gmail.com", null);

        given(engineerRepository.getOne(1L)).willReturn(engineer);
        given(teamRepository.getOne(2L)).willReturn(null);
        given(engineerRepository.save(any(Engineer.class))).will(AdditionalAnswers.returnsFirstArg());

        Engineer result = engineerBO.assignEngineerTeam(1L, 2L);
        assertNotNull(result);
        assertEquals(result.getEngineerId().longValue(), engineer.getEngineerId().longValue());
        assertNull(result.getTeam());
    }

    @Test(expected = EntityNotFoundException.class)
    public void assignEngineerTeamFailTest(){
        given(engineerRepository.getOne(1L)).willThrow(EntityNotFoundException.class);
        Engineer result = engineerBO.assignEngineerTeam(1L, 1L);
    }
}
