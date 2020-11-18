-- Below are the SQL sentences used in DatabaseConnection.java after debugging

-- SQL DDL and Loading Data
-- CREATE TABLE: set up the database
CREATE TABLE user_info (
    user_id varchar2(10) NOT NULL PRIMARY KEY,
    password varchar2(10) NOT NULL,
    name varchar2(20) NOT NULL,
    city varchar2(20),
    email varchar2(100));

CREATE TABLE time_zone (
    city varchar2(20) NOT NULL PRIMARY KEY,
    time_zone varchar2(5) NOT NULL);

CREATE TABLE individual_chat (
    time TIMESTAMP,
    sender varchar2(10),
    receiver varchar2(10),
    content varchar2(100),
    PRIMARY KEY (sender, receiver, time),
    FOREIGN KEY (sender) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (receiver) REFERENCES user_info ON DELETE CASCADE);

CREATE TABLE media (
    media_id varchar2(10),
    media_type varchar2(20),
    url varchar2(1000),
    PRIMARY KEY (media_id));

CREATE TABLE share_post (
    post_id varchar2(10),
    time TIMESTAMP,
    user_id varchar2(10) NOT NULL,
    content varchar2(100),
    media_id varchar2(10),
    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (media_id) REFERENCES media);


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

INSERT INTO media VALUES ('M1 ', 'Image ', 'https://i.imgur.com/QwEq1g2.jpg');
INSERT INTO media VALUES ('M2 ', 'Music ', 'https://music.163.com/#/song?id=1405903472');
INSERT INTO media VALUES ('M3 ', 'Video ', 'https://www.youtube.com/watch?v=BoZ0Zwab6Oc');
INSERT INTO media VALUES ('M4 ', 'Image ', 'https://i.imgur.com/veHL0mf.jpg');
INSERT INTO media VALUES ('M5 ', 'Video ', 'https://www.youtube.com/watch?v=HXV3zeQKqGY');

INSERT INTO share_post VALUES ('P1', '2020-11-17 12:00:00', '0003', 'My first post!!', null);
INSERT INTO share_post VALUES ('P2', '2020-11-17 12:30:00', '0002', 'Hello everyone', null);
INSERT INTO share_post VALUES ('P3', '2020-11-17 19:40:06', '0001', 'Hello! This is Gelila', 'M2');
INSERT INTO share_post VALUES ('P4', '2020-11-18 11:09:25', '0002', 'Favourite sushi', 'M1');
INSERT INTO share_post VALUES ('P5', '2020-11-18 18:05:20', '0005', 'Long time no see, Vancouver', 'M4');


-- Queries
-- DELETE Operation
-- DELETE FROM user_info WHERE user_id = '0001';

-- UPDATE Operation
-- UPDATE user_info SET password = '1111' WHERE user_id = '0001';

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