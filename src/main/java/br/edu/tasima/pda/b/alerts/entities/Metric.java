package br.edu.tasima.pda.b.alerts.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain about Metric.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 *
 * | METRIC                                    |
 * | ----------------------------------------- |
 * | METRIC_CODE PK VARCHAR - e.g MEMORY_USAGE |
 * | ORIGIN VARCHAR - e.g CONTAINER_1          |
 * | VALUE BIGINT - e.g 550                    |
 * | RULE VARCHAR - e.g >=                     |
 * | METRIC_TIMESTAMP TIMESTAMP - e.g 1234567  |
 * | METRIC_THRESHOLD BIGINT - e.g 500         |
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Metric {

    @Id
    private String metricCode;

    private String origin;

    private Integer value;

    private String rule;

    private LocalDateTime metricTimestamp;

    private Integer metricThreshold;

    @OneToMany(mappedBy = "teamId")
    private List<Team> teams;
}
