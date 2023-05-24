drop table if exists PostReply,PostCity,city,PostCategory,category,UserReply,
    Replies,UserWritePost,UserLikePost,
    UserSharePost,UserFavoritePost,posts,UserFollowUser,UserBlockUser,users;

-- 创建序列
DROP SEQUENCE IF EXISTS user_id_seq, replies_reply_id_seq;
CREATE SEQUENCE user_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE replies_reply_id_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS users
(
    username          VARCHAR(50) primary key,
    registration_time TIMESTAMP          not null,
    phone_number      VARCHAR(50) unique not null,
    user_id           VARCHAR(20)        not null,
    password          VARCHAR(20)        not null
);

CREATE TABLE IF NOT EXISTS posts
(
    post_id      SERIAL PRIMARY KEY,
    title        VARCHAR(100)  not null,
    content      VARCHAR(1000) not null,
    posting_time TIMESTAMP     not null,
    shared       BIGINT        not null
);

CREATE TABLE IF NOT EXISTS Replies
(
    reply_id  SERIAL PRIMARY KEY,
    content   VARCHAR(1000) not null,
    stars     BIGINT        not null,
    anonymous BOOLEAN       not null,
    parent_id BIGINT
);

CREATE TABLE IF NOT EXISTS Category
(
    categoryid SERIAL primary key,
    category    varchar(30) not null
);

CREATE TABLE IF NOT EXISTS City
(
    city_id BIGINT primary key,
    city    varchar(20) not null,
    country varchar(20) not null,

    CONSTRAINT country_city UNIQUE (city, country)

);

CREATE TABLE IF NOT EXISTS PostReply
(
    post_id  BIGINT not null,
    reply_id BIGINT not null,

    foreign key (post_id) references posts (post_id),
    foreign key (reply_id) references Replies (reply_id),

    primary key (reply_id, post_id)
);

CREATE TABLE IF NOT EXISTS PostCity
(
    post_id BIGINT not null,
    city_id BIGINT not null,

    foreign key (post_id) references posts (post_id),
    foreign key (city_id) references city (city_id),

    primary key (post_id, city_id)
);

CREATE TABLE IF NOT EXISTS PostCategory
(
    categoryid BIGINT not null,
    post_id     BIGINT not null,

    foreign key (categoryid) references Category (categoryid),
    foreign key (post_id) references posts (post_id),

    primary key (categoryid, post_id)
);

CREATE TABLE IF NOT EXISTS UserReply
(
    user_name VARCHAR(50) not null,
    reply_id  BIGINT      not null,

    foreign key (user_name) references users (username),
    foreign key (reply_id) references Replies (reply_id),

    primary key (reply_id, user_name)
);

CREATE TABLE IF NOT EXISTS UserWritePost
(
    post_id   BIGINT      not null,
    user_name varchar(50) not null,

    foreign key (post_id) references posts (post_id),
    foreign key (user_name) references users (username),

    primary key (post_id, user_name)
);

CREATE TABLE IF NOT EXISTS UserLikePost
(
    post_id   BIGINT      not null,
    user_name varchar(50) not null,

    foreign key (post_id) references posts (post_id),
    foreign key (user_name) references users (username),

    primary key (post_id, user_name)
);

CREATE TABLE IF NOT EXISTS UserFavoritePost
(
    post_id   BIGINT      not null,
    user_name varchar(50) not null,

    foreign key (post_id) references posts (post_id),
    foreign key (user_name) references users (username),

    primary key (post_id, user_name)
);

CREATE TABLE IF NOT EXISTS UserFollowUser
(
    user_follower    VARCHAR(50) not null,
    user_be_followed VARCHAR(50) not null,

    foreign key (user_follower) references users (username),
    foreign key (user_be_followed) references users (username),

    primary key (user_follower, user_be_followed)
);

CREATE TABLE IF NOT EXISTS UserBlockUser
(
    user_blocker    VARCHAR(50) not null,
    user_be_blocked VARCHAR(50) not null,

    foreign key (user_blocker) references users (username),
    foreign key (user_be_blocked) references users (username),

    primary key (user_blocker, user_be_blocked)
);



SELECT setval('user_id_seq', (SELECT max(CAST(user_id AS INT)) FROM users) + 1);

CREATE OR REPLACE FUNCTION users_trigger_function()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.user_id IS NULL OR TRIM(NEW.user_id) = '' THEN
        NEW.user_id := to_char(nextval('user_id_seq'), 'FM000000000');
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_trigger
    BEFORE INSERT
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE users_trigger_function();
CREATE OR REPLACE FUNCTION assign_id()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.reply_id IS NULL THEN
        SELECT nextval('replies_reply_id_seq') INTO NEW.reply_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER assign_id_trigger
    BEFORE INSERT
    ON Replies
    FOR EACH ROW
EXECUTE PROCEDURE assign_id();

insert into users (username, registration_time, phone_number, password)
VALUES ('bing', now(), '100000', '123456');
insert into users (username, registration_time, phone_number, password)
VALUES ('ding', now(), '100001', '123456');

