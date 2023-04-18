create table if not exists Authors
(
    A_id int not null
        primary key ,
    Author_name varchar not null
        constraint Author_uq
        unique ,
    Author_Registration_Time timestamp not null unique,
    Author_id char(18) not null unique
        check ( length(Author_id) =18),
    Author_Phone_number char(11) not null unique
        check ( length(Author_Phone_number) =11)
);

create table if not exists Posts
(
    P_id int not null
        primary key ,
    A_id int not null ,
        constraint Post_fk foreign key (A_id) references  Authors(A_id),
    Title varchar not null ,
    Content varchar not null ,
    Author_Registration_Time timestamp not null ,
    Posting_Time timestamp not null ,
    Posting_City varchar not null,
    constraint Posts_fk foreign key (Author_Registration_Time) references Authors(Author_Registration_Time),
    constraint Posts_ch check ( Posts.Posting_Time>Posts.Author_Registration_Time )
);

create table if not exists Category
(
    P_id int not null
        constraint Category_fk1
            references Posts,
    P_Category varchar not null ,
    constraint Category_pk
        primary key (P_id,P_Category)
);

create table if not exists Follower
(
     A_id int not null,
     Follower_name varchar not null ,
     constraint Follower_pk primary key (A_id,Follower_name),
     constraint Follower_fk1 foreign key (A_id) references Authors(A_id),
     constraint Follower_fk2 foreign key (Follower_name) references Authors(Author_name)
);

create table if not exists Favorited
(
    P_id int not null,
    Favorited_name varchar not null ,
    constraint Favorited_pk primary key (P_id,Favorited_name),
    constraint Favorited_fk1 foreign key (P_id) references Posts(P_id),
    constraint Favorited_fk2 foreign key (Favorited_name) references Authors(Author_name)
);

create table if not exists Shared
(
    P_id int not null,
    Shared_name varchar not null ,
    constraint Shared_pk primary key (P_id,Shared_name),
    constraint Shared_fk1 foreign key (P_id) references Posts(P_id),
    constraint Shared_fk2 foreign key (Shared_name) references Authors(Author_name)
);

create table if not exists Liked
(
    P_id int not null,
    Liked_name varchar not null ,
    constraint Liked_pk primary key (P_id, Liked_name),
    constraint Liked_fk1 foreign key (P_id) references Posts(P_id),
    constraint Liked_fk2 foreign key (Liked_name) references Authors(Author_name)
);

create table if not exists Replies
(
    P_id int not null ,
    R_id1 int not null ,
    Reply_Content varchar not null ,
    Reply_Stars int not null
        check ( Reply_Stars>=0 ),
    Reply_Author varchar not null,
    constraint Replies_pk primary key (P_id,R_id1),
    constraint Replies_fk1 foreign key (P_id) references Posts(P_id),
    constraint Replies_fk2 foreign key (Reply_Author) references Authors(Author_name)

);

create table if not exists Secondary_Replies
(
    P_id int not null ,
    R_id1 int not null ,
    R_id2 int not null ,
    Secondary_Reply_Content varchar not null ,
    Secondary_Reply_Stars int not null
        check ( Secondary_Reply_Stars>=0 ),
    Secondary_Reply_Author varchar not null ,
    constraint Secondary_Replies_pk primary key (P_id,R_id1,R_id2),
    constraint Secondary_Replies_fk1 foreign key (P_id,R_id1) references Replies(P_id,R_id1),
    constraint Secondary_Replies_fk2 foreign key (Secondary_Reply_Author) references Authors(Author_name)
);




