DROP USER IF EXISTS viewer, normal, opter, sender, authors;

CREATE USER viewer  WITH PASSWORD 'viewer_pass';
CREATE USER normal WITH PASSWORD 'normal_pass';
CREATE USER opter WITH PASSWORD 'opter_pass';
CREATE USER sender WITH PASSWORD 'sender_pass';
CREATE USER authors WITH PASSWORD 'authors_pass';

GRANT SELECT, UPDATE, INSERT, DELETE, TRIGGER ON TABLE post_views TO viewer, normal, opter, sender, authors;
GRANT EXECUTE ON FUNCTION delete_view_count_fsl(), update_view_count_fsl() TO opter;
GRANT EXECUTE ON FUNCTION update_view_count_byreply(),update_view_count_bysubreply() TO sender;

GRANT SELECT ON TABLE authors,posts,p_cate,posts, replies, sub_replies, blocked TO viewer;

GRANT SELECT ON TABLE authors,posts,replies, sub_replies,
    shared, favorited, followed, blocked, liked TO normal;

GRANT SELECT, DELETE, INSERT ON TABLE shared, favorited, followed, blocked, liked TO opter;

GRANT SELECT, INSERT ON TABLE posts,p_cate,replies,sub_replies,post_views TO sender;

GRANT SELECT, INSERT ON TABLE authors TO authors;