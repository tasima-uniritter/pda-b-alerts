package br.edu.tasima.pda.b.alerts.api.notify.controller;

import br.edu.tasima.pda.b.alerts.api.notify.business.AlertBO;
import br.edu.tasima.pda.b.alerts.api.notify.dto.MetricDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/alert")
public class AlertController {

    private AlertBO alertBO;

    @RequestMapping(method = RequestMethod.POST, value = "/sendAlert")
    public ResponseEntity<MetricDTO> saveTeam(@RequestBody MetricDTO metricDTO) {
        ResponseEntity<MetricDTO> response;
        try {
            alertBO.sendAlertFromMonitor(metricDTO);

            response = new ResponseEntity<>(metricDTO, HttpStatus.OK);

        } catch (Exception e) {
            response = new ResponseEntity<>(metricDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
