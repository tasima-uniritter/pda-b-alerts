package br.edu.tasima.pda.b.alerts.api.notify.mq;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteAws extends SpringRouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class)
                .handled(false)
                .log(LoggingLevel.ERROR, "Error in processing route!");

        from("amqp:queue:b-monitor-alerts")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
                .to("http4://localhost:8080/api/alert/sendAlert")
                .log("Content ${body}");
    }
}