package br.edu.tasima.pda.b.alerts.api.audit;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/audit")
public class AuditController {

    private AuditBO auditBO;

    @RequestMapping(method = RequestMethod.GET, value = "/findByMetricCode/{metricCode}")
    public ResponseEntity<List<Audit>> findByMetricCode(@PathVariable String metricCode) {
        List<Audit> auditList = auditBO.getByMetricCode(metricCode);
        return new ResponseEntity<>(auditList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByEngineer/{engineerId}")
    public ResponseEntity<List<Audit>> findByEngineer(@PathVariable Long engineerId) {
        List<Audit> auditList = auditBO.getByEngineer(engineerId);
        return new ResponseEntity<>(auditList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public ResponseEntity<List<Audit>> getAllAuditEntries() {
        List<Audit> auditList = auditBO.findAll();
        return new ResponseEntity<>(auditList, HttpStatus.OK);
    }
}
