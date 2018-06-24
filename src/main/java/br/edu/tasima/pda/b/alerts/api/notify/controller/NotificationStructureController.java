package br.edu.tasima.pda.b.alerts.api.notify.controller;

import br.edu.tasima.pda.b.alerts.api.notify.business.EngineerBO;
import br.edu.tasima.pda.b.alerts.api.notify.business.MetricCodeBO;
import br.edu.tasima.pda.b.alerts.api.notify.business.TeamBO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Agenda;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notificationStructure")
public class NotificationStructureController {

    private EngineerBO engineerBO;
    private MetricCodeBO metricCodeBO;
    private TeamBO teamBO;

    @RequestMapping(method = RequestMethod.POST, value = "/engineer/save")
    public ResponseEntity<Engineer> saveEngineer(@RequestBody Engineer engineer) {
        ResponseEntity<Engineer> response;
        Engineer savedEngineer;
        try {
            for (Agenda agenda : engineer.getEngineerAgenda()) {
                agenda.setEngineer(engineer);
            }

            savedEngineer = engineerBO.save(engineer);
            response = new ResponseEntity<>(savedEngineer, HttpStatus.CREATED);

        } catch (Exception e) {
            response = new ResponseEntity<>(engineer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/engineer/assignTeam/{engineerId}/{teamId}")
    public ResponseEntity<Engineer> assignEngineerToTeam(@PathVariable Long engineerId, @PathVariable Long teamId) {
        ResponseEntity<Engineer> response;
        Engineer engineerAssignedToTeam;
        try {
            engineerAssignedToTeam = engineerBO.assignEngineerTeam(engineerId, teamId);
            response = new ResponseEntity<>(engineerAssignedToTeam, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            Engineer engineerError = new Engineer();
            engineerError.setEngineerId(engineerId);
            Team teamError = new Team();
            teamError.setTeamId(teamId);
            engineerError.setTeam(teamError);
            response = new ResponseEntity<>(engineerError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/engineer/get/{id}")
    public ResponseEntity<Engineer> getEngineerById(@PathVariable Long id) {
        ResponseEntity<Engineer> response;
        Engineer engineer;
        try {
            engineer = engineerBO.getById(id);
            response = new ResponseEntity<>(engineer, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            Engineer engineerError = new Engineer();
            engineerError.setEngineerId(id);
            response = new ResponseEntity<>(engineerError, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/engineer/getByName/{name}")
    public ResponseEntity<Engineer> getEngineerByName(@PathVariable String name) {
        ResponseEntity<Engineer> response;
        Engineer engineer = engineerBO.getByName(name);
        if (engineer != null) {
            response = new ResponseEntity<>(engineer, HttpStatus.OK);
        } else {
            Engineer engineerError = new Engineer();
            engineerError.setName(name);
            response = new ResponseEntity<>(engineerError, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/engineer/findAllByTeam/{teamId}")
    public ResponseEntity<List<Engineer>> getEngineersByTeam(@PathVariable Long teamId) {
        List<Engineer> teamEngineers = engineerBO.findEgineersByTeam(teamId);
        return new ResponseEntity<>(teamEngineers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/engineer/delete/{id}")
    public ResponseEntity deleteEngineer(@PathVariable long id) {
        engineerBO.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/metricCode/save")
    public ResponseEntity<MetricCode> saveMetricCode(@RequestBody MetricCode metricCode) {
        ResponseEntity<MetricCode> response;
        MetricCode savedMetricCode;
        try {
            savedMetricCode = metricCodeBO.save(metricCode);

            response = new ResponseEntity<>(savedMetricCode, HttpStatus.CREATED);

        } catch (Exception e) {
            response = new ResponseEntity<>(metricCode, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/metricCode/get/{code}")
    public ResponseEntity<MetricCode> getMetricCodeByCode(@PathVariable String code) {
        ResponseEntity<MetricCode> response;
        MetricCode metricCode;
        try {
            metricCode = metricCodeBO.getByCode(code);
            response = new ResponseEntity<>(metricCode, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            MetricCode metricCodeError = new MetricCode();
            metricCodeError.setCode(code);
            response = new ResponseEntity<>(metricCodeError, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/metricCode/findAll")
    public ResponseEntity<List<MetricCode>> getAllMetricCodes() {
        List<MetricCode> metricCodes = metricCodeBO.findAll();
        return new ResponseEntity<>(metricCodes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/metricCode/delete/{code}")
    public ResponseEntity deleteMetricCode(@PathVariable String code) {
        metricCodeBO.delete(code);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/team/save")
    public ResponseEntity<Team> saveTeam(@RequestBody Team team) {
        ResponseEntity<Team> response;
        Team savedTeam;
        try {
            savedTeam = teamBO.save(team);

            response = new ResponseEntity<>(savedTeam, HttpStatus.CREATED);

        } catch (Exception e) {
            response = new ResponseEntity<>(team, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/team/get/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        ResponseEntity<Team> response;
        Team team;
        try {
            team = teamBO.getById(id);
            response = new ResponseEntity<>(team, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            Team teamError = new Team();
            teamError.setTeamId(id);
            response = new ResponseEntity<>(teamError, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/team/getByMetricCode/{metricCode}")
    public ResponseEntity<Team> getTeamById(@PathVariable String metricCode) {
        ResponseEntity<Team> response;
        Team team;
        try {
            team = teamBO.getByMetricCode(metricCode);
            response = new ResponseEntity<>(team, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            Team teamError = new Team();
            MetricCode metricCodeError = new MetricCode();
            metricCodeError.setCode(metricCode);
            teamError.setMetricCode(metricCodeError);
            response = new ResponseEntity<>(teamError, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/team/findAll")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamBO.findAll();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/team/delete/{id}")
    public ResponseEntity deleteTeam(@PathVariable Long id) {
        teamBO.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
