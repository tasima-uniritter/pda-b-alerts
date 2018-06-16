package br.edu.tasima.pda.b.alerts.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Domain about Engineer.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | ENGINEER                              |
 * | ------------------------------------- |
 * | ENGINEER_ID PK BIGINT - e.g 54321     |
 * | TEAM_ID FK BIGINT - e.g 12345         |
 * | NAME VARCHAR - e.g Neymar Júnior      |
 * | EMAIL VARCHAR - e.g njr10@gmail.com   |
 */
@Data
@Entity
public class Engineer {

    @Id
    @SequenceGenerator(name = "SEQ_ENGINEER", sequenceName = "SEQ_ENGINEER", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_ENGINEER")
    private Integer engineerId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private String name;

    private String email;

}
