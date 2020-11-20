-- Below are the SQL sentences used in DatabaseConnection.java after debugging

/*
 * ==================================================================
 *
 * SQL DDL and Loading Data
 *
 * ==================================================================
 * */

-- CREATE TABLE: set up the database

DROP TABLE Contain_Task;
DROP TABLE Schedule_Record;
DROP TABLE Task_Status;
DROP TABLE Meeting_Record;
DROP TABLE GroupChat_Record;
DROP TABLE Group_Creates;
DROP TABLE Group_Joins;
DROP TABLE group_record;
DROP TABLE Group_administrator;
DROP TABLE Group_Member;
DROP TABLE Mini_Program;
DROP TABLE MiniProgram_Record;
DROP TABLE Media;
DROP TABLE Share_Post;
DROP TABLE Individual_Chat;
DROP TABLE Time_Zone;
DROP TABLE User_info;

CREATE TABLE user_info (
    user_id varchar2(10),
    password varchar2(10) NOT NULL,
    name varchar2(20) NOT NULL,
    city varchar2(20),
    email varchar2(100),
    PRIMARY KEY (user_id));

CREATE TABLE time_zone (
        city varchar2(20),
        time_zone varchar2(5) NOT NULL,
        PRIMARY KEY (city));

CREATE TABLE individual_chat (
    time TIMESTAMP,
    sender varchar2(10),
    receiver varchar2(10),
    content varchar2(100),
    PRIMARY KEY (sender, receiver, time),
    FOREIGN KEY (sender) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (receiver) REFERENCES user_info ON DELETE CASCADE);

CREATE TABLE media (
    media_type varchar2(20),
    url varchar2(1000),
    PRIMARY KEY (url));

CREATE TABLE share_post (
    post_id varchar2(10),
    time TIMESTAMP,
    user_id varchar2(10) NOT NULL,
    content varchar2(100),
    media_url varchar2(10),
    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (media_url) REFERENCES media);

CREATE TABLE group_record (
    group_id varchar2(10),
    group_name varchar2(30),
    PRIMARY KEY (group_id));

CREATE TABLE group_creates (
    create_time TIMESTAMP,
    user_id varchar2(10),
    group_id varchar2(10),
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user_info,
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);

CREATE TABLE group_admin (
    user_id varchar2(10),
    group_id varchar2(10),
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);

CREATE TABLE group_joins (
    join_time TIMESTAMP,
    user_id varchar2(10),
    group_id varchar2(10),
    nickname varchar2(20),
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);

CREATE TABLE group_chat (
    time TIMESTAMP,
    user_id varchar2(10),
    content varchar2(100),
    group_id varchar2(10),
    PRIMARY KEY (user_id, group_id, time),
    FOREIGN KEY (user_id) REFERENCES user_info,
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);

-- CREATE TABLE meeting_record
--     (mid varchar2(10) PRIMARY KEY,
--     attendance INTEGER,
--     topic varchar2(50),
--     start_time TIMESTAMP,
--     end_time TIMESTAMP,
--     gid varchar2(10) NOT NULL,
--     FOREIGN KEY (gid) REFERENCES group_record ON DELETE CASCADE);
--
-- CREATE TABLE task_status
--     (stime TIMESTAMP,
--     passed INTEGER,
--     PRIMARY KEY (stime));
--
-- CREATE TABLE schedule_record
--     (sid varchar2(10) PRIMARY KEY,
--     stime TIMESTAMP,
--     event varchar2(50),
--     u_id varchar2(10),
--     FOREIGN KEY (u_id) REFERENCES user_info,
--     FOREIGN KEY (stime) REFERENCES task_status);
--
-- CREATE TABLE contain_task
--     (tname varchar2(50),
--     priority_val INTEGER,
--     sid varchar2(20),
--     PRIMARY KEY (sid,tname),
--     FOREIGN KEY (sid) REFERENCES schedule_record ON DELETE CASCADE);

-- CREATE TABLE mini_program (
--     miniid varchar2(10),
--     mediaid varchar2(20),
--     type varchar2(20),
--     PRIMARY KEY (miniid));
--
-- CREATE TABLE miniprogram_record (
--     u_id varchar2(10),
--     miniid varchar2(20),
--     time TIMESTAMP,
--     PRIMARY KEY (u_id , miniid,time),
--     FOREIGN KEY (u_id) REFERENCES user_info,
--     FOREIGN KEY (miniid) REFERENCES mini_program);

-- INSERT: load pre-set data
INSERT INTO user_info VALUES ('0001','1','Gelila Zhang','London', 'gelilaz@gmail.com');
INSERT INTO user_info VALUES ('0002','2','Kerry Yang','Vancouver', 'kerryy@gmail.com');
INSERT INTO user_info VALUES ('0003','3','Dora Ni','Beijing', 'doran@gmail.com');
INSERT INTO user_info VALUES ('0004','4','John Doe ','New York', 'johnd@gmail.com');
INSERT INTO user_info VALUES ('0005','5','Jane Doe','Toronto', 'janed@gmail');

