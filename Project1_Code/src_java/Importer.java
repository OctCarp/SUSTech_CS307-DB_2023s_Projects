import java.sql.*;
import java.util.*;

public class Importer {
    Connection conn;
    PreparedStatement pAuthor;
    PreparedStatement pPost;
    PreparedStatement pCity;
    PreparedStatement pCate;
    PreparedStatement pPostCate;
    PreparedStatement pFollower;
    PreparedStatement pFavorited;
    PreparedStatement pLiked;
    PreparedStatement pShare;
    PreparedStatement pReply;
    PreparedStatement pSecReply;
    List<Post> posts;
    List<Reply> replies;

    List<String> authors = new ArrayList<>();
    List<String> phoneNumbers = new ArrayList<>();
    List<String> cities = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    List<String> authorIDs = new ArrayList<>();

    public void batchInsert() {
        try {
            conn = MyUt.getConnection();
            MyUt.initDatabase(conn);

            prepare();
            posts = Info.posts;
            replies = Info.replies;

            conn.createStatement().execute(Info.disTrigger);

            conn.setAutoCommit(false);
            postIns();
            postMisc();
            reply();

            pAuthor.executeBatch();
            pCity.executeBatch();
            pCate.executeBatch();
            pPost.executeBatch();
            pPostCate.executeBatch();
            pFavorited.executeBatch();
            pFollower.executeBatch();
            pShare.executeBatch();
            pLiked.executeBatch();
            pReply.executeBatch();
            pSecReply.executeBatch();

            conn.commit();

            MyUt.closeConnect(conn);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void prepare() {
        try {
            if (conn != null) {
                pAuthor = conn.prepareStatement(Info.iAuthor);
                pPost = conn.prepareStatement(Info.iPost);
                pCity = conn.prepareStatement(Info.iCity);
                pCate = conn.prepareStatement(Info.iCate);
                pPostCate = conn.prepareStatement(Info.iPostCate);
                pFollower = conn.prepareStatement(Info.iFollower);
                pFavorited = conn.prepareStatement(Info.iFavorited);
                pLiked = conn.prepareStatement(Info.iLiked);
                pShare = conn.prepareStatement(Info.iShare);
                pReply = conn.prepareStatement(Info.iReply);
                pSecReply = conn.prepareStatement(Info.iSecReply);
            }
        } catch (SQLException sqlE) {
            System.err.println(sqlE);
        }
    }

    private void postIns() throws SQLException {
        for (Post p : posts) {
            int p_id = p.getPostID();
            String title = p.getTitle();
            String content = p.getContent();
            String postTime = p.getPostingTime();
            String author = p.getAuthor();
            String authorID = p.getAuthorID();
            String phone = p.getAuthorPhone();
            String[] postingCityCountry = p.getPostingCity().split(", ");

            int a_id = authors.indexOf(author);
            if (a_id == -1) {
                a_id = authors.size();

                authors.add(author);
                authorIDs.add(authorID);
                phoneNumbers.add(phone);

                Ins.author_p(pAuthor, a_id, author, Timestamp.valueOf(p.getAuthorRegistrationTime()), authorID, phone);
            }
            p.setAID(a_id);

            if (!cities.contains(postingCityCountry[0])) {
                cities.add(postingCityCountry[0]);

                Ins.city_p(pCity, postingCityCountry);
            }

            Ins.post_p(pPost, p_id, a_id, title, content, Timestamp.valueOf(postTime), postingCityCountry[0]);

            for (String cate : p.getCategory()) {
                int c_id = categories.indexOf(cate);
                if (c_id == -1) {
                    c_id = categories.size();
                    categories.add(cate);

                    Ins.cate_p(pCate, c_id, cate);
                }

                Ins.post_cate_p(pPostCate, p_id, c_id);
            }
        }
    }

    private int ambAuthor(String name) throws SQLException {
        int id = authors.indexOf(name);
        if (id == -1) {
            id = authors.size();
            authors.add(name);

            Ins.author_p(pAuthor, id, name, MyUt.randomDate(), MyUt.randStrByLenAdd(authorIDs, 18), MyUt.randStrByLenAdd(phoneNumbers, 11));
        }

        return id;
    }

    private void postMisc() throws SQLException {
        for (Post p : posts) {
            int p_id = p.getPostID();
            int a_id = p.getAID();

            for (String follower : p.getAuthorFollowedBy()) {
                int f_id = ambAuthor(follower);

                Ins.follow_p(pFollower, a_id, f_id);
            }
            for (String sharer : p.getAuthorShared()) {
                int s_id = ambAuthor(sharer);

                Ins.post_act_author_p(pShare, p_id, s_id);
            }
            for (String liker : p.getAuthorLiked()) {
                int la_id = ambAuthor(liker);

                Ins.post_act_author_p(pLiked, p_id, la_id);
            }
            for (String favor : p.getAuthorFavorited()) {
                int fa_id = ambAuthor(favor);

                Ins.post_act_author_p(pFavorited, p_id, fa_id);
            }
        }
    }

    private void reply() throws SQLException {
        int p_id = 0, r1_i = 0, r2_i = 0;

        String r1_c = "init", r1a_name = "";
        for (Reply reply : replies) {
            if (p_id != reply.getPostID()) {
                p_id = reply.getPostID();
                r1_c = reply.getReplyContent();
                r1a_name = reply.getReplyAuthor();

                int r1a_id = ambAuthor(r1a_name);
                int r2a_id = ambAuthor(reply.getSecondaryReplyAuthor());

                Ins.reply_p(pReply, r1_i, p_id, r1_c, reply.getReplyStars(), r1a_id);
                Ins.reply_p(pSecReply, r2_i++, r1_i, reply.getSecondaryReplyContent(), reply.getSecondaryReplyStars(), r2a_id);
                ++r1_i;
            } else {
                if (r1_c.equals(reply.getReplyContent()) && r1a_name.equals(reply.getReplyAuthor())) {
                    int r2a_id = ambAuthor(reply.getSecondaryReplyAuthor());
                    Ins.reply_p(pSecReply, r2_i++, r1_i, r1_c, reply.getSecondaryReplyStars(), r2a_id);

                } else {
                    r1_c = reply.getReplyContent();
                    r1a_name = reply.getReplyAuthor();

                    int r1a_id = ambAuthor(r1a_name);
                    int r2a_id = ambAuthor(reply.getSecondaryReplyAuthor());

                    Ins.reply_p(pReply, r1_i, p_id, r1_c, reply.getReplyStars(), r1a_id);
                    Ins.reply_p(pSecReply, r2_i++, r1_i, reply.getSecondaryReplyContent(), reply.getSecondaryReplyStars(), r2a_id);
                    ++r1_i;
                }
            }
        }
    }
}
