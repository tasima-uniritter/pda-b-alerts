package br.edu.tasima.pda.b.alerts.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Domain about Team.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 *
 * | TEAM                                  |
 * | --------------------------------------------- |
 * | TEAM_ID PK BIGINT - e.g 12345                 |
 * | METRIC_CODE VARCHAR - e.g MEMORY_USAGE        |
 * | NAME VARCHAR - e.g Memory Usage Support Team  |
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Team {

    @Id
    @SequenceGenerator(name = "seq_team", sequenceName = "seq_team", allocationSize = 1)
    @GeneratedValue(generator = "seq_team")
    private Integer teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric metric;

    private String name;

    @OneToMany(mappedBy = "enginnerId")
    private List<Engineer> engineers;
}