INSERT INTO time_zone VALUES ('Vancouver','GMT-8');
INSERT INTO time_zone VALUES ('Toronto', 'GMT-5');
INSERT INTO time_zone VALUES ('Beijing', 'GMT+8');
INSERT INTO time_zone VALUES ('London', 'GMT+1');
INSERT INTO time_zone VALUES ('Seattle', 'UTC-7');
INSERT INTO time_zone VALUES ('Tokyo', 'UTC+9');
INSERT INTO time_zone VALUES ('New York', 'UTC-5');

INSERT INTO individual_chat VALUES ('2020-11-17 11:23:08', '0001', '0002', 'Hello!');
INSERT INTO individual_chat VALUES ('2020-11-17 11:24:12', '0002', '0001', 'Hello! I am karry');
INSERT INTO individual_chat VALUES ('2020-11-17 11:23:10', '0003', '0001', 'Hello! I am dora');
INSERT INTO individual_chat VALUES ('2020-11-17 11:31:48', '0001', '0003', 'Hi!');
INSERT INTO individual_chat VALUES ('2020-11-17 21:23:08', '0002', '0003', 'Nice to see you');
INSERT INTO individual_chat VALUES ('2020-11-18 11:23:08', '0003', '0002', 'Any recommended sushi?');

INSERT INTO media VALUES ('Image ', 'https://i.imgur.com/QwEq1g2.jpg');
INSERT INTO media VALUES ('Music ', 'https://music.163.com/#/song?id=1405903472');
INSERT INTO media VALUES ('Video ', 'https://www.youtube.com/watch?v=BoZ0Zwab6Oc');
INSERT INTO media VALUES ('Image ', 'https://i.imgur.com/veHL0mf.jpg');
INSERT INTO media VALUES ('Video ', 'https://www.youtube.com/watch?v=HXV3zeQKqGY');

INSERT INTO share_post VALUES ('P1', '2020-11-17 12:00:00', '0003', 'My first post!!', null);
INSERT INTO share_post VALUES ('P2', '2020-11-17 12:30:00', '0002', 'Hello everyone', null);
INSERT INTO share_post VALUES ('P3', '2020-11-17 19:40:06', '0001', 'Hello! This is Gelila', 'https://music.163.com/#/song?id=1405903472');
INSERT INTO share_post VALUES ('P4', '2020-11-18 11:09:25', '0002', 'Favourite sushi', 'https://i.imgur.com/QwEq1g2.jpg');
INSERT INTO share_post VALUES ('P5', '2020-11-18 18:05:20', '0005', 'Long time no see, Vancouver', 'https://i.imgur.com/veHL0mf.jpg');

INSERT INTO group_record VALUES('G1','Project Team 1');
INSERT INTO group_record VALUES('G2','Project Team 2');

INSERT INTO group_creates VALUES('2020-11-17 12:00:00','0001','G1');
INSERT INTO group_creates VALUES('2020-11-18 12:01:00','0004','G2');

INSERT INTO group_admin VALUES('0001','G1');
INSERT INTO group_admin VALUES('0004','G2');

INSERT INTO group_joins VALUES('2020-11-17 12:00:00','0001','G1', null);
INSERT INTO group_joins VALUES('2020-11-17 12:00:01','0002','G1', 'Frozen Cloud');
INSERT INTO group_joins VALUES('2020-11-17 12:00:02','0003','G1', 'Doooora');
INSERT INTO group_joins VALUES('2020-11-18 12:01:00','0004','G2', '7k+');
INSERT INTO group_joins VALUES('2020-11-18 12:01:01','0005','G2', 'magge');

INSERT INTO groupchat_record VALUES('2020-11-17 12:01:30', '0001','Hello folks!','G1');
INSERT INTO groupchat_record VALUES('2020-11-17 12:02:00', '0002','Nice to see you guys! This is Karry.','G1');
INSERT INTO groupchat_record VALUES('2020-11-17 12:03:00', '0003','Hi, I am Dora!','G1');
INSERT INTO groupchat_record VALUES('2020-11-18 12:01:30', '0004','This is our project group','G2');
INSERT INTO groupchat_record VALUES('2020-11-18 12:02:00', '0005','Thanks for doing this!','G2');

