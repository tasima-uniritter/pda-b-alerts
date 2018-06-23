INSERT INTO METRIC_CODE (CODE, VALUE) VALUES ('MEMORY_USAGE','Memory usage is higher than normal');
INSERT INTO METRIC_CODE (CODE, VALUE) VALUES ('STORAGE_LIMIT','The storage is almost full');

INSERT INTO TEAM (NAME, METRIC_CODE) VALUES ('Memory Usage Team', 'MEMORY_USAGE');

INSERT INTO ENGINEER (NAME, EMAIL, TEAM_ID) VALUES ('Lucas', 'lucasgpc@gmail.com', 1);
INSERT INTO ENGINEER (NAME, EMAIL, TEAM_ID) VALUES ('Gelson', 'gelson@gmail.com', 1);
INSERT INTO ENGINEER (NAME, EMAIL, TEAM_ID) VALUES ('Tiago', 'tiago@gmail.com', 1);
INSERT INTO ENGINEER (NAME, EMAIL, TEAM_ID) VALUES ('Neymar', 'njr@gmail.com', 1);

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'MONDAY', '08:00:00', '12:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'WEDENESDAY', '08:00:00', '12:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (1, 'SATURDAY', '08:00:00', '11:40:00');

INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (2, 'SATURDAY', '11:45:00', '12:00:00');
INSERT INTO AGENDA (ENGINEER_ID, WEEK_DAY, START_TIME, END_TIME) VALUES (2, 'SUNDAY', '07:00:00', '17:00:00');
