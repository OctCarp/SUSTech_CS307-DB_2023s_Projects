CREATE TABLE IF NOT EXISTS authors
(
    a_id integer PRIMARY KEY ,
    author_name varchar NOT NULL UNIQUE ,
    author_registration_time timestamp NOT NULL UNIQUE,
    author_id char(18) NOT NULL UNIQUE
        check ( length(author_id) = 18),
    author_phone_number char(11) NOT NULL UNIQUE
        check ( length(author_phone_number) = 11)
);

CREATE TABLE IF NOT EXISTS cities
(
    city_name varchar NOT NULL
        PRIMARY KEY,
    city_country varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS posts
(
    p_id integer NOT NULL
        PRIMARY KEY ,
    a_id integer REFERENCES  authors(a_id),
    title varchar NOT NULL ,
    content varchar NOT NULL ,
    author_registration_time timestamp REFERENCES authors(author_registration_time) ,
    posting_time timestamp NOT NULL ,
    posting_city varchar NOT NULL
        REFERENCES cities(city_name),

    CONSTRAINT Posts_ch check ( posts.posting_time > posts.author_registration_time )
);

CREATE TABLE IF NOT EXISTS category
(
    c_id integer not null PRIMARY KEY ,
    category_name varchar NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS post_category
(
    p_id integer NOT NULL REFERENCES posts(p_id),
    c_id integer NOT NULL REFERENCES category(c_id),

    CONSTRAINT Post_Category_pk PRIMARY KEY (p_id,c_id)
);

CREATE TABLE IF NOT EXISTS follower
(
     a_id integer REFERENCES authors(a_id),
     follower_id integer REFERENCES authors(a_id),

    CONSTRAINT Follower_pk PRIMARY KEY (a_id,follower_id)
);

CREATE TABLE IF NOT EXISTS favorited
(
    p_id integer REFERENCES posts(p_id),
    favorited_id integer REFERENCES authors(a_id),

    CONSTRAINT Favorited_pk PRIMARY KEY (p_id,favorited_id)
);

CREATE TABLE IF NOT EXISTS shared
(
    p_id integer REFERENCES posts(p_id),
    shared_id integer REFERENCES authors(a_id),

    CONSTRAINT Shared_pk PRIMARY KEY (p_id,shared_id)
);

CREATE TABLE IF NOT EXISTS liked
(
    p_id integer REFERENCES posts(p_id),
    liked_id integer REFERENCES authors(a_id),

    CONSTRAINT liked_pk PRIMARY KEY (p_id,liked_id)
);

CREATE TABLE IF NOT EXISTS replies
(
    r_id1 integer NOT NULL PRIMARY KEY ,

    p_id integer REFERENCES posts(p_id) NOT NULL ,
    reply_content varchar NOT NULL ,
    reply_stars integer NOT NULL
        check ( reply_stars>=0 ),
    reply_author_id integer REFERENCES authors(a_id)
);

CREATE TABLE IF NOT EXISTS secondary_replies
(
    r_id2 integer NOT NULL PRIMARY KEY ,

    r_id1 integer REFERENCES replies(r_id1) ,
    secondary_reply_content varchar NOT NULL ,
    secondary_reply_stars integer NOT NULL
        check ( secondary_reply_stars>=0 ),
    secondary_reply_author_id integer REFERENCES authors(a_id)
);
