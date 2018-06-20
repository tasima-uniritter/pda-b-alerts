package br.edu.tasima.pda.b.alerts.api.v1.audit;

import br.edu.tasima.pda.b.alerts.api.v1.audit.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.v1.notify.model.Engineer;
import br.edu.tasima.pda.b.alerts.api.v1.notify.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Domain about Audit.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | AUDIT                                              |
 * | -------------------------------------------------- |
 * | AUDIT_ID PK BIGINT - e.g 22222                     |
 * | METRIC_CONTENT CLOB - e.g Metric Message in JSON   |
 * | TEAM_ID FK BIGINT - e.g 12345                      |
 * | ENGINEER_ID FK BIGINT - e.g 54321                  |
 * | TRIGGER_TIMESTAMP TIMESTAMP - e.g 1234567          |
 * | STATUS VARCHAR - e.g SUCCESS/FAILURE               |
 */
@Data
@Entity
@AllArgsConstructor
@Table(name = "AUDIT")
public class Audit {

    @Id
    @SequenceGenerator(name = "SEQ_AUDIT", sequenceName = "SEQ_AUDIT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUDIT")
    private Integer auditId;

    @Column(name = "METRIC_CONTENT", columnDefinition = "CLOB")
    private String metricContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENGINEER_ID")
    private Engineer engineer;

    @Column(name = "TRIGGER_TIMESTAMP")
    private LocalDateTime triggerTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private NotifyStatus status;
}
