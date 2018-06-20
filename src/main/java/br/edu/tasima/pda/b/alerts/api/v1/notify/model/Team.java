package br.edu.tasima.pda.b.alerts.api.v1.notify.model;

import br.edu.tasima.pda.b.alerts.api.v1.notify.serializer.MetricCodeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Domain about Team.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | TEAM                                          |
 * | --------------------------------------------- |
 * | TEAM_ID PK BIGINT - e.g 12345                 |
 * | METRIC_CODE VARCHAR - e.g MEMORY_USAGE        |
 * | NAME VARCHAR - e.g Memory Usage Support Team  |
 */
@Data
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "METRIC_CODE", unique = true)
    @JsonSerialize(using = MetricCodeSerializer.class)
    private MetricCode metricCode;

    @Column(name = "NAME", unique = true)
    private String name;

    @Transient
    private List<Engineer> engineers;
}
