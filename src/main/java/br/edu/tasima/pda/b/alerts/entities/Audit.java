package br.edu.tasima.pda.b.alerts.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Domain about Audit.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 *
 * | AUDIT                                         |
 * | --------------------------------------        |
 * | AUDIT_ID PK BIGINT - e.g 22222                |
 * | METRIC_CODE FK VARCHAR - e.g MEMORY_USAGE     |
 * | TEAM_ID FK BIGINT - e.g 12345                 |
 * | ENGINEER_ID FK BIGINT - e.g 54321             |
 * | TRIGGER_TIMESTAMP TIMESTAMP - e.g 1234567     |
 * | STATUS VARCHAR - e.g SUCCESS/FAILURE          |
 * | MESSAGE VARCHAR - e.g Error line 123 bla bla  |
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Audit {

    @Id
    @SequenceGenerator(name = "seq_audit", sequenceName = "seq_audit", allocationSize = 1)
    @GeneratedValue(generator = "seq_audit")
    private Integer auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric matric;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Engineer engineer;

    private LocalDateTime triggerTimestamp;

    private String status;

    private String message;
}
