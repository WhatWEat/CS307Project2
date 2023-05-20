drop table if exists PostReply,PostCity,city,PostCategory,category,UserReply,
    Replies,UserWritePost,UserLikePost,
    UserSharePost,UserFavoritePost,posts,UserFollowUser,UserBlockUser,users;


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
    posting_time TIMESTAMP     not null
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
    category_id BIGINT primary key,
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
    category_id BIGINT not null,
    post_id     BIGINT not null,

    foreign key (category_id) references Category (category_id),
    foreign key (post_id) references posts (post_id),

    primary key (category_id, post_id)
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

CREATE TABLE IF NOT EXISTS UserSharePost
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

-- 创建序列
CREATE SEQUENCE user_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE replies_reply_id_seq START 1 INCREMENT 1;



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
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.reply_id IS NULL THEN
        SELECT nextval('replies_reply_id_seq') INTO NEW.reply_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER assign_id_trigger
    BEFORE INSERT ON Replies
    FOR EACH ROW
EXECUTE PROCEDURE assign_id();

insert into users (username, registration_time, phone_number, password) VALUES ('bing',now(),'100000','123456');

insert into posts (title, content, posting_time) VALUES (1, 'debfdaeadc',now());
insert into UserWritePost (post_id, user_name) values (1,'bing');
insert into posts (title, content, posting_time) VALUES (2, 'cfbcddfecc',now());
insert into UserWritePost (post_id, user_name) values (2,'bing');
insert into posts (title, content, posting_time) VALUES (3, 'bbfdaefdaa',now());
insert into UserWritePost (post_id, user_name) values (3,'bing');
insert into posts (title, content, posting_time) VALUES (4, 'bcadddccde',now());
insert into UserWritePost (post_id, user_name) values (4,'bing');
insert into posts (title, content, posting_time) VALUES (5, 'ddbcbefdbb',now());
insert into UserWritePost (post_id, user_name) values (5,'bing');
insert into posts (title, content, posting_time) VALUES (6, 'ddfaeafccd',now());
insert into UserWritePost (post_id, user_name) values (6,'bing');
insert into posts (title, content, posting_time) VALUES (7, 'dacdaedbac',now());
insert into UserWritePost (post_id, user_name) values (7,'bing');
insert into posts (title, content, posting_time) VALUES (8, 'eaddecefdd',now());
insert into UserWritePost (post_id, user_name) values (8,'bing');
insert into posts (title, content, posting_time) VALUES (9, 'fcdcacfeab',now());
insert into UserWritePost (post_id, user_name) values (9,'bing');
insert into posts (title, content, posting_time) VALUES (10, 'abffbedebe',now());
insert into UserWritePost (post_id, user_name) values (10,'bing');
insert into posts (title, content, posting_time) VALUES (11, 'bbaeadbecf',now());
insert into UserWritePost (post_id, user_name) values (11,'bing');
insert into posts (title, content, posting_time) VALUES (12, 'adecdddded',now());
insert into UserWritePost (post_id, user_name) values (12,'bing');
insert into posts (title, content, posting_time) VALUES (13, 'eddfbddccb',now());
insert into UserWritePost (post_id, user_name) values (13,'bing');
insert into posts (title, content, posting_time) VALUES (14, 'bfcfeadcdc',now());
insert into UserWritePost (post_id, user_name) values (14,'bing');
insert into posts (title, content, posting_time) VALUES (15, 'dcfbcfdade',now());
insert into UserWritePost (post_id, user_name) values (15,'bing');
insert into posts (title, content, posting_time) VALUES (16, 'caedefdecf',now());
insert into UserWritePost (post_id, user_name) values (16,'bing');
insert into posts (title, content, posting_time) VALUES (17, 'dbfcbdaeaf',now());
insert into UserWritePost (post_id, user_name) values (17,'bing');
insert into posts (title, content, posting_time) VALUES (18, 'dbcddcacda',now());
insert into UserWritePost (post_id, user_name) values (18,'bing');
insert into posts (title, content, posting_time) VALUES (19, 'fbfdcdebcc',now());
insert into UserWritePost (post_id, user_name) values (19,'bing');
insert into posts (title, content, posting_time) VALUES (20, 'afcbcfdeff',now());
insert into UserWritePost (post_id, user_name) values (20,'bing');
insert into posts (title, content, posting_time) VALUES (21, 'cebeccfbca',now());
insert into UserWritePost (post_id, user_name) values (21,'bing');
insert into posts (title, content, posting_time) VALUES (22, 'fbccfeebbf',now());
insert into UserWritePost (post_id, user_name) values (22,'bing');
insert into posts (title, content, posting_time) VALUES (23, 'fddaebfbcc',now());
insert into UserWritePost (post_id, user_name) values (23,'bing');
insert into posts (title, content, posting_time) VALUES (24, 'deaedafcea',now());
insert into UserWritePost (post_id, user_name) values (24,'bing');
insert into posts (title, content, posting_time) VALUES (25, 'bebfdfcdab',now());
insert into UserWritePost (post_id, user_name) values (25,'bing');
insert into posts (title, content, posting_time) VALUES (26, 'affeeffcbc',now());
insert into UserWritePost (post_id, user_name) values (26,'bing');
insert into posts (title, content, posting_time) VALUES (27, 'fbecafcfde',now());
insert into UserWritePost (post_id, user_name) values (27,'bing');
insert into posts (title, content, posting_time) VALUES (28, 'dabffedcaa',now());
insert into UserWritePost (post_id, user_name) values (28,'bing');
insert into posts (title, content, posting_time) VALUES (29, 'babbadecce',now());
insert into UserWritePost (post_id, user_name) values (29,'bing');
insert into posts (title, content, posting_time) VALUES (30, 'aafdddebbf',now());
insert into UserWritePost (post_id, user_name) values (30,'bing');
insert into posts (title, content, posting_time) VALUES (31, 'dafefdcbaf',now());
insert into UserWritePost (post_id, user_name) values (31,'bing');
insert into posts (title, content, posting_time) VALUES (32, 'fcecfcbaff',now());
insert into UserWritePost (post_id, user_name) values (32,'bing');
insert into posts (title, content, posting_time) VALUES (33, 'ebcbdacdca',now());
insert into UserWritePost (post_id, user_name) values (33,'bing');
insert into posts (title, content, posting_time) VALUES (34, 'cfcacbbbbd',now());
insert into UserWritePost (post_id, user_name) values (34,'bing');
insert into posts (title, content, posting_time) VALUES (35, 'feecacdaac',now());
insert into UserWritePost (post_id, user_name) values (35,'bing');
insert into posts (title, content, posting_time) VALUES (36, 'bdfafcafae',now());
insert into UserWritePost (post_id, user_name) values (36,'bing');
insert into posts (title, content, posting_time) VALUES (37, 'ecbefdbffa',now());
insert into UserWritePost (post_id, user_name) values (37,'bing');
insert into posts (title, content, posting_time) VALUES (38, 'adacfcacdf',now());
insert into UserWritePost (post_id, user_name) values (38,'bing');
insert into posts (title, content, posting_time) VALUES (39, 'afdcdfeebc',now());
insert into UserWritePost (post_id, user_name) values (39,'bing');
insert into posts (title, content, posting_time) VALUES (40, 'cdbaffabca',now());
insert into UserWritePost (post_id, user_name) values (40,'bing');
insert into posts (title, content, posting_time) VALUES (41, 'dfffebeaaa',now());
insert into UserWritePost (post_id, user_name) values (41,'bing');
insert into posts (title, content, posting_time) VALUES (42, 'dcadfacfec',now());
insert into UserWritePost (post_id, user_name) values (42,'bing');
insert into posts (title, content, posting_time) VALUES (43, 'bfcbeedbac',now());
insert into UserWritePost (post_id, user_name) values (43,'bing');
insert into posts (title, content, posting_time) VALUES (44, 'debdceffee',now());
insert into UserWritePost (post_id, user_name) values (44,'bing');
insert into posts (title, content, posting_time) VALUES (45, 'efaefcfbae',now());
insert into UserWritePost (post_id, user_name) values (45,'bing');
insert into posts (title, content, posting_time) VALUES (46, 'acdcedddea',now());
insert into UserWritePost (post_id, user_name) values (46,'bing');
insert into posts (title, content, posting_time) VALUES (47, 'fabadedfcb',now());
insert into UserWritePost (post_id, user_name) values (47,'bing');
insert into posts (title, content, posting_time) VALUES (48, 'fecdbfeefa',now());
insert into UserWritePost (post_id, user_name) values (48,'bing');
insert into posts (title, content, posting_time) VALUES (49, 'ffddaaadee',now());
insert into UserWritePost (post_id, user_name) values (49,'bing');
insert into posts (title, content, posting_time) VALUES (50, 'eddfdcbdcc',now());
insert into UserWritePost (post_id, user_name) values (50,'bing');
