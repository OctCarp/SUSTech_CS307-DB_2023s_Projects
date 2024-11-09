package io.github.octcarp.sustech.cs307.loader;

import io.github.octcarp.sustech.cs307.loader.models.Post;
import io.github.octcarp.sustech.cs307.loader.models.Reply;
import io.github.octcarp.sustech.cs307.myutils.Info;
import io.github.octcarp.sustech.cs307.myutils.Ins;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Importer {

    public static void main(String[] args) {
        Info.infoInit();

        new Importer().batchInsert();
//
    }

    Connection conn;
    PreparedStatement pAuthor;
    PreparedStatement pPost;
    PreparedStatement pPostview;
    PreparedStatement pPostCate;
    PreparedStatement pFollowed;
    PreparedStatement pFavorited;
    PreparedStatement pLiked;
    PreparedStatement pShare;
    PreparedStatement pReply;
    PreparedStatement pSecReply;
    List<Post> posts;
    List<Reply> replies;

    List<String> authors = new ArrayList<>();
    List<String> phoneNumbers = new ArrayList<>();
    List<String> authorIDs = new ArrayList<>();

    public long batchInsert() {
        long dur = -1;
        try {
            long startTime = System.currentTimeMillis();

            conn = getConnection();
            init();

            posts = Info.posts;
            replies = Info.replies;

            //conn.createStatement().execute(Info.disTrigger);

            conn.setAutoCommit(false);
            postIns();
            postMisc();
            reply();

            pAuthor.executeBatch();
            pPost.executeBatch();
            pPostview.executeBatch();
            pPostCate.executeBatch();
            pFavorited.executeBatch();
            pFollowed.executeBatch();
            pShare.executeBatch();
            pLiked.executeBatch();
            pReply.executeBatch();
            pSecReply.executeBatch();

            //conn.createStatement().execute(Info.enTrigger);
            conn.commit();

            closeConnect(conn);

            dur = System.currentTimeMillis() - startTime;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return dur;
    }


    private void postIns() {
        for (Post p : posts) {
            int p_id = p.getPostID();
            String title = p.getTitle();
            String content = p.getContent();
            String postTime = p.getPostingTime();
            String a_name = p.getAuthor();
            String identity = p.getAuthorID();
            String phone = p.getAuthorPhone();
            String postingCityCountry = p.getPostingCity();

            int a_id = authors.indexOf(a_name);
            if (a_id == -1) {
                a_id = authors.size();

                authors.add(a_name);
                authorIDs.add(identity);
                phoneNumbers.add(phone);

                Ins.author_bat(pAuthor, a_id, a_name, String.valueOf(a_id), "1", phone, Timestamp.valueOf(p.getAuthorRegistrationTime()));
            }
            p.setAID(a_id);

            Ins.post_bat(pPost, p_id, a_id, title, content, Timestamp.valueOf(postTime), postingCityCountry);
            Ins.post_view_bat(pPostview, p_id, 0);

            for (String cate : p.getCategory()) {
                Ins.post_cate_bat(pPostCate, p_id, cate);
            }
        }
    }

    private int ambAuthor(String name) {
        int id = authors.indexOf(name);
        if (id == -1) {
            id = authors.size();
            authors.add(name);

            Ins.author_bat(pAuthor, id, name, String.valueOf(id), "1", null, null);
        }

        return id;
    }

    private void postMisc() {
        for (Post p : posts) {
            int p_id = p.getPostID();
            int a_id = p.getAID();

            for (String follower : p.getAuthorFollowedBy()) {
                int f_id = ambAuthor(follower);

                Ins.follow_bat(pFollowed, a_id, f_id);
            }
            for (String sharer : p.getAuthorShared()) {
                int s_id = ambAuthor(sharer);

                Ins.post_act_author_bat(pShare, p_id, s_id);
            }
            for (String liker : p.getAuthorLiked()) {
                int la_id = ambAuthor(liker);

                Ins.post_act_author_bat(pLiked, p_id, la_id);
            }
            for (String favor : p.getAuthorFavorited()) {
                int fa_id = ambAuthor(favor);

                Ins.post_act_author_bat(pFavorited, p_id, fa_id);
            }
        }
    }

    private void reply() {
        int p_id = 0, r1_i = -1, r2_i = -1;

        String r1_c = "init", r1a_name = "";
        for (Reply reply : replies) {
            if ((p_id == reply.getPostID() && r1_c.equals(reply.getReplyContent()) && r1a_name.equals(reply.getReplyAuthor()))) {
                int r2a_id = ambAuthor(reply.getSecondaryReplyAuthor());

                Ins.reply_bat(pSecReply, ++r2_i, r1_i, r1_c, reply.getSecondaryReplyStars(), r2a_id);
            } else {
                ++r1_i;
                p_id = reply.getPostID();
                r1_c = reply.getReplyContent();
                r1a_name = reply.getReplyAuthor();

                int r1a_id = ambAuthor(r1a_name);
                int r2a_id = ambAuthor(reply.getSecondaryReplyAuthor());

                Ins.reply_bat(pReply, r1_i, p_id, r1_c, reply.getReplyStars(), r1a_id);
                Ins.reply_bat(pSecReply, ++r2_i, r1_i, reply.getSecondaryReplyContent(), reply.getSecondaryReplyStars(), r2a_id);
            }
        }
    }

    private static Random r = new Random();

    private static String randomByLength(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    private void init() {
        try {
            Statement Statement = conn.createStatement();
            conn.setAutoCommit(false);
//            Statement.executeUpdate(Info.dropCreate);
//            Statement.executeUpdate(Info.gainUsers);
            Statement.executeUpdate("INSERT INTO authors (a_id, a_name, identity, passwd) " +
                    "values (-2, 'anonymous', -2, 1);");
            conn.commit();
            conn.setAutoCommit(true);

            prepare(conn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver.");
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(Info.url, Info.user, Info.pwd);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return conn;
    }
    public static Connection getOgConnection() {
        Connection conn = null;

        try {
            Class.forName("org.opengauss.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver.");
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection("jdbc:opengauss://192.168.161.18:5432/CS307_Project2", "checker", "gauss@123");
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return conn;
    }



    private void prepare(Connection conn) {
        try {
            if (conn != null) {
                pAuthor = conn.prepareStatement(
                        "INSERT INTO authors (a_id, a_name, identity, passwd, a_phone, a_reg_time) " +
                                "VALUES (?, ?, ?, ?, ?, ?)");
                pPost = conn.prepareStatement(
                        "INSERT INTO posts (p_id, a_id, title, content, p_time, p_city)" +
                                "VALUES (?, ?, ?, ?, ?, ?)"
                );
                pPostview = conn.prepareStatement(
                        "INSERT INTO post_views (p_id, view_count)" +
                                "VALUES (?, ?)"
                );
                pPostCate = conn.prepareStatement(
                        "INSERT INTO p_cate (p_id, cate) VALUES (?, ?)");
                pFollowed = conn.prepareStatement(
                        "INSERT INTO followed (a_id, followed_id) VALUES (?, ?)"
                );
                pFavorited = conn.prepareStatement(
                        "INSERT INTO favorited (p_id, favorited_id) VALUES (?, ?)"
                );
                pLiked = conn.prepareStatement(
                        "INSERT INTO liked (p_id, liked_id) VALUES (?, ?)");
                pShare = conn.prepareStatement(
                        "INSERT INTO shared (p_id, shared_id) VALUES (?, ?)");
                pReply = conn.prepareStatement(
                        "INSERT INTO replies (r_id1, p_id, r_content, r_stars, r_a_id) " +
                                "VALUES (?, ?, ?, ?, ?)");
                pSecReply = conn.prepareStatement(
                        "INSERT INTO sub_replies (r_id2, r_id1, r2_content, r2_stars, r2_a_id) " +
                                "VALUES (?, ?, ?, ?, ?)");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public static String randStrByLenAdd(List<String> list, int length) {
        String randString = randomByLength(length);
        while (list.contains(randString)) {
            randString = randomByLength(11);
        }
        list.add(randString);
        return randString;
    }

    public static void closeConnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ignored) {
            }
        }
    }
}
