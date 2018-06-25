package br.edu.tasima.pda.b.alerts.api.notify.controller;
import br.edu.tasima.pda.b.alerts.api.notify.business.EngineerBO;
import br.edu.tasima.pda.b.alerts.api.notify.business.MetricCodeBO;
import br.edu.tasima.pda.b.alerts.api.notify.business.TeamBO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotificationStructureControllerTest {

    private ArrayList<Engineer> engineerList;
    private ArrayList<Team> teamList;
    private ArrayList<MetricCode> metricCodeList;

    @Autowired
    private NotificationStructureController controller;

    @MockBean
    private EngineerBO engineerBO;
    @MockBean
    private TeamBO teamBO;
    @MockBean
    private MetricCodeBO metricCodeBO;

    @Before
    public void init(){
        //MockitoAnnotations.initMocks(this);

        engineerList = new ArrayList<>();
        engineerList.add(new Engineer(1L, new Team(), "João", "joao@bol.com.br", new ArrayList<>()));

        teamList = new ArrayList<>();
        teamList.add(new Team(1L, new MetricCode("processador", "80"), "Suporte", new ArrayList<>()));

        metricCodeList = new ArrayList<>();
        metricCodeList.add(new MetricCode("disk", "85"));
    }

    //***********************************************ENGINEER***********************************************

    @Test
    public void getEngineerByIdExistsTest(){
        given(engineerBO.getById(1L)).willReturn(engineerList.get(0));

        ResponseEntity<Engineer> response = controller.getEngineerById(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEngineerId().longValue(), 1L);
        assertEquals(response.getBody().getName(), "João");
        assertEquals(response.getBody().getEmail(), "joao@bol.com.br");
    }

    @Test
    public void getEngineerByIdNotFoundTest(){
        given(engineerBO.getById(2L)).willThrow(new EntityNotFoundException());

        ResponseEntity<Engineer> response = controller.getEngineerById(2L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getEngineerByNameExistsTest(){
        given(engineerBO.getByName("João")).willReturn(engineerList.get(0));

        ResponseEntity<Engineer> response = controller.getEngineerByName("João");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEngineerId().longValue(), 1L);
        assertEquals(response.getBody().getName(), "João");
        assertEquals(response.getBody().getEmail(), "joao@bol.com.br");
    }

    @Test
    public void getEngineerByNameNotFoundTest(){
        given(engineerBO.getByName("Márcia"))
                .willReturn(null);

        ResponseEntity<Engineer> response = controller.getEngineerByName("Márcia");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveEngineerSuccessTest(){
        Engineer engineer = new Engineer(2L, new Team(), "Márcia", "marcia@aol.com.br", new ArrayList<>());
        given(engineerBO.save(any(Engineer.class))).willReturn(engineer);

        ResponseEntity<Engineer> response = controller.saveEngineer(engineer);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getEngineerId().longValue(), 2L);
        assertEquals(response.getBody().getName(), "Márcia");
        assertEquals(response.getBody().getEmail(), "marcia@aol.com.br");

        engineerList.add(engineer);
    }

    @Test
    public void saveEngineerInternalServerErrorTest(){
        given(engineerBO.save(any(Engineer.class))).willThrow(RuntimeException.class);

        ResponseEntity<Engineer> response = controller.saveEngineer(new Engineer(2L, new Team(), "Márcia", "marcia@aol.com.br", new ArrayList<>()));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void deleteEngineerSuccessTest(){
        ResponseEntity<Engineer> deleted = controller.getEngineerById(1L);
        assertEquals(deleted.getStatusCode(), HttpStatus.OK);

        given(engineerBO.getById(1L)).willReturn(deleted.getBody());

        ResponseEntity<Engineer> returned = controller.deleteEngineer(1L);

        assertEquals(deleted.getBody(), returned.getBody());

        engineerList.remove(deleted);
    }

    //***********************************************METRICS***********************************************

    @Test
    public void getMetricsByIdExistsTest(){
        given(metricCodeBO.getByCode("disk")).willReturn(metricCodeList.get(0));

        ResponseEntity<MetricCode> response = controller.getMetricCodeByCode("disk");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getCode(), "disk");
        assertEquals(response.getBody().getValue(), "85");
    }

    @Test
    public void getMetricsByIdNotFoundTest(){
        given(metricCodeBO.getByCode("timeout")).willThrow(new EntityNotFoundException());

        ResponseEntity<MetricCode> response = controller.getMetricCodeByCode("timeout");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveMetricsSuccessTest(){
        MetricCode metricCode = new MetricCode("timeout", "200");
        given(metricCodeBO.save(any(MetricCode.class))).willReturn(metricCode);

        ResponseEntity<MetricCode> response = controller.saveMetricCode(metricCode);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getCode(), "timeout");
        assertEquals(response.getBody().getValue(), "200");

        metricCodeList.add(metricCode);
    }

    @Test
    public void saveMetricsInternalServerErrorTest(){
        given(metricCodeBO.save(any(MetricCode.class))).willThrow(RuntimeException.class);

        ResponseEntity<MetricCode> response = controller.saveMetricCode(new MetricCode("timeout", "200"));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getAllMetricsSuccessTest(){
        given(metricCodeBO.findAll()).willReturn(metricCodeList);

        ResponseEntity<List<MetricCode>> response = controller.getAllMetricCodes();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void getAllMetricsErrorTest(){
        given(metricCodeBO.findAll()).willReturn(null);

        ResponseEntity<List<MetricCode>> response = controller.getAllMetricCodes();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), null);
    }

    @Test
    public void deleteMetricsSuccessTest(){
        ResponseEntity<MetricCode> deleted = controller.getMetricCodeByCode("disk");
        assertEquals(deleted.getStatusCode(), HttpStatus.OK);

        given(metricCodeBO.getByCode("disk")).willReturn(deleted.getBody());

        ResponseEntity<MetricCode> returned = controller.deleteMetricCode("disk");

        assertEquals(deleted.getBody(), returned.getBody());

        metricCodeList.remove(deleted);
    }

    //***********************************************TEAM***********************************************

    @Test
    public void getTeamByIdExistsTest(){
        given(teamBO.getById(1L)).willReturn(teamList.get(0));

        ResponseEntity<Team> response = controller.getTeamById(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getTeamId().longValue(), 1L);
        assertEquals(response.getBody().getName(), "Suporte");
        assertEquals(response.getBody().getMetricCode().getCode(), "processador");
        assertEquals(response.getBody().getMetricCode().getValue(), "80");
    }

    @Test
    public void getTeamByIdNotFoundTest(){
        given(teamBO.getById(2L)).willThrow(new EntityNotFoundException());

        ResponseEntity<Team> response = controller.getTeamById(2L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getTeamByMetricCodeExistsTest(){
        given(teamBO.getByMetricCode("processador")).willReturn(teamList.get(0));

        ResponseEntity<Team> response = controller.getTeamById("processador");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getTeamId().longValue(), 1L);
        assertEquals(response.getBody().getName(), "Suporte");
        assertEquals(response.getBody().getMetricCode().getCode(), "processador");
        assertEquals(response.getBody().getMetricCode().getValue(), "80");
    }

    @Test
    public void getTeamByMetricCodeNotFoundTest(){
        given(teamBO.getByMetricCode("memory")).willThrow(EntityNotFoundException.class);

        ResponseEntity<Team> response = controller.getTeamById("memory");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveTeamSuccessTest(){
        Team team = new Team(2L, new MetricCode("memory", "70"), "Suporte", new ArrayList<>());
        given(teamBO.save(any(Team.class))).willReturn(team);

        ResponseEntity<Team> response = controller.saveTeam(team);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getTeamId().longValue(), 2L);
        assertEquals(response.getBody().getName(), "Suporte");
        assertEquals(response.getBody().getMetricCode().getCode(), "memory");
        assertEquals(response.getBody().getMetricCode().getValue(), "70");

        teamList.add(team);
    }

    @Test
    public void saveTeamInternalServerErrorTest(){
        given(teamBO.save(any(Team.class))).willThrow(RuntimeException.class);

        ResponseEntity<Team> response = controller.saveTeam(new Team(2L, new MetricCode("memory", "70"), "Suporte", new ArrayList<>()));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getAllTeamsSuccessTest(){
        given(teamBO.findAll()).willReturn(teamList);

        ResponseEntity<List<Team>> response = controller.getAllTeams();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void getAllTeamsErrorTest(){
        given(teamBO.findAll()).willReturn(null);

        ResponseEntity<List<Team>> response = controller.getAllTeams();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), null);
    }

    @Test
    public void deleteTeamSuccessTest(){
        ResponseEntity<Team> deleted = controller.getTeamById(1L);
        assertEquals(deleted.getStatusCode(), HttpStatus.OK);

        given(teamBO.getById(1L)).willReturn(deleted.getBody());

        ResponseEntity<Team> returned = controller.deleteTeam(1L);

        assertEquals(deleted.getBody(), returned.getBody());

        teamList.remove(deleted);
    }

}