insert into posts (title, content, posting_time, shared) VALUES (1, 'adbacebafa',now(),0);
insert into UserWritePost (post_id, user_name) values (1,'bing');
insert into posts (title, content, posting_time, shared) VALUES (2, 'bfebadbcaa',now(),0);
insert into UserWritePost (post_id, user_name) values (2,'bing');
insert into posts (title, content, posting_time, shared) VALUES (3, 'ecdccfefda',now(),0);
insert into UserWritePost (post_id, user_name) values (3,'bing');
insert into posts (title, content, posting_time, shared) VALUES (4, 'bbbbdfeaaa',now(),0);
insert into UserWritePost (post_id, user_name) values (4,'bing');
insert into posts (title, content, posting_time, shared) VALUES (5, 'eedcffedba',now(),0);
insert into UserWritePost (post_id, user_name) values (5,'bing');
insert into posts (title, content, posting_time, shared) VALUES (6, 'aedcdeaade',now(),0);
insert into UserWritePost (post_id, user_name) values (6,'bing');
insert into posts (title, content, posting_time, shared) VALUES (7, 'faefeeedda',now(),0);
insert into UserWritePost (post_id, user_name) values (7,'bing');
insert into posts (title, content, posting_time, shared) VALUES (8, 'bdbbeafade',now(),0);
insert into UserWritePost (post_id, user_name) values (8,'bing');
insert into posts (title, content, posting_time, shared) VALUES (9, 'ccccbacdea',now(),0);
insert into UserWritePost (post_id, user_name) values (9,'bing');
insert into posts (title, content, posting_time, shared) VALUES (10, 'efafcbfbbb',now(),0);
insert into UserWritePost (post_id, user_name) values (10,'bing');
insert into posts (title, content, posting_time, shared) VALUES (11, 'accfaddcdb',now(),0);
insert into UserWritePost (post_id, user_name) values (11,'bing');
insert into posts (title, content, posting_time, shared) VALUES (12, 'decbbfffdf',now(),0);
insert into UserWritePost (post_id, user_name) values (12,'bing');
insert into posts (title, content, posting_time, shared) VALUES (13, 'cabdfbedfc',now(),0);
insert into UserWritePost (post_id, user_name) values (13,'bing');
insert into posts (title, content, posting_time, shared) VALUES (14, 'fbeeadebbd',now(),0);
insert into UserWritePost (post_id, user_name) values (14,'bing');
insert into posts (title, content, posting_time, shared) VALUES (15, 'ceecaefdad',now(),0);
insert into UserWritePost (post_id, user_name) values (15,'bing');
insert into posts (title, content, posting_time, shared) VALUES (16, 'efdcdcfeed',now(),0);
insert into UserWritePost (post_id, user_name) values (16,'bing');
insert into posts (title, content, posting_time, shared) VALUES (17, 'edadfaabff',now(),0);
insert into UserWritePost (post_id, user_name) values (17,'bing');
insert into posts (title, content, posting_time, shared) VALUES (18, 'fcfebbfecd',now(),0);
insert into UserWritePost (post_id, user_name) values (18,'bing');
insert into posts (title, content, posting_time, shared) VALUES (19, 'badeddffbc',now(),0);
insert into UserWritePost (post_id, user_name) values (19,'bing');
insert into posts (title, content, posting_time, shared) VALUES (20, 'edbbdeacdd',now(),0);
insert into UserWritePost (post_id, user_name) values (20,'bing');
insert into posts (title, content, posting_time, shared) VALUES (21, 'eceefcdddf',now(),0);
insert into UserWritePost (post_id, user_name) values (21,'bing');
insert into posts (title, content, posting_time, shared) VALUES (22, 'febbdfbfdf',now(),0);
insert into UserWritePost (post_id, user_name) values (22,'bing');
insert into posts (title, content, posting_time, shared) VALUES (23, 'bbdaaddebc',now(),0);
insert into UserWritePost (post_id, user_name) values (23,'bing');
insert into posts (title, content, posting_time, shared) VALUES (24, 'aeddaddaca',now(),0);
insert into UserWritePost (post_id, user_name) values (24,'bing');
insert into posts (title, content, posting_time, shared) VALUES (25, 'dabadabafa',now(),0);
insert into UserWritePost (post_id, user_name) values (25,'bing');
insert into posts (title, content, posting_time, shared) VALUES (26, 'aceffaedaf',now(),0);
insert into UserWritePost (post_id, user_name) values (26,'bing');
insert into posts (title, content, posting_time, shared) VALUES (27, 'bfeebaabad',now(),0);
insert into UserWritePost (post_id, user_name) values (27,'bing');
insert into posts (title, content, posting_time, shared) VALUES (28, 'aefaaaabbf',now(),0);
insert into UserWritePost (post_id, user_name) values (28,'bing');
insert into posts (title, content, posting_time, shared) VALUES (29, 'bfedbdbbff',now(),0);
insert into UserWritePost (post_id, user_name) values (29,'bing');
insert into posts (title, content, posting_time, shared) VALUES (30, 'cdcbfbfebb',now(),0);
insert into UserWritePost (post_id, user_name) values (30,'bing');
insert into posts (title, content, posting_time, shared) VALUES (31, 'fbcbacdbda',now(),0);
insert into UserWritePost (post_id, user_name) values (31,'bing');
insert into posts (title, content, posting_time, shared) VALUES (32, 'dcbffbfbde',now(),0);
insert into UserWritePost (post_id, user_name) values (32,'bing');
insert into posts (title, content, posting_time, shared) VALUES (33, 'edcfbeecfc',now(),0);
insert into UserWritePost (post_id, user_name) values (33,'bing');
insert into posts (title, content, posting_time, shared) VALUES (34, 'dcfdfccbfa',now(),0);
insert into UserWritePost (post_id, user_name) values (34,'bing');
insert into posts (title, content, posting_time, shared) VALUES (35, 'bfaefebfcb',now(),0);
insert into UserWritePost (post_id, user_name) values (35,'bing');
insert into posts (title, content, posting_time, shared) VALUES (36, 'bbecbcfbbd',now(),0);
insert into UserWritePost (post_id, user_name) values (36,'bing');
insert into posts (title, content, posting_time, shared) VALUES (37, 'bfabfeffed',now(),0);
insert into UserWritePost (post_id, user_name) values (37,'bing');
insert into posts (title, content, posting_time, shared) VALUES (38, 'ebccadfacb',now(),0);
insert into UserWritePost (post_id, user_name) values (38,'bing');
insert into posts (title, content, posting_time, shared) VALUES (39, 'afaefdfdbd',now(),0);
insert into UserWritePost (post_id, user_name) values (39,'bing');
insert into posts (title, content, posting_time, shared) VALUES (40, 'aadcfbefea',now(),0);
insert into UserWritePost (post_id, user_name) values (40,'bing');
insert into posts (title, content, posting_time, shared) VALUES (41, 'cdeffefdfd',now(),0);
insert into UserWritePost (post_id, user_name) values (41,'bing');
insert into posts (title, content, posting_time, shared) VALUES (42, 'ebabbacada',now(),0);
insert into UserWritePost (post_id, user_name) values (42,'bing');
insert into posts (title, content, posting_time, shared) VALUES (43, 'aedbbebadc',now(),0);
insert into UserWritePost (post_id, user_name) values (43,'bing');
insert into posts (title, content, posting_time, shared) VALUES (44, 'eaeccdafef',now(),0);
insert into UserWritePost (post_id, user_name) values (44,'bing');
insert into posts (title, content, posting_time, shared) VALUES (45, 'dcafaecdcf',now(),0);
insert into UserWritePost (post_id, user_name) values (45,'bing');
insert into posts (title, content, posting_time, shared) VALUES (46, 'ccdddfacdd',now(),0);
insert into UserWritePost (post_id, user_name) values (46,'bing');
insert into posts (title, content, posting_time, shared) VALUES (47, 'ecefedfbed',now(),0);
insert into UserWritePost (post_id, user_name) values (47,'bing');
insert into posts (title, content, posting_time, shared) VALUES (48, 'cdbcfcecdb',now(),0);
insert into UserWritePost (post_id, user_name) values (48,'bing');
insert into posts (title, content, posting_time, shared) VALUES (49, 'bccafcfafe',now(),0);
insert into UserWritePost (post_id, user_name) values (49,'bing');
insert into posts (title, content, posting_time, shared) VALUES (50, 'dfefdcabda',now(),0);
insert into UserWritePost (post_id, user_name) values (50,'bing');
insert into posts (title, content, posting_time, shared) VALUES (51, 'fdbfbddafd',now(),0);
insert into UserWritePost (post_id, user_name) values (51,'ding');
insert into posts (title, content, posting_time, shared) VALUES (52, 'dceeacbeec',now(),0);
insert into UserWritePost (post_id, user_name) values (52,'ding');
insert into posts (title, content, posting_time, shared) VALUES (53, 'efcbdaddfc',now(),0);
insert into UserWritePost (post_id, user_name) values (53,'ding');
insert into posts (title, content, posting_time, shared) VALUES (54, 'bfdaeeaada',now(),0);
insert into UserWritePost (post_id, user_name) values (54,'ding');
insert into posts (title, content, posting_time, shared) VALUES (55, 'fbeabdceed',now(),0);
insert into UserWritePost (post_id, user_name) values (55,'ding');
insert into posts (title, content, posting_time, shared) VALUES (56, 'cfcdfedfba',now(),0);
insert into UserWritePost (post_id, user_name) values (56,'ding');
insert into posts (title, content, posting_time, shared) VALUES (57, 'edfedfbbef',now(),0);
insert into UserWritePost (post_id, user_name) values (57,'ding');
insert into posts (title, content, posting_time, shared) VALUES (58, 'cbdffaaded',now(),0);
insert into UserWritePost (post_id, user_name) values (58,'ding');
insert into posts (title, content, posting_time, shared) VALUES (59, 'ccccfbafff',now(),0);
insert into UserWritePost (post_id, user_name) values (59,'ding');
insert into posts (title, content, posting_time, shared) VALUES (60, 'debddecafc',now(),0);
insert into UserWritePost (post_id, user_name) values (60,'ding');
