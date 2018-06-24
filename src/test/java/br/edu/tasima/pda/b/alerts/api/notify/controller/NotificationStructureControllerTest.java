package br.edu.tasima.pda.b.alerts.api.notify.controller;

import br.edu.tasima.pda.b.alerts.api.notify.business.EngineerBO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationStructureControllerTest {

    private ArrayList<Engineer> engineerList;

    @Autowired
    private NotificationStructureController controller;

    @MockBean
    private EngineerBO engineerBO;

    @Before
    public void init(){
        //MockitoAnnotations.initMocks(this);

        engineerList = new ArrayList<>();
        engineerList.add(new Engineer(1L, new Team(), "João", "joao@bol.com.br", new ArrayList<>()));
    }

    @Test
    public void getEngineerByIdExistsTest(){
        given(engineerBO.getById(1L))
                .willReturn(engineerList.get(0));

        ResponseEntity<Engineer> response = controller.getEngineerById(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEngineerId().longValue(), 1L);
        assertEquals(response.getBody().getName(), "João");
        assertEquals(response.getBody().getEmail(), "joao@bol.com.br");
    }

    @Test
    public void getEngineerByIdNotFoundTest(){
        given(engineerBO.getById(2L))
                .willThrow(new EntityNotFoundException());

        ResponseEntity<Engineer> response = controller.getEngineerById(2L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getEngineerByNameExistsTest(){
        given(engineerBO.getByName("João"))
                .willReturn(engineerList.get(0));

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
        given(engineerBO.save(any(Engineer.class)))
                .willReturn(new Engineer(2L, new Team(), "Márcia", "marcia@aol.com.br", new ArrayList<>()));

        ResponseEntity<Engineer> response = controller.saveEngineer(new Engineer(2L, new Team(), "Márcia", "marcia@aol.com.br", new ArrayList<>()));
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getEngineerId().longValue(), 2L);
        assertEquals(response.getBody().getName(), "Márcia");
        assertEquals(response.getBody().getEmail(), "marcia@aol.com.br");
    }

    @Test
    public void saveEngineerInternalServerErrorTest(){
        given(engineerBO.save(any(Engineer.class)))
                .willThrow(RuntimeException.class);

        ResponseEntity<Engineer> response = controller.saveEngineer(new Engineer(2L, new Team(), "Márcia", "marcia@aol.com.br", new ArrayList<>()));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
