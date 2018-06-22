# pda-b-alerts

Initial commit - Spring Boot + Spring Data + AMQP RabbitMQ + H2 + Lombok + Swagger

Example: https://spring.io/guides/gs/messaging-rabbitmq/

#Database Structure

 | TEAM                                          |
 | --------------------------------------------- |
 | TEAM_ID PK BIGINT - e.g 12345                 |
 | METRIC_CODE VARCHAR - e.g MEMORY_USAGE        |
 | NAME VARCHAR - e.g Memory Usage Support Team  |    

 | ENGINEER                              |
 | ------------------------------------- |
 | ENGINEER_ID PK BIGINT - e.g 54321     |
 | TEAM_ID FK BIGINT - e.g 222222        |
 | NAME VARCHAR - e.g Neymar Júnior      |
 | EMAIL VARCHAR - e.g njr10@gmail.com   |

| AGENDA                                |
| ------------------------------------- |
| AGENDA_ID PK BIGINT - e.g 11111       |
| ENGINEER_ID FK BIGINT - e.g 54321     |
| WEEK_DAY VARCHAR - e.g MONDAY         |
| START_TIME TIME - e.g 10:00:00        |
| END_TIME TIME - e.g 18:00:00          |
######Unique keys
#####ENGINEER_ID + WEEK_DAY + START_TIME 
#####ENGINEER_ID + WEEK_DAY + END_TIME
#####ENGINEER_ID + WEEK_DAY + START_TIME + END_TIME

| AUDIT                                              |
| -------------------------------------------------- |
| AUDIT_ID PK BIGINT - e.g 22222                     |
| METRIC_CONTENT CLOB - e.g Metric Message in JSON   |
| TEAM_ID FK BIGINT - e.g 12345                      |
| ENGINEER_ID FK BIGINT - e.g 54321                  |
| TRIGGER_TIMESTAMP TIMESTAMP - e.g 1234567          |
| STATUS VARCHAR - e.g SUCCESS/FAILURE               |

| METRIC_CODE                                       |
| ------------------------------------------------- |
| METRIC_CODE PK VARCHAR - e.g MEMORY_USAGE         |
| METRIC_CODE_VALUE VARCHAR - e.g Memory Usage      |

##Database example
```
INSERT INTO ENGINEER (NAME, EMAIL) VALUES ('Lucas', 'lucasgpc@gmail.com');
INSERT INTO ENGINEER (NAME, EMAIL) VALUES ('Gelson', 'gelson@gmail.com');
INSERT INTO ENGINEER (NAME, EMAIL) VALUES ('Tiago', 'tiago@gmail.com');
INSERT INTO ENGINEER (NAME, EMAIL) VALUES ('Neymar', 'njr@gmail.com');


INSERT INTO METRIC_CODE (CODE, VALUE) VALUES ('MEMORY_USAGE','Memory usage is higher than normal');
INSERT INTO METRIC_CODE (CODE, VALUE) VALUES ('STORAGE_LIMIT','The storage is almost full');

INSERT INTO TEAM (NAME, METRIC_CODE) VALUES ('Memory Usage Team', 'MEMORY_USAGE');
INSERT INTO TEAM (NAME, METRIC_CODE) VALUES ('Storage Limit Team', 'STORAGE_LIMIT');

UPDATE ENGINEER SET TEAM_ID = 1 WHERE ENGINEER_ID IN (1,2);
UPDATE ENGINEER SET TEAM_ID = 2 WHERE ENGINEER_ID IN (3,4);

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'MONDAY', '08:00:00', '18:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'WEDNESDAY', '08:00:00', '18:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'FRIDAY', '08:00:00', '18:00:00');

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (2, 'TUESDAY', '07:00:00', '17:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (2, 'THURSDAY', '07:00:00', '17:00:00');

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (3, 'SATURDAY', '08:00:00', '12:00:00');

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (4, 'SUNDAY', '14:00:00', '00:00:00');
```
## Queries

```
-- Get engineer engineer agenda
SELECT * FROM AGENDA WHERE ENGINEER_ID = 1;

-- Get team responsible for the specific metric
SELECT * FROM TEAM WHERE METRIC_CODE = 'MEMORY_USAGE';

-- Get engineers list from the specific team or even by metric code
SELECT * FROM ENGINEER WHERE TEAM_ID = 1; 
SELECT * FROM ENGINEER JOIN TEAM WHERE METRIC_CODE = 'MEMORY_USAGE';
```

## MetricDTO - Received message structure
###### Need to confirm with other team
```
{
  "metric": "MEMORY_USAGE",
  "origin": "CONTAINER_1",
  "value": 550,
  "rule": ">=",
  "timestamp": 1234567,
  "threshold": 500
}
```