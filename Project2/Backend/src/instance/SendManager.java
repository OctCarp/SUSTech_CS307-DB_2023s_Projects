package instance;

import java.sql.*;

public class SendManager {
    PreparedStatement postI;
    PreparedStatement postCateI;
    PreparedStatement r1I;
    PreparedStatement r2I;
    PreparedStatement post_viewI;


    public boolean sendPost(int a_id, String title, String content, String city, String[] cates) {
        try {
            int p_id = CountId.getAddP();
            postI.setInt(1, p_id);
            postI.setInt(2, a_id);
            postI.setString(3, title);
            postI.setString(4, content);
            postI.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            postI.setString(6, city);
            int res = postI.executeUpdate();
            post_viewI.setInt(1, p_id);
            post_viewI.setInt(2, 0);
            post_viewI.executeUpdate();
            if (cates != null) {
                for (String cate : cates) {
                    postCateI.setInt(1, p_id);
                    postCateI.setString(2, cate);
                    postCateI.executeUpdate();
                }
            }

            return res > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendReply(int p_id, int a_id, String content) {
        try {
            int r1_id = CountId.getAddR1();
            r1I.setInt(1, r1_id);
            r1I.setInt(2, p_id);
            r1I.setString(3, content);
            r1I.setInt(4, 0);
            r1I.setInt(5, a_id);
            return r1I.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendSubReply(int r1_id, int a_id, String content) {
        try {
            int r2_id = CountId.getAddR2();
            r2I.setInt(1, r2_id);
            r2I.setInt(2, r1_id);
            r2I.setString(3, content);
            r2I.setInt(4, 0);
            r2I.setInt(5, a_id);
            return r2I.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SendManager() {
        try {
            Connection conn = getConn();
            postI = conn.prepareStatement(
                    "INSERT INTO posts (p_id, a_id, title, content, p_time, p_city) VALUES (?, ?, ?, ?, ?, ?)"
            );
            postCateI = conn.prepareStatement(
                    "INSERT INTO p_cate (p_id, cate) VALUES (?, ?)"
            );
            r1I = conn.prepareStatement(
                    "INSERT INTO replies (r_id1, p_id, r_content, r_stars, r_a_id) VALUES (?, ?, ?, ?, ?)"
            );
            r2I = conn.prepareStatement(
                    "INSERT INTO sub_replies (r_id2, r_id1, r2_content, r2_stars, r2_a_id) VALUES (?, ?, ?, ?, ?)"
            );
            post_viewI = conn.prepareStatement(
                    "INSERT INTO post_views (p_id, view_count) VALUES (?, ?)"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConn() {
        return ConnectionManager.getSenderConn();
    }
}
