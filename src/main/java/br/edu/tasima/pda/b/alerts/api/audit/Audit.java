package br.edu.tasima.pda.b.alerts.api.audit;

import br.edu.tasima.pda.b.alerts.api.audit.enums.NotifyStatus;
import br.edu.tasima.pda.b.alerts.api.notify.model.Engineer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * | ENGINEER_ID FK BIGINT - e.g 54321                  |
 * | METRIC_CONTENT CLOB - e.g Metric Message in JSON   |
 * | TRIGGER_TIMESTAMP TIMESTAMP - e.g 1479249799770    |
 * | STATUS VARCHAR - e.g SUCCESS/FAILURE               |
 * | MESSAGE VARCHAR - e.g ErrorMessage bla bla         |
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AUDIT")
class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @Column(name = "METRIC_CONTENT", columnDefinition = "CLOB")
    private String metricContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENGINEER_ID")
    private Engineer engineer;

    @Column(name = "TRIGGER_TIMESTAMP")
    private LocalDateTime triggerTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private NotifyStatus status;

    @Column(name = "MESSAGE", columnDefinition = "CLOB")
    private String message;
}