-- INSERT INTO meeting_record VALUES('m0001',10,'Welcome ceremony','2020-08-16 12:00:00','2020-08-16 14:00:00','g0001');
-- INSERT INTO meeting_record VALUES('m0002',5,'team discussion','2020-08-19 12:00:00','2020-08-19 13:00:00','g0001');
-- INSERT INTO meeting_record VALUES('m0003',6,'project discussion','2020-09-10 12:00:00','2020-08-16 16:00:00','g0001');
-- INSERT INTO meeting_record VALUES('m0004',7,'Welcome ceremony','2020-08-16 12:00:00','2020-08-16 14:00:00','g0002');
-- INSERT INTO meeting_record VALUES('m0005',4,'random chatting','2020-08-20 17:00:00','2020-08-16 18:00:00','g0002');
--
-- INSERT INTO task_status VALUES('2020-08-16 12:00:00'),0);
-- INSERT INTO task_status VALUES('2020-08-17 14:00:00'),1);
-- INSERT INTO task_status VALUES('2020-08-18 16:00:00'),1);
-- INSERT INTO task_status VALUES('2020-08-19 16:00:00'),1);
-- INSERT INTO task_status VALUES('2020-08-20 17:00:00'),0);
--
-- INSERT INTO schedule_record VALUES('s0001','event1','0001',0);
-- INSERT INTO schedule_record VALUES('s0002','event2','0001',1);
-- INSERT INTO schedule_record VALUES('s0003','event3','0002',1);
-- INSERT INTO schedule_record VALUES('s0004','event4','0002',1);
-- INSERT INTO schedule_record VALUES('s0005','event5','0003',0);
--
-- INSERT INTO contain_task VALUES('Finish Milestone1',1,'s0001');
-- INSERT INTO contain_task VALUES('Finish Milestone2',3,'s0002');
-- INSERT INTO contain_task VALUES('Finish Milestone3',2,'s0003');
-- INSERT INTO contain_task VALUES('Finish Milestone4',4,'s0004');
-- INSERT INTO contain_task VALUES('Finish Milestone5',2,'s0005');
--
-- INSERT INTO mini_program VALUES ('mi0001', 'Payroll Check', 'Work');
-- INSERT INTO mini_program VALUES ('mi0002', 'Monthly Report', 'Work');
-- INSERT INTO mini_program VALUES ('mi0003', 'Announcement', 'Work');
-- INSERT INTO mini_program VALUES ('mi0004', 'Dashboards', 'Work');
-- INSERT INTO mini_program VALUES ('mi0005', 'Expenses', 'Work');
--
-- INSERT INTO miniprogram_record VALUES ('0001','mi0001','2020-01-15 12:00:00');
-- INSERT INTO miniprogram_record VALUES ('0002','mi0002','2020-01-15 12:30:00');
-- INSERT INTO miniprogram_record VALUES ('0003','mi0003','2020-01-16 12:00:00');
-- INSERT INTO miniprogram_record VALUES ('0004','mi0004','2020-01-17 12:00:00');
-- INSERT INTO miniprogram_record VALUES ('0005','mi0005','2020-01-18 12:00:00');

/*
 * ==================================================================
 *
 * Queries
 *
 * ==================================================================
 * */
-- DELETE Operation
DELETE FROM user_info WHERE user_id = '0001'; -- 0001 could be replaced by other user_id
DELETE FROM share_post WHERE post_id = 'P1'; -- P1 could be replaced by other post_id

-- UPDATE Operation
UPDATE user_info
SET password = '1111' -- 1111 could be replaced by other password input
WHERE user_id = '0001'; -- 0001 could be replaced by other user_id

UPDATE user_info
SET name = 'admin' -- admin could be replaced by other name
WHERE user_id = '0001'; -- 0001 could be replaced by other user_id

UPDATE user_info
SET city = 'Vancouver' -- Vancouver could be replaced by other city name
WHERE user_id = '0001'; -- 0001 could be replaced by other user_id

UPDATE user_info
SET email = 'email@gmail.com' -- email address could be replaced by other email
WHERE user_id = '0001'; -- 0001 could be replaced by other user_id

-- Selection
SELECT *
FROM time_zone
WHERE city = 'Vancouver'; -- Vancouver could be replaced by other city name

SELECT *
FROM share_post
WHERE user_id = '0001' -- 0001 could be replaced by other user_id
ORDER BY time;

SELECT *
FROM share_post
WHERE post_id = 'P1'; -- P1 could be replaced by other post_id

SELECT *
FROM media
WHERE url = 'https://i.imgur.com/QwEq1g2.jpg'; -- the url link could be replaced

-- Projection
SELECT city FROM time_zone;
select user_id from user_info;
select user_id, password from user_info;

-- JOIN
SELECT *
FROM user_info, time_zone
WHERE user_info.city = time_zone.city;

SELECT *
FROM user_info, time_zone
WHERE user_id = '0001' -- 0001 could be replaced by other user_id
    AND user_info.city = time_zone.city;

SELECT *
FROM individual_chat, user_info
WHERE ((sender = '0001' AND receiver = '0002' -- 0001 could be replaced by other user_id
        OR (sender = '0002' AND receiver = '0001')) -- 0002 could be replaced by other user_id
    AND (sender = user_id)
ORDER BY time;

-- Aggregation with GROUP BY


-- Aggregation with HAVING

SELECT COUNT(*)
FROM group_join, time_zone
GROUP BY group_id
HAVING COUNT(*) >= 2;

-- Nested Aggregation with GROUP BY

-- Division

-- SELECT ALL THE CHECK THE USER THAT JOINS ALL THE GROUPS?

