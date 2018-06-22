package br.edu.tasima.pda.b.alerts.api.notify.controller;

import br.edu.tasima.pda.b.alerts.api.notify.business.EngineerBO;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/notificationStructure")
public class NotificationStructureController {

    private EngineerBO engineerBO;

    @RequestMapping(method = RequestMethod.POST, value = "/engineer/save")
    public ResponseEntity<Engineer> saveEngineer(@RequestBody Engineer engineer) {

        Engineer savedEngineer;
        try{
            savedEngineer = engineerBO.save(engineer);

            return new ResponseEntity<>(savedEngineer, HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(engineer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/engineer/get/{id}")
    public ResponseEntity<Engineer> getEngineerById(@PathVariable Long id) {
        Engineer engineer = engineerBO.getById(id);
        if(engineer != null){
            return new ResponseEntity<>(engineer, HttpStatus.OK);
        } else {
            Engineer engineerError = new Engineer();
            engineerError.setEngineerId(id);
            return new ResponseEntity<>(engineerError, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/engineer/getByName/{name}")
    public ResponseEntity<Engineer> getEngineerByName(@PathVariable String name) {
        Engineer engineer = engineerBO.getByName(name);
        if(engineer != null){
            return new ResponseEntity<>(engineer, HttpStatus.OK);
        } else {
            Engineer engineerError = new Engineer();
            engineerError.setName(name);
            return new ResponseEntity<>(engineerError, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/engineer/delete/{id}")
    public ResponseEntity deleteEngineer(@PathVariable long id) {
        engineerBO.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
