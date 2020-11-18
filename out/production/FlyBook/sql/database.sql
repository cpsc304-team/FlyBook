-- Paste the SQL used in DatabaseConnection.java here

-- SQL DDL and Loading Data
-- CREATE TABLE: set up the database
DROP TABLE Contain_Task;
DROP TABLE Task_Status;
DROP TABLE Schedule_Record;
DROP TABLE Meeting_Record;
DROP TABLE GroupChat_Record;
DROP TABLE Group_Creates;
DROP TABLE Group_Joins;
DROP TABLE Group_administrator;
DROP TABLE Group_Member;
DROP TABLE Mini_Program;
DROP TABLE MiniProgram_Record;
DROP TABLE Post_Contains;
DROP TABLE Media;
DROP TABLE Share_Post;
DROP TABLE Individual_Chat;
DROP TABLE Time_Zone;
DROP TABLE User_info;


CREATE TABLE User_info
    (u_id CHAR(20),
    u_password CHAR(20),
    u_name CHAR(20),
    city CHAR(20),
    email CHAR(20) UNIQUE,
    PRIMARY KEY (u_id),
    FOREIGN KEY (city) REFERENCES Time_Zone);

CREATE TABLE Time_Zone
    (city CHAR(20),
    time_zone CHAR(10),
    PRIMARY KEY (city));

CREATE TABLE Individual_Chat
    (time TIMESTAMP,
    u_id1 CHAR(10),
    u_id2 CHAR(10),
    content CHAR(100),
    PRIMARY KEY (u_id1, u_id2, time),
    FOREIGN KEY (u_id1) REFERENCES User_info ON DELETE CASCADE,
    FOREIGN KEY (u_id2) REFERENCES User_info ON DELETE CASCADE);


CREATE TABLE Share_Post
    (postid CHAR(10),
    post_time TIMESTAMP,
    u_id CHAR(10) NOT NULL,
    content CHAR(100),
    PRIMARY KEY (postid),
    FOREIGN KEY (u_id) REFERENCES User_info);

CREATE TABLE Media
    (mediaid CHAR(10),
    mtype CHAR(10),
    PRIMARY KEY (mediaid));

CREATE TABLE Post_Contains
    (postid CHAR(10),
    mediaid CHAR(10),
    PRIMARY KEY (postid,mediaid),
    FOREIGN KEY (postid) REFERENCES Share_Post,
    FOREIGN KEY (mediaid) REFERENCES Media);

CREATE TABLE Mini_Program
    (pid CHAR(10),
    pname CHAR(20),
    type CHAR(10),
    PRIMARY KEY (pid));

CREATE TABLE MiniProgram_Record
    (u_id CHAR(10),
    pid CHAR(10),
    time TIMESTAMP,
    PRIMARY KEY (u_id, pid, time),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (pid) REFERENCES Mini_Program);

CREATE TABLE Group_Member
    (nickname CHAR(10),
    u_id CHAR(10),
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_id) REFERENCES User_info);

CREATE TABLE Group_administrator
    (nickname CHAR(10),
    u_id CHAR(10),
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_id) REFERENCES User_info);

CREATE TABLE Group_Record
    (gid CHAR(10),
    gname CHAR(20),
    PRIMARY KEY (gid));

CREATE TABLE Group_Joins
    (join_time TIMESTAMP,
    u_id CHAR(10),
    gid CHAR(10),
    PRIMARY KEY (u_id,gid),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (gid) REFERENCES Group_Record);

CREATE TABLE Group_Creates
    (create_time TIMESTAMP,
    u_id CHAR(10),
    gid CHAR(10),
    PRIMARY KEY (u_id,gid),
    FOREIGN KEY (u_id) REFERENCES User_info, FOREIGN KEY (gid) REFERENCES Group_Record);


CREATE TABLE GroupChat_Record
    (time TIMESTAMP,
    content CHAR(100),
    gid CHAR(10),
    PRIMARY KEY (u_id, gid, tie),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (gid) REFERENCES Group_Record ON DELETE CASCADE);

CREATE TABLE Meeting_Record
    (mid CHAR(10), attendance INTEGER, topic CHAR(50),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    gid CHAR (10) NOT NULL, PRIMARY KEY (mid),
    FOREIGN KEY (gid) REFERENCES Group_Record ON DELETE SET NULL);

CREATE TABLE Schedule_Record
    (sid CHAR(10),
    stime TIMESTAMP,
    event CHAR(20),
    u_id CHAR(10) NOT NULL,
    PRIMARY KEY (sid),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (stime) REFERENCES Task_Status);

CREATE TABLE Task_Status
    (stime TIMESTAMP,
    passed INTEGER, -- 0 is false, 1 is true
    PRIMARY KEY (stime));

CREATE TABLE Contain_Task
    (tname CHAR(50),
    Priority_val INTEGER,
    sid CHAR(10),
    PRIMARY KEY (sid, tname),
    FOREIGN KEY (sid) REFERENCES Schedule_Record ON DELETE CASCADE);



