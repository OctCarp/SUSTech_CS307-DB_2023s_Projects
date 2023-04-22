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

with l1 as (select count(*),Follower_id from follower group by Follower_id),
 l2 as (select max(l.count) as value from (select count(*)from follower group by Follower_id) l)
select Author_name,l2.value from l1,l2,Authors where l1.count=l2.value and l1.Follower_id = Authors.A_id order by Follower_id;

with l1 as (select count(*),A_id from follower group by A_id),
 l2 as (select max(l.count) as value from (select count(*)from follower group by A_id) l)
select Author_name,l2.value from l1,l2,Authors where l1.count=l2.value and l1.A_id = Authors.A_id order by l1.A_id;

with p as (select min(Posting_Time) from Posts)
select p_id from Posts,p where Posts.Posting_Time=p.min;

with p as (select max(Posting_Time) from Posts)
select p_id from Posts,p where Posts.Posting_Time=p.max;

select count(*) from Posts where Posting_Time>='2020-1-1' and Posting_Time<'2021-1-1';

select count(*) from Posts where Posting_Time>='2021-1-1' and Posting_Time<'2022-1-1';

select count(*) from Posts where Posting_Time>='2022-1-1' and Posting_Time<'2023-1-1';
