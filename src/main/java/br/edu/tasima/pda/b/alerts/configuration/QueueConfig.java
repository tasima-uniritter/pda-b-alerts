package br.edu.tasima.pda.b.alerts.configuration;

import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueConfig {

    @Value("${amqp.service.host}")
    private String host;

    @Value("${amqp.service.port}")
    private String port;

    @Value("${amqp.service.username}")
    private String username;

    @Value("${amqp.service.password}")
    private String password;

    public AMQPConnectionDetails securedAmqpConnection() {
        return new AMQPConnectionDetails("amqps://".concat(host).concat(":").concat(port), username, password);
    }
}