-- INSERT: load data
INSERT INTO time_zone VALUES ('Vancouver','GMT-8');
INSERT INTO time_zone VALUES ('Toronto', 'GMT-5');
INSERT INTO time_zone VALUES ('Beijing', 'GMT+8');
INSERT INTO time_zone VALUES ('London', 'GMT+1');
INSERT INTO time_zone VALUES ('Seattle', 'UTC-7');

INSERT INTO user_info VALUES ('0000','0000','admin','Vancouver','admin@gmail.com');
INSERT INTO user_info VALUES ('0001','0001','karry','Vancouver', 'karry@gmail');
INSERT INTO user_info VALUES ('0002','0002','dora','Vancouver', 'dora@gmail');
INSERT INTO user_info VALUES ('0003','0003','gelila','Beijing', 'gelila@gmail');
INSERT INTO user_info VALUES ('0004','0004','tony','London', 'tony@gmail');
INSERT INTO user_info VALUES ('0005','0005','mike','Toronto', 'mike@gmail');

INSERT INTO Individual_Chat VALUES ('2020-01-15 12:00:00', '0001', '0002', 'Hello! This is karry');
INSERT INTO Individual_Chat VALUES ('2020-01-15 12:30:00', '0002', '0001', 'Hello! This is dora');
INSERT INTO Individual_Chat VALUES ('2020-01-16 12:00:00', '0003', '0001', 'Hello! This is gelila');
INSERT INTO Individual_Chat VALUES ('2020-01-17 12:00:00', '0004', '0003', 'Hello! This is karry');
INSERT INTO Individual_Chat VALUES ('2020-01-18 12:00:00', '0005', '0001', 'Hello! This is mike');

INSERT INTO Share_Post VALUES ('p0001','2020-01-15 12:00:00', '0001', 'My first post!!');
INSERT INTO Share_Post VALUES ('p0002','2020-01-15 12:30:00', '0002', 'Hello everyone');
INSERT INTO Share_Post VALUES ('p0003','2020-01-16 12:00:00', '0003', 'Hello! THis is gelila');
INSERT INTO Share_Post VALUES ('p0004','2020-01-17 12:00:00', '0004', 'Hello! Karry from the north');
INSERT INTO Share_Post VALUES ('p0005','2020-01-18 12:00:00', '0005', 'Long time no see, Vancouver');

INSERT INTO Media VALUES ('m0001', 'Image');
INSERT INTO Media VALUES ('m0002', 'Music');
INSERT INTO Media VALUES ('m0003', 'Video');
INSERT INTO Media VALUES ('m0004', 'Image');
INSERT INTO Media VALUES ('m0005', 'Video');

INSERT INTO Post_Contains VALUES ('p0001', 'm0001');
INSERT INTO Post_Contains VALUES ('p0002', 'm0002');
INSERT INTO Post_Contains VALUES ('p0003','m0003');
INSERT INTO Post_Contains VALUES ('p0004','m0004');
INSERT INTO Post_Contains VALUES ('p0005','m0005');

INSERT INTO Mini_Program VALUES ('mi0001', 'Payroll Check', 'Work');
INSERT INTO Mini_Program VALUES ('mi0002', 'Monthly Report', 'Work');
INSERT INTO Mini_Program VALUES ('mi0003', 'Announcement', 'Work');
INSERT INTO Mini_Program VALUES ('mi0004', 'Payroll Check', 'Work');
INSERT INTO Mini_Program VALUES ('mi0005', 'Payroll Check', 'Work');

INSERT INTO MiniProgram_Record VALUES ('0001', 'mi0001','2020-01-15 12:00:00');
INSERT INTO MiniProgram_Record VALUES ('0002', 'mi0002','2020-01-15 12:30:00');
INSERT INTO MiniProgram_Record VALUES ('0003', 'mi0003','2020-01-16 12:00:00');
INSERT INTO MiniProgram_Record VALUES ('0004', 'mi0004','2020-01-17 12:00:00');
INSERT INTO MiniProgram_Record VALUES ('0005', 'mi0005','2020-01-18 12:00:00');

INSERT INTO Group_member VALUES ('FrozenCloud', '0001');
INSERT INTO Group_member VALUES ('Doooora', '0002');
INSERT INTO Group_member VALUES ('QQQ', '0003');
INSERT INTO Group_member VALUES ('7k+', '0004');
INSERT INTO Group_member VALUES ('magge', '0005');

INSERT INTO Group_administrator VALUES ('FrozenCloud', '00001');
INSERT INTO Group_administrator VALUES ('Doooora', '00002');
INSERT INTO Group_administrator VALUES ('QQQ', '0003');
INSERT INTO Group_administrator VALUES ('7k+', '0004');
INSERT INTO Group_administrator VALUES ('magge', '0005');

