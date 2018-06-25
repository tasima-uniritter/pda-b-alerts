package br.edu.tasima.pda.b.alerts.api.notify.mq;

import br.edu.tasima.pda.b.alerts.configuration.QueueConfig;
import org.apache.camel.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class RunnerAws implements CommandLineRunner {

    private QueueConfig config;

    public RunnerAws(QueueConfig config) {
        this.config = config;
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.bind("amqp", config.securedAmqpConnection());
        main.addRouteBuilder(new RouteAws());
        main.run(args);
    }
}
