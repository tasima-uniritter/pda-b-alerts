# pda-b-alerts

Initial commit - Spring Boot + Spring Data + AMQP RabbitMQ + H2 + Lombok

Example: https://spring.io/guides/gs/messaging-rabbitmq/

| METRIC                                    |
| ----------------------------------------- |
| METRIC_CODE PK VARCHAR - e.g MEMORY_USAGE |
| ORIGIN VARCHAR - e.g CONTAINER_1          |
| VALUE BIGINT - e.g 550                    |
| RULE VARCHAR - e.g >=                     |
| METRIC_TIMESTAMP TIMESTAMP - e.g 1234567  |
| METRIC_THRESHOLD BIGINT - e.g 500         |

many to one

| TEAM                                  |
| --------------------------------------------- |
| TEAM_ID PK BIGINT - e.g 12345                 |
| METRIC_CODE VARCHAR - e.g MEMORY_USAGE        |
| NAME VARCHAR - e.g Memory Usage Support Team  |

one to many

| ENGINEER                              |
| ------------------------------------- |
| ENGINEER_ID PK BIGINT - e.g 54321     |
| TEAM_ID FK BIGINT - e.g 12345         |
| NAME VARCHAR - e.g Neymar Júnior      |
| EMAIL VARCHAR - e.g njr10@gmail.com   |

one to many

| AGENDA                                |
| ------------------------------------- |
| AGENDA_ID PK BIGINT - e.g 11111       |
| ENGINEER_ID FK BIGINT - e.g 54321     |
| WEEK_DAY VARCHAR - e.g MONDAY         |
| START_TIME TIME - e.g 10:00           |
| END_TIME TIME - e.g 18:00             |
\*** AGENDA_ID + ENGINEER_ID + WEEK_DAY = UNIQUE KEY

| AUDIT                                         |
| --------------------------------------        |
| AUDIT_ID PK BIGINT - e.g 22222                |
| METRIC_CODE FK VARCHAR - e.g MEMORY_USAGE     |
| TEAM_ID FK BIGINT - e.g 12345                 |
| ENGINEER_ID FK BIGINT - e.g 54321             |
| TRIGGER_TIMESTAMP TIMESTAMP - e.g 1234567     |
| STATUS VARCHAR - e.g SUCCESS/FAILURE          |
| MESSAGE VARCHAR - e.g Error line 123 bla bla  |