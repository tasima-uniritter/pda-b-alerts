package br.edu.tasima.pda.b.alerts.api.v1.notify.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

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
 * | START_TIME TIME - e.g 10:00:00        |
 * | END_TIME TIME - e.g 18:00:00          |
 * \*** Unique keys
 * \*** ENGINEER_ID + WEEK_DAY + START_TIME
 * \*** ENGINEER_ID + WEEK_DAY + END_TIME
 * \*** ENGINEER_ID + WEEK_DAY + START_TIME + END_TIME
 */
@Data
@Entity
@Table(
        name = "AGENDA",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ENGINEER_ID", "WEEK_DAY", "START_TIME"}),
                @UniqueConstraint(columnNames = {"ENGINEER_ID", "WEEK_DAY", "END_TIME"}),
                @UniqueConstraint(columnNames = {"ENGINEER_ID", "WEEK_DAY", "START_TIME", "END_TIME"})
        }
)
public class Agenda {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long agendaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENGINEER_ID")
    private Engineer engineer;

    @NotNull
    @Column(name = "WEEK_DAY")
    @Enumerated(value = EnumType.STRING)
    private DayOfWeek weekDay;

    @NotNull
    @Column(name = "START_TIME")
    private LocalTime startTime;

    @NotNull
    @Column(name = "END_TIME")
    private LocalTime endTime;
}