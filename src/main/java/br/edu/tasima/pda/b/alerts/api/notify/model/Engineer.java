package br.edu.tasima.pda.b.alerts.api.notify.model;

import br.edu.tasima.pda.b.alerts.api.notify.serializer.TeamSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Domain about Engineer.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | ENGINEER                              |
 * | ------------------------------------- |
 * | ENGINEER_ID PK BIGINT - e.g 54321     |
 * | TEAM_ID FK BIGINT - e.g 222222        |
 * | NAME VARCHAR - e.g Neymar Júnior      |
 * | EMAIL VARCHAR - e.g njr10@gmail.com   |
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ENGINEER")
@NoArgsConstructor
@AllArgsConstructor
public class Engineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENGINEER_ID")
    private Long engineerId;

    @ManyToOne
    @JsonSerialize(using = TeamSerializer.class)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @NotNull
    @Column(name = "NAME", unique = true)
    private String name;

    @NotNull
    @Column(name = "EMAIL", unique = true)
    private String email;

    @OneToMany(mappedBy = "engineer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Agenda> engineerAgenda;
}