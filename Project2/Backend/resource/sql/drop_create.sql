DROP TABLE IF EXISTS authors, p_cate, favorited, followed, blocked,
    liked, posts, post_views, replies, sub_replies, shared;

CREATE TABLE IF NOT EXISTS authors
(
    a_id       integer PRIMARY KEY,
    a_name     varchar NOT NULL,
    identity   varchar NOT NULL UNIQUE,
    passwd     varchar NOT NULL,

    a_phone    varchar,
    a_reg_time timestamp
);

CREATE TABLE IF NOT EXISTS posts
(
    p_id    integer PRIMARY KEY,
    a_id    integer REFERENCES authors (a_id),
    title   varchar   NOT NULL,
    content varchar   NOT NULL,
    p_time  timestamp NOT NULL,
    p_city  varchar   NOT NULL
);

CREATE TABLE IF NOT EXISTS post_views
(
    p_id integer REFERENCES posts (p_id),
    view_count integer DEFAULT 0,
    PRIMARY KEY (p_id)
);

CREATE TABLE IF NOT EXISTS p_cate
(
    p_id integer NOT NULL REFERENCES posts (p_id),
    cate varchar NOT NULL,

    CONSTRAINT Post_Category_pk PRIMARY KEY (p_id, cate)
);

CREATE TABLE IF NOT EXISTS followed
(
    a_id        integer REFERENCES authors (a_id),
    followed_id integer REFERENCES authors (a_id),

    CONSTRAINT Followed_pk PRIMARY KEY (a_id, followed_id)
);

CREATE TABLE IF NOT EXISTS blocked
(
    a_id        integer REFERENCES authors (a_id),
    blocked_id    integer REFERENCES authors (a_id),

    CONSTRAINT Blocked_pk PRIMARY KEY (a_id, blocked_id)
);

CREATE TABLE IF NOT EXISTS favorited
(
    p_id         integer REFERENCES posts (p_id),
    favorited_id integer REFERENCES authors (a_id),

    CONSTRAINT Favorited_pk PRIMARY KEY (p_id, favorited_id)
);

CREATE TABLE IF NOT EXISTS shared
(
    p_id      integer REFERENCES posts (p_id),
    shared_id integer REFERENCES authors (a_id),

    CONSTRAINT Shared_pk PRIMARY KEY (p_id, shared_id)
);

CREATE TABLE IF NOT EXISTS liked
(
    p_id     integer REFERENCES posts (p_id),
    liked_id integer REFERENCES authors (a_id),

    CONSTRAINT liked_pk PRIMARY KEY (p_id, liked_id)
);

CREATE TABLE IF NOT EXISTS replies
(
    r_id1     integer                         NOT NULL PRIMARY KEY,

    p_id      integer REFERENCES posts (p_id) NOT NULL,
    r_content varchar                         NOT NULL,
    r_stars   integer                         NOT NULL
        check ( r_stars >= 0 ),
    r_a_id    integer REFERENCES authors (a_id)
);

CREATE TABLE IF NOT EXISTS sub_replies
(
    r_id2      integer NOT NULL PRIMARY KEY,

    r_id1      integer REFERENCES replies (r_id1),
    r2_content varchar NOT NULL,
    r2_stars   integer NOT NULL
        check ( r2_stars >= 0 ),
    r2_a_id    integer REFERENCES authors (a_id)
);

CREATE OR REPLACE FUNCTION update_view_count_byreply()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE post_views
    SET view_count = view_count + 3
    WHERE p_id = NEW.p_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_view_count1
    AFTER INSERT ON replies
    FOR EACH ROW
EXECUTE PROCEDURE update_view_count_byreply();

CREATE OR REPLACE FUNCTION update_view_count_bysubreply()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE post_views
    SET view_count = view_count + 2
    WHERE p_id = (
        SELECT p_id
        FROM replies
        WHERE r_id1 = NEW.r_id1
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_view_count2
    AFTER INSERT ON sub_replies
    FOR EACH ROW
EXECUTE PROCEDURE update_view_count_bysubreply();

CREATE OR REPLACE FUNCTION update_view_count_fsl()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE post_views
    SET view_count = view_count + 2
    WHERE p_id = NEW.p_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_view_count3
    AFTER INSERT ON favorited
    FOR EACH ROW
EXECUTE PROCEDURE update_view_count_fsl();

CREATE TRIGGER increment_view_count4
    AFTER INSERT ON shared
    FOR EACH ROW
EXECUTE PROCEDURE update_view_count_fsl();

CREATE TRIGGER increment_view_count5
    AFTER INSERT ON liked
    FOR EACH ROW
EXECUTE PROCEDURE update_view_count_fsl();

CREATE OR REPLACE FUNCTION delete_view_count_fsl()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE post_views
    SET view_count = view_count - 2
    WHERE p_id = OLD.p_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER decr_view_count_favorite
    AFTER DELETE ON favorited
    FOR EACH ROW
EXECUTE PROCEDURE  delete_view_count_fsl();

CREATE TRIGGER decr_view_count_shared
    AFTER DELETE ON shared
    FOR EACH ROW
EXECUTE PROCEDURE delete_view_count_fsl();

CREATE TRIGGER decr_view_count_liked
    AFTER DELETE ON liked
    FOR EACH ROW
EXECUTE PROCEDURE delete_view_count_fsl();