INSERT INTO Group_Joins VALUES ('2020-02-16 12:00:00', 'p0001', 'g0002');
INSERT INTO Group_Joins VALUES ('2020-02-17 12:00:00', 'p0002', 'g0001');
INSERT INTO Group_Joins VALUES ('2020-02-18 12:00:00', 'p0003', 'g0002');
INSERT INTO Group_Joins VALUES ('2020-02-19 12:00:00', 'p0004', 'g0003');
INSERT INTO Group_Joins VALUES ('2020-02-20 12:00:00', 'p0005', 'g0001');


INSERT INTO Group_Creates VALUES ('2020-02-15 12:00:00', 'p0001', 'g0001');
INSERT INTO Group_Creates VALUES ('2020-02-17 12:00:00', 'p0002', 'g0002');
INSERT INTO Group_Creates VALUES ('2020-02-18 12:00:00', 'p0003', 'g0003');
INSERT INTO Group_Creates VALUES ('2020-02-19 12:00:00', 'p0004', 'g0004');
INSERT INTO Group_Creates VALUES ('2020-02-20 12:00:00', 'p0005', 'g0005');

INSERT INTO Group_Record VALUES ('g0001', 'project_team1');
INSERT INTO Group_Record VALUES ('g0002', 'project_team2');
INSERT INTO Group_Record VALUES ('g0003', 'project_team3');
INSERT INTO Group_Record VALUES ('g0004', 'project_team4');
INSERT INTO Group_Record VALUES ('g0005', 'project_team5');

INSERT INTO GroupChat_Record VALUES ('0001', 'Nice to see you guys! This is karry.', 'g0001');
INSERT INTO GroupChat_Record VALUES ('0002', 'Nice to see you guys! This is dora.', 'g0001');
INSERT INTO GroupChat_Record VALUES ('0003', 'Nice to see you guys! This is gelila.', 'g0001');
INSERT INTO GroupChat_Record VALUES ('0004', 'Nice to see you guys! This is tony.', 'g0001');
INSERT INTO GroupChat_Record VALUES ('0005', 'Nice to see you guys! This is Mike.', 'g0001');

INSERT INTO Meeting_Record VALUES ('m0001', 10, 'Welcome ceremony', '2020-08-16 12:00:00'
,'2020-08-16 14:00:00', 'g0001');
INSERT INTO Meeting_Record VALUES ('m0001', 5, 'team discussion', '2020-08-19 12:00:00'
,'2020-08-19 13:00:00', 'g0001');
INSERT INTO Meeting_Record VALUES ('m0001', 6, 'project discussion', '2020-09-10 12:00:00'
,'2020-08-16 16:00:00', 'g0001');
INSERT INTO Meeting_Record VALUES ('m0001', 7, 'Welcome ceremony', '2020-08-16 12:00:00'
,'2020-08-16 14:00:00', 'g0002');
INSERT INTO Meeting_Record VALUES ('m0001', 4, 'random chatting', '2020-08-20 17:00:00'
,'2020-08-16 18:00:00', 'g0002');

INSERT INTO Schedule_Record VALUES ('s0001', '2020-08-16 12:00:00', 'event1', '0001');
INSERT INTO Schedule_Record VALUES ('s0002', '2020-08-17 14:00:00', 'event2', '0001');
INSERT INTO Schedule_Record VALUES ('s0003', '2020-08-18 16:00:00', 'event3', '0002');
INSERT INTO Schedule_Record VALUES ('s0004', '2020-08-19 16:00:00', 'event4', '0002');
INSERT INTO Schedule_Record VALUES ('s0005', '2020-08-20 17:00:00', 'event5', '0003');

INSERT INTO Task_Status VALUES ('2020-08-16 12:00:00', 0);
INSERT INTO Task_Status VALUES ('2020-08-17 14:00:00', 1);
INSERT INTO Task_Status VALUES ('2020-08-18 16:00:00', 1);
INSERT INTO Task_Status VALUES ('2020-08-19 16:00:00', 1);
INSERT INTO Task_Status VALUES ('2020-08-20 17:00:00', 0);

INSERT INTO Contain_Task VALUES ('Finish Milestone1', 1, 's0001');
INSERT INTO Contain_Task VALUES ('Finish Milestone2', 3, 's0002');
INSERT INTO Contain_Task VALUES ('Finish Milestone3', 2, 's0003');
INSERT INTO Contain_Task VALUES ('Finish Milestone4', 4, 's0004');
INSERT INTO Contain_Task VALUES ('Finish Milestone5', 2, 's0005');

-- Queries
-- DELETE Operation

-- UPTIMESTAMP Operation

-- Selection
-- SELECT *
-- FROM user_info, time_zone
-- WHERE user_info.city = time_zone.city;

-- SELECT *
-- FROM user_info, time_zone
-- WHERE user_id = ' + userid + '
--     AND user_info.city = time_zone.city;

-- -- Projection
-- SELECT city FROM time_zone;
-- select user_id from user_info;
-- select user_id, u_password from user_info;

-- JOIN

-- Aggregation with GROUP BY

-- Aggregation with HAVING

-- Nested Aggregation with GROUP BY

-- Division