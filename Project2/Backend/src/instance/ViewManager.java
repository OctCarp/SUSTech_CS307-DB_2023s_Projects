package instance;

import model.IdCon;
import model.Post;
import model.Reply;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.Integer.max;

public class ViewManager {
    PreparedStatement postByIdQ;
    PreparedStatement replyByIdQ;
    PreparedStatement reply2ByIdQ;
    PreparedStatement getCateQ;
    PreparedStatement AllpostsQ;
    PreparedStatement AllpostsobtQ;
    PreparedStatement postreplies;
    PreparedStatement replyreplies;
    PreparedStatement hotlist;
    PreparedStatement updatepostview;
    PreparedStatement multi_parameter_search;


    public Post getPost(int p_id, int va_id) {
        try {
            ResultSet res;

            postByIdQ.setInt(1, p_id);
            postByIdQ.setInt(2, va_id);
            updatepostview.setInt(1, p_id);
            updatepostview.executeUpdate();
            res = postByIdQ.executeQuery();
            if (res.next()) {
                Post post = new Post();
                post.setP_id(p_id);
                post.setA_id(res.getInt("a_id"));
                post.setTitle(res.getString("title"));
                post.setA_name(res.getString("a_name"));
                post.setContent(res.getString("content"));
                post.setTime(res.getString("p_time"));
                post.setCity(res.getString("p_city"));
                getCateQ.setInt(1, p_id);
                res = getCateQ.executeQuery();

                ArrayList<String> out = new ArrayList<>();
                while (res.next()) {
                    out.add(res.getString("cate"));
                }
                String[] Array = new String[out.size()];
                post.setTags(out.toArray(Array));

                return post;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reply getReply(int r_id1, int va_id) {
        try {
            ResultSet res;

            replyByIdQ.setInt(1, r_id1);
            replyByIdQ.setInt(2, va_id);
            res = replyByIdQ.executeQuery();
            if (res.next()) {
                Reply r = new Reply();
                r.setA_id(res.getInt("r_a_id"));
                r.setRs_id(res.getInt("r_id1"));
                r.setOri_id(res.getInt("p_id"));
                r.setOri_c(res.getString("title"));
                r.setA_name(res.getString("a_name"));
                r.setContent(res.getString("r_content"));
                r.setStars(res.getInt("r_stars"));
                return r;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reply getReply2(int r_id2, int va_id) {
        try {
            ResultSet res;

            reply2ByIdQ.setInt(1, r_id2);
            reply2ByIdQ.setInt(2, va_id);
            res = reply2ByIdQ.executeQuery();
            if (res.next()) {
                Reply r2 = new Reply();
                r2.setA_id(res.getInt("r2_a_id"));
                r2.setRs_id(res.getInt("r_id2"));
                r2.setOri_id(res.getInt("r_id1"));
                r2.setOri_c(res.getString("r_content"));
                r2.setA_name(res.getString("a_name"));
                r2.setContent(res.getString("r2_content"));
                r2.setStars(res.getInt("r2_stars"));
                return r2;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_All_Posts(int page_num, int va_id) {
        try {
            ResultSet res;
            int start = max((page_num - 1) * 20, 0);
            AllpostsQ.setInt(1, va_id);
            AllpostsQ.setInt(2, start);
            res = AllpostsQ.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("p_id"), res.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_All_Posts_obt(int page_num, int va_id) {
        try {
            ResultSet res;
            int start = max((page_num - 1) * 20, 0);
            AllpostsobtQ.setInt(1, va_id);
            AllpostsobtQ.setInt(2, start);
            res = AllpostsobtQ.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("p_id"), res.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_post_replies(int p_id, int va_id) {
        try {
            ResultSet res;
            postreplies.setInt(1, p_id);
            postreplies.setInt(2, va_id);
            res = postreplies.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("r_id1"), res.getString("r_content")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_reply_replies(int r_id1, int va_id) {
        try {
            ResultSet res;
            replyreplies.setInt(1, r_id1);
            replyreplies.setInt(2, va_id);
            res = replyreplies.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("r_id2"), res.getString("r2_content")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_hot_list(int va_id) {
        try {
            ResultSet res;
            hotlist.setInt(1, va_id);
            res = hotlist.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("p_id"), res.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] get_multi_parameter_search(int va_id, String keyword, String category) {
        try {
            ResultSet res;
            multi_parameter_search.setString(1, keyword);
            multi_parameter_search.setString(2, category);
            multi_parameter_search.setInt(3, va_id);
            res = multi_parameter_search.executeQuery();
            ArrayList<IdCon> out = new ArrayList<>();
            while (res.next()) {
                out.add(new IdCon(res.getInt("p_id"), res.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ViewManager() {
        Connection conn = getConn();
        try {
            postByIdQ = conn.prepareStatement(
                    "SELECT p.title, p.content, p.p_time, p.p_city, a.a_name, p.a_id FROM posts p " +
                            "LEFT JOIN authors a on a.a_id = p.a_id WHERE p.p_id = ? " +
                            "AND p.a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
            getCateQ = conn.prepareStatement(
                    "SELECT pc.cate FROM p_cate pc WHERE pc.p_id = ?");
            replyByIdQ = conn.prepareStatement(
                    "SELECT p.p_id, p.title, r.r_a_id, a.a_name, r.r_id1, r.r_content, r.r_stars " +
                            "FROM replies r LEFT JOIN authors a on a.a_id = r.r_a_id " +
                            "LEFT JOIN posts p on r.p_id = p.p_id WHERE r.r_id1 = ? " +
                            "AND r.r_a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
            reply2ByIdQ = conn.prepareStatement(
                    "SELECT r1.r_id1, r1.r_content, r2.r2_a_id, a.a_name, r2.r_id2, r2.r2_content,  r2.r2_stars " +
                            "FROM sub_replies r2 LEFT JOIN authors a on a.a_id = r2.r2_a_id " +
                            "LEFT JOIN replies r1 on r1.r_id1 = r2.r_id1 WHERE r2.r_id2 = ? " +
                            "AND r2.r2_a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
            AllpostsQ = conn.prepareStatement(
                    "SELECT p.title, p.content, p.p_time, p.p_city, a.a_name, p.a_id, p.p_id FROM posts p " +
                            "LEFT JOIN authors a on a.a_id = p.a_id " +
                            "AND p.a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)" +
                            "order by p.p_id LIMIT 20 OFFSET ? ");
            AllpostsobtQ = conn.prepareStatement(
                    "SELECT p.title, p.content, p.p_time, p.p_city, a.a_name, p.a_id, p.p_id FROM posts p " +
                            "LEFT JOIN authors a on a.a_id = p.a_id " +
                            "AND p.a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)" +
                            "order by p.p_time desc LIMIT 20 OFFSET ? ");
            postreplies = conn.prepareStatement(
                    "SELECT p.p_id, p.title, r.r_a_id, a.a_name, r.r_id1, r.r_content, r.r_stars " +
                            "FROM replies r LEFT JOIN authors a on a.a_id = r.r_a_id " +
                            "LEFT JOIN posts p on r.p_id = p.p_id WHERE r.p_id = ? " +
                            "AND r.r_a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
            replyreplies = conn.prepareStatement(
                    "SELECT r1.r_id1, r1.r_content, r2.r2_a_id, a.a_name, r2.r_id2, r2.r2_content,  r2.r2_stars " +
                            "FROM sub_replies r2 LEFT JOIN authors a on a.a_id = r2.r2_a_id " +
                            "LEFT JOIN replies r1 on r1.r_id1 = r2.r_id1 WHERE r2.r_id1 = ? " +
                            "AND r2.r2_a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
            hotlist = conn.prepareStatement(
                    "SELECT p.p_id, p.title, p.p_city, p.p_time, p.content, p.a_id, pv.view_count FROM posts p " +
                            "LEFT JOIN post_views pv on pv.p_id = p.p_id " +
                            "AND p.a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)" +
                            "ORDER BY pv.view_count DESC , p_id LIMIT 15 ");
            updatepostview = conn.prepareStatement(
                    "UPDATE post_views SET view_count = view_count + 1 WHERE p_id = ?");
            multi_parameter_search = conn.prepareStatement(
                    "SELECT p.p_id, p.a_id, p.title, p.content, p.p_time, p.p_city " +
                            "FROM posts p " +
                            "LEFT JOIN p_cate pc ON p.p_id = pc.p_id " +
                            "WHERE p.title LIKE '%' || ? || '%' " +
                            "  AND pc.cate = ? " +
                            "  AND p.a_id NOT IN (SELECT b.blocked_id FROM blocked b WHERE b.a_id = ?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConn() {
        return ConnectionManager.getViewConn();
    }
}
