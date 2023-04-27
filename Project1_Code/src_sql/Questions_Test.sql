/*
For Original information, please check:
./Project1_Docs/Project1_Requirements/Questions.pdf
*/

/*
easy:
*/
-- How many posts are there in total?
SELECT count(*)
FROM posts;
-- Answer: 203


-- How many likes in post with Post ID {key}?
-- How many likes in post with Post ID 163?
SELECT count(*)
FROM liked ls
WHERE ls.p_id = 163;
-- Answer: 45

-- How many likes in post with Post ID 28?
SELECT count(*)
FROM liked ls
WHERE ls.p_id = 28;
-- Answer: 2

-- How many likes in post with Post ID 6?
SELECT count(*)
FROM liked ls
WHERE ls.p_id = 6;
-- Answer: 24


-- How many favorites in post with Post ID {key}?
-- How many favorites in post with Post ID 163?
SELECT count(*)
FROM favorited fs
WHERE fs.p_id = 163;
-- Answer: 41

-- How many favorites in post with Post ID 28?
SELECT count(*)
FROM favorited fs
WHERE fs.p_id = 28;
-- Answer: 13

-- How many favorites in post with Post ID 6?
SELECT count(*)
FROM favorited fs
WHERE fs.p_id = 6;
-- Answer: 3


-- How many shares in post with Post ID {key}?
-- How many shares in post with Post ID 163?
SELECT count(*)
FROM shared ss
WHERE ss.p_id = 163;
-- Answer: 19

-- How many shares in post with Post ID 28?
SELECT count(*)
FROM shared ss
WHERE ss.p_id = 28;
-- Answer: 10

-- How many shares in post with Post ID 6?
SELECT count(*)
FROM shared ss
WHERE ss.p_id = 6;
-- Answer: 25


-- List the information of {name}.
-- List the information of novel_expert.
SELECT a.author_id, a.author_phone_number, a.author_registration_time
FROM authors a
WHERE a.author_name = 'novel_expert';
-- Answer: ('77299519840727839X', '18889727122', '2010-06-04 11:07:08')

-- List the information of creative_hat.
SELECT a.author_id, a.author_phone_number, a.author_registration_time
FROM authors a
WHERE a.author_name = 'creative_hat';
-- Answer: ('102053196407095853', '18288072303', '2021-11-02 15:10:15')

-- List the information of  embarrassed_guitar.
SELECT a.author_id, a.author_phone_number, a.author_registration_time
FROM authors a
WHERE a.author_name = 'embarrassed_guitar';
-- Answer: ('154877196111232324', '13322606476', '2010-08-24 15:03:48')
/*
medium:
*/
-- What is the ID of the post with the highest number of likes?
WITH ls AS (SELECT l.p_id, count(*) AS likes
            FROM liked l
            GROUP BY l.p_id)
SELECT *
FROM ls
WHERE ls.likes = (SELECT max(ls.likes) FROM ls)
ORDER BY ls.p_id;
-- Answer: [85, 120], with value: 100


-- What is the ID of the post with the highest number of favorites??
WITH fs AS (SELECT f.p_id, count(*) AS favor
            FROM favorited f
            GROUP BY f.p_id)
SELECT *
FROM fs
WHERE fs.favor = (SELECT max(fs.favor) FROM fs)
ORDER BY fs.p_id;
-- Answer: [19, 23, 78, 114], with value: 50


-- What is the ID of the post with the highest number of shares?
WITH ss AS (SELECT s.p_id, count(*) AS share
            FROM shared s
            GROUP BY s.p_id)
SELECT *
FROM ss
WHERE ss.share = (SELECT max(ss.share) FROM ss)
ORDER BY ss.p_id;
-- Answer: [46, 50, 52, 65, 72, 191], with value: 30


-- Who is the author who follows the most people?
WITH fws AS (SELECT fw.a_id, count(*) AS followed
             FROM followed fw
             GROUP BY fw.a_id)
SELECT a.author_name, fws.followed
FROM fws
         LEFT JOIN authors a ON fws.a_id = a.a_id
