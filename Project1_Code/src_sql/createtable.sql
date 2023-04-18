CREATE TABLE IF NOT EXISTS Authors
(
    A_id SERIAL PRIMARY KEY ,
    Author_name varchar NOT NULL UNIQUE ,
    Author_Registration_Time timestamp NOT NULL UNIQUE,
    Author_id char(18) NOT NULL UNIQUE
        check ( length(Author_id) =18),
    Author_Phone_number char(11) NOT NULL UNIQUE
        check ( length(Author_Phone_number) =11)
);

CREATE TABLE IF NOT EXISTS Cities
(
    City_Name varchar NOT NULL
        PRIMARY KEY,
    City_Country varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS Posts
(
    P_id integer NOT NULL
        PRIMARY KEY ,
    A_id integer REFERENCES  Authors(A_id),
    Title varchar NOT NULL ,
    Content varchar NOT NULL ,
    Author_Registration_Time timestamp REFERENCES Authors(Author_Registration_Time) ,
    Posting_Time timestamp NOT NULL ,
    Posting_City varchar NOT NULL
        REFERENCES Cities(City_Name),

    CONSTRAINT Posts_ch check ( Posts.Posting_Time>Posts.Author_Registration_Time )
);

CREATE TABLE IF NOT EXISTS Category
(
    C_id integer not null PRIMARY KEY ,
    Category_Name varchar NOT NULL unique
);

CREATE TABLE IF NOT EXISTS Post_Category
(
    P_id integer NOT NULL ,
    C_id integer NOT NULL ,

    CONSTRAINT Post_Category_pk PRIMARY KEY (P_id,C_id)
);

CREATE TABLE IF NOT EXISTS Follower
(
     A_id integer REFERENCES Authors(A_id),
     Follower_id integer REFERENCES Authors(A_id),

    CONSTRAINT Follower_pk PRIMARY KEY (A_id,Follower_id)
);

CREATE TABLE IF NOT EXISTS Favorited
(
    P_id integer REFERENCES Posts(P_id),
    Favorited_id integer REFERENCES Authors(A_id),

    CONSTRAINT Favorited_pk PRIMARY KEY (P_id,Favorited_id)
);

CREATE TABLE IF NOT EXISTS Shared
(
    P_id integer REFERENCES Posts(P_id),
    Shared_id integer REFERENCES Authors(A_id),

    CONSTRAINT Shared_pk PRIMARY KEY (P_id,Shared_id)
);

CREATE TABLE IF NOT EXISTS Liked
(
    P_id integer REFERENCES Posts(P_id),
    Liked_id integer REFERENCES Authors(A_id),

    CONSTRAINT liked_pk PRIMARY KEY (P_id,Liked_id)
);

CREATE TABLE IF NOT EXISTS Replies
(
    R_id1 integer NOT NULL PRIMARY KEY ,
    P_id integer REFERENCES Posts(P_id) NOT NULL ,
    Reply_Content varchar NOT NULL ,
    Reply_Stars integer NOT NULL
        check ( Reply_Stars>=0 ),
    Reply_Author varchar REFERENCES Authors(Author_name)
);

CREATE TABLE IF NOT EXISTS Secondary_Replies
(
    R_id2 integer NOT NULL PRIMARY KEY ,
    R_id1 integer NOT NULL ,
    Secondary_Reply_Content varchar NOT NULL ,
    Secondary_Reply_Stars integer NOT NULL
        check ( Secondary_Reply_Stars>=0 ),
    Secondary_Reply_Author varchar REFERENCES Authors(Author_name),

    CONSTRAINT Secondary_Replies_fk1 FOREIGN KEY (R_id1) REFERENCES Replies(R_id1)
);




