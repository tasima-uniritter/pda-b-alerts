package br.edu.tasima.pda.b.alerts.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain about Agenda.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | AGENDA                                |
 * | ------------------------------------- |
 * | AGENDA_ID PK BIGINT - e.g 11111       |
 * | ENGINEER_ID FK BIGINT - e.g 54321     |
 * | WEEK_DAY VARCHAR - e.g MONDAY         |
 * | START_TIME TIME - e.g 10:00           |
 * | END_TIME TIME - e.g 18:00             |
 * \*** AGENDA_ID + ENGINEER_ID + WEEK_DAY = UNIQUE KEY
 */
@Data
@Entity
public class Agenda {

    @Id
    @SequenceGenerator(name = "SEQ_AGENDA", sequenceName = "SEQ_AGENDA", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_AGENDA")
    private Integer agendaId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Engineer engineer;

    private String weekDay;

    private LocalDateTime startTime;

    private LocalDate endTime;

}