WHERE fws.followed = (SELECT max(fws.followed) FROM fws)
ORDER BY fws.a_id;
-- Answer: ['young_contribution'], with value: 17


-- Who is the author with the highest number of followers?
WITH fws AS (SELECT fw.followed_id, count(*) AS follower
             FROM followed fw
             GROUP BY fw.followed_id)
SELECT a.author_name, fws.follower
FROM fws
         LEFT JOIN authors a ON fws.followed_id = a.a_id
WHERE fws.follower = (SELECT max(fws.follower) FROM fws)
ORDER BY fws.followed_id;
-- Answer: ['realistic_negative'], with value: 4


-- What is the Post ID of the earliest post?
SELECT p.p_id
FROM posts p
WHERE p.posting_time = (SELECT min(posting_time) FROM posts);
-- Answer: 129


-- What is the Post ID of the latest post?
SELECT p.p_id
FROM posts p
WHERE p.posting_time = (SELECT max(posting_time) FROM posts);
-- Answer: 59


/*
hard:
*/
-- How many posts are there in 2020?
SELECT count(*) AS p_in2020
FROM posts p
WHERE p.posting_time BETWEEN '2020-01-01 00:00:00' AND '2020-12-31 23:59:59';
-- Answer: 18


-- How many posts are there in 2021?
SELECT count(*) AS p_in2021
FROM posts p
WHERE p.posting_time BETWEEN '2021-01-01 00:00:00' AND '2021-12-31 23:59:59';
-- Answer: 29


-- How many posts are there in 2022?
SELECT count(*) AS p_in2022
FROM posts p
WHERE p.posting_time BETWEEN '2022-01-01 00:00:00' AND '2022-12-31 23:59:59';
-- Answer: 27



/*
 by GUO
*/

select count(*) from Posts;
select count(*) from liked where P_id=163;
select count(*) from liked where P_id=28;
select count(*) from liked where P_id=6;
select count(*) from favorited where P_id=163;
select count(*) from favorited where P_id=28;
select count(*) from favorited where P_id=6;
select count(*) from shared where P_id=163;
select count(*) from shared where P_id=28;
select count(*) from shared where P_id=6;
select * from authors where author_name = 'novel_expert';
select * from authors where author_name = 'creative_hat';
select * from authors where author_name = 'embarrassed_guitar';

with l1 as (select count(*),P_id from liked group by P_id),
     l2 as (select max(l.count) as value from (select count(*)from liked group by P_id) l)
select P_id,l2.value from l1,l2 where l1.count=l2.value order by P_id;

with l1 as (select count(*),P_id from favorited group by P_id),
     l2 as (select max(l.count) as value from (select count(*)from favorited group by P_id) l)
select P_id,l2.value from l1,l2 where l1.count=l2.value order by P_id;

with l1 as (select count(*),P_id from shared group by P_id),
     l2 as (select max(l.count) as value from (select count(*)from shared group by P_id) l)
select P_id,l2.value from l1,l2 where l1.count=l2.value order by P_id;

with l1 as (select count(*),Followed_id from followed group by Followed_id),
     l2 as (select max(l.count) as value from (select count(*)from followed group by Followed_id) l)
select Author_name,l2.value from l1,l2,Authors where l1.count=l2.value and l1.Followed_id = Authors.A_id order by followed_id;

with l1 as (select count(*),A_id from followed group by A_id),
     l2 as (select max(l.count) as value from (select count(*)from followed group by A_id) l)
select Author_name,l2.value from l1,l2,Authors where l1.count=l2.value and l1.A_id = Authors.A_id order by l1.A_id;

with p as (select min(Posting_Time) from Posts)
select p_id from Posts,p where Posts.Posting_Time=p.min;

with p as (select max(Posting_Time) from Posts)
select p_id from Posts,p where Posts.Posting_Time=p.max;

select count(*) from Posts where Posting_Time>='2020-1-1' and Posting_Time<'2021-1-1';

select count(*) from Posts where Posting_Time>='2021-1-1' and Posting_Time<'2022-1-1';

select count(*) from Posts where Posting_Time>='2022-1-1' and Posting_Time<'2023-1-1';