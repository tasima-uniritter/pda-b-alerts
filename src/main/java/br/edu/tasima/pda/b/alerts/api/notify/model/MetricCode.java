package br.edu.tasima.pda.b.alerts.api.notify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Domain about MetricCode.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 * <p>
 * | METRIC_CODE                         |
 * | ----------------------------------- |
 * | CODE PK VARCHAR - e.g MEMORY_USAGE  |
 * | VALUE VARCHAR - e.g Memory Usage    |
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "METRIC_CODE")
public class MetricCode {

    @Id
    @Column(name = "CODE", unique = true)
    private String code;

    @NotNull
    @Column(name = "VALUE")
    private String value;
}