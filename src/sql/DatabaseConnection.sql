-- Below are the SQL sentences used in DatabaseConnection.java after debugging

/*
 * ==================================================================
 *
 * SQL DDL and Loading Data
 *
 * ==================================================================
 * */
-- CREATE TABLE: set up the database
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

-- Nested Aggregation with GROUP BY

-- Division