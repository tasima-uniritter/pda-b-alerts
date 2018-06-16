package br.edu.tasima.pda.b.alerts.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Domain about Team.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | TEAM                                  |
 * | --------------------------------------------- |
 * | TEAM_ID PK BIGINT - e.g 12345                 |
 * | METRIC_CODE VARCHAR - e.g MEMORY_USAGE        |
 * | NAME VARCHAR - e.g Memory Usage Support Team  |
 */
@Data
@Entity
public class Team {

    @Id
    @SequenceGenerator(name = "SEQ_TEAM", sequenceName = "SEQ_TEAM", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TEAM")
    private Integer teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric metric;

    private String name;

    @OneToMany(mappedBy = "engineerId")
    private List<Engineer> engineers;
}
