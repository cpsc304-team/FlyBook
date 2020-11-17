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
    (time DATE,
    sender CHAR(20), content CHAR(100),
    u_id1 CHAR(10),
    u_id2 CHAR(10),
    PRIMARY KEY (u_id1, u_id2, time),
    FOREIGN KEY (u_id1) REFERENCES User_info ON DELETE CASCADE,
    FOREIGN KEY (u_id2) REFERENCES User_info ON DELETE CASCADE);


CREATE TABLE Share_Post
    (postid CHAR(10),
    time DATE,
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
    time DATE,
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
    gid CHAR(10),
    gname CHAR(20),
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_id) REFERENCES User_info);

CREATE TABLE Group_Record
    (gid CHAR(10),
    gname CHAR(20),
    PRIMARY KEY (gid));



CREATE TABLE Group_Joins
    (join_time DATE,
    u_id CHAR(10),
    gid CHAR(10),
    PRIMARY KEY (u_id,gid),
    FOREIGN KEY (u_id) REFERENCES User_info, FOREIGN KEY (gid) REFERENCES Group_Record);

CREATE TABLE Group_Creates
    (create_time DATE,
    u_id CHAR(10),
    gid CHAR(10),
    PRIMARY KEY (u_id,gid),
    FOREIGN KEY (u_id) REFERENCES User_info, FOREIGN KEY (gid) REFERENCES Group_Record);


CREATE TABLE GroupChat_Record
    (time DATE,
    sender CHAR(20),
    content CHAR(100),
    gid CHAR(10),
    PRIMARY KEY (gid, time),
    FOREIGN KEY (gid) REFERENCES Group_Record ON DELETE CASCADE);

CREATE TABLE Meeting_Record
    (mid CHAR(10), attendance INTEGER, topic CHAR(50),
    start_time DATE,
    end_time DATE,
    gid CHAR (10) NOT NULL, PRIMARY KEY (mid),
    FOREIGN KEY (gid) REFERENCES Group_Record ON DELETE SET NULL);

CREATE TABLE Schedule_Record
    (sid CHAR(10),
    stime DATE,
    event CHAR(20),
    u_id CHAR(10) NOT NULL,
    PRIMARY KEY (sid),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (stime) REFERENCES Task_Status);

CREATE TABLE Task_Status
    (stime DATE,
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
INSERT INTO user_info VALUES ("0000","0000","admin","Vancouver","admin@gmail.com");


-- Queries
-- DELETE Operation

-- UPDATE Operation

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