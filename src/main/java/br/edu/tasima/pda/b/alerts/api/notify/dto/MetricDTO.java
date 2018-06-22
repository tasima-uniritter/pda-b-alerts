package br.edu.tasima.pda.b.alerts.api.notify.dto;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * MetricDTO received as a message.
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex S. Rosa</a>
 * @since 10/06/2018 21:05:00
 */
@Data
public class MetricDTO {
    private MetricCode metricCode;
    private String origin;
    private Integer value;
    private String rule;
    private LocalDateTime metricTimestamp;
    private Integer metricThreshold;
}
