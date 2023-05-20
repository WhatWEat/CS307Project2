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
