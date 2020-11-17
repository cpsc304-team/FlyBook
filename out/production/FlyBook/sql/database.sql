-- Paste the SQL used in DatabaseConnection.java here

-- SQL DDL and Loading Data
-- CREATE TABLE: set up the database
DROP TABLE User_info;
DROP TABLE Time_Zone;
DROP TABLE Individual_Chat;
DROP TABLE Share_Post;
DROP TABLE Media;
DROP TABLE Post_Contains;
DROP TABLE Mini_Program;
DROP TABLE MiniProgram_Record;
DROP TABLE Group_Member;
DROP TABLE Group_administrator;
DROP TABLE Group_Joins;
DROP TABLE Group_Creates;
DROP TABLE GroupChat_Record;
DROP TABLE Meeting_Record;
DROP TABLE Schedule_Record;
DROP TABLE Task_Status;
DROP TABLE Contain_Task;


CREATE TABLE User_info
    (u_id CHAR(10),
    password CHAR(20),
    u_name CHAR(20),
    city CHAR(20),
    email CHAR(20) UNIQUE,
    PRIMARY KEY (u_id),
    FOREIGN KEY (city) REFERENCES Time_Zone);

CREATE TABLE Time_Zone
    (city CHAR(20),
    time_zone CHAR(5),
    PRIMARY KEY (city));

CREATE TABLE Individual_Chat
    (time TIMESTAMP,
    sender CHAR(20), content CHAR(100),
    u_id1 CHAR(10),
    u_id2 CHAR(10),
    PRIMARY KEY (u_id1, u_id2, time),
    FOREIGN KEY (u_id1) REFERENCES User_info ON DELETE CASCADE,
    FOREIGN KEY (u_id2) REFERENCES User_info ON DELETE CASCADE);


CREATE TABLE Share_Post
    (postid CHAR(10),
    post_TIMESTAMP TIMESTAMP,
    post_time CHAR(20),
    content CHAR(1000),
    u_id CHAR(10) NOT NULL,
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
    sender CHAR(20),
    content CHAR(100),
    gid CHAR(10),
    PRIMARY KEY (gid, time),
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
    (tname CHAR(10),
    Priority_val INTEGER,
    sid CHAR(10),
    PRIMARY KEY (sid, tname),
    FOREIGN KEY (sid) REFERENCES Schedule_Record ON DELETE CASCADE);



-- INSERT: load data
INSERT INTO time_zone VALUES ("Vancouver","GMT-8");
INSERT INTO time_zone VALUES ("Toronto", "GMT-5");
INSERT INTO time_zone VALUES ("Beijing", "GMT+8");
INSERT INTO time_zone VALUES ("London", "GMT+1");
INSERT INTO time_zone VALUES ("Seattle", "UTC-7");

INSERT INTO user_info VALUES ("0000","0000","admin","Vancouver","admin@gmail.com");
INSERT INTO user_info VALUES ("0001","0001","karry","Vancouver", "karry@gmail");
INSERT INTO user_info VALUES ("0002","0002","dora","Vancouver", "dora@gmail");
INSERT INTO user_info VALUES ("0003","0003","gelila","Beijing", "gelila@gmail");
INSERT INTO user_info VALUES ("0004","0004","tony","London", "tony@gmail");
INSERT INTO user_info VALUES ("0005","0005","mike","Toronto", "mike@gmail");

INSERT INTO Individual_Chat VALUES ('2020-01-15 12:00:00', "karry", "Hello! I'm karry", 0001);
INSERT INTO Individual_Chat VALUES ('2020-01-15 12:30:00', "dora", "Hello! I'm dora", 0002);
INSERT INTO Individual_Chat VALUES ('2020-01-16 12:00:00', "gelila", "Hello! I'm gelila", 0003);
INSERT INTO Individual_Chat VALUES ('2020-01-17 12:00:00', "karry", "Hello! I'm karry", 0004);
INSERT INTO Individual_Chat VALUES ('2020-01-18 12:00:00', "mike", "Hello! I'm mike", 0005);

INSERT INTO Share_Post VALUES ("p0001",'2020-01-15 12:00:00', "karry", "My first post!!", 0001);
INSERT INTO Share_Post VALUES ("p0002",'2020-01-15 12:30:00', "dora", "Hello everyone", 0002);
INSERT INTO Share_Post VALUES ("p0003",'2020-01-16 12:00:00', "gelila", "Hello! I'm gelila", 0003);
INSERT INTO Share_Post VALUES ("p0004",'2020-01-17 12:00:00', "karry", "Hello! Karry from the north", 0004);
INSERT INTO Share_Post VALUES ("p0005",'2020-01-18 12:00:00', "mike", "Long time no see, Vancouver", 0005);

INSERT INTO Media VALUES ("m0001", "Image");
INSERT INTO Media VALUES ("m0002", "Music");
INSERT INTO Media VALUES ("m0003", "Video");
INSERT INTO Media VALUES ("m0004", "Image");
INSERT INTO Media VALUES ("m0005", "Video");

INSERT INTO 



-- Queries
-- DELETE Operation

-- UPTIMESTAMP Operation

-- Selection
SELECT *
FROM user_info, time_zone
WHERE user_info.city = time_zone.city;

SELECT *
FROM user_info, time_zone
WHERE user_id = " + userid + "
    AND user_info.city = time_zone.city;

-- Projection
SELECT city FROM time_zone;
select user_id from user_info;
select user_id, password from user_info;

-- JOIN

-- Aggregation with GROUP BY

-- Aggregation with HAVING

-- Nested Aggregation with GROUP BY

-- Division