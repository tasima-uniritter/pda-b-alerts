package br.edu.tasima.pda.b.alerts.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Domain about Engineer.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 *
 * | ENGINEER                              |
 * | ------------------------------------- |
 * | ENGINEER_ID PK BIGINT - e.g 54321     |
 * | TEAM_ID FK BIGINT - e.g 12345         |
 * | NAME VARCHAR - e.g Neymar Júnior      |
 * | EMAIL VARCHAR - e.g njr10@gmail.com   |
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Engineer {

    @Id
    @SequenceGenerator(name = "seq_enginner", sequenceName = "seq_enginner", allocationSize = 1)
    @GeneratedValue(generator = "seq_enginner")
    private Integer enginnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private String name;

    private String email;

}
