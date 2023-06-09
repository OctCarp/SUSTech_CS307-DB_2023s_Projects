package myutils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Ins {
    public static void city_bat(PreparedStatement pre, String[] cityCountry) {
        try {
            pre.setString(1, cityCountry[0]);
            pre.setString(2, cityCountry[1]);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public static void author_bat(PreparedStatement pre, int a_id, String name, Timestamp reg_time, String author_id, String phone) {
        try {
            pre.setInt(1, a_id);
            pre.setString(2, name);
            pre.setTimestamp(3, reg_time);
            pre.setString(4, author_id);
            pre.setString(5, phone);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public static void post_bat(PreparedStatement pre, int p_id, int a_id, String title, String content, Timestamp posting_time, String post_city) {
        try {
            pre.setInt(1, p_id);
            pre.setInt(2, a_id);
            pre.setString(3, title);
            pre.setString(4, content);
            pre.setTimestamp(5, posting_time);
            pre.setString(6, post_city);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }

    public static void cate_bat(PreparedStatement pre, int c_id, String name) {
        try {
            pre.setInt(1, c_id);
            pre.setString(2, name);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public static void post_cate_bat(PreparedStatement pre, int p_id, int c_id) {
        try {
            pre.setInt(1, p_id);
            pre.setInt(2, c_id);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public static void follow_bat(PreparedStatement pre, int a_id, int fa_id) {
        try {
            pre.setInt(1, a_id);
            pre.setInt(2, fa_id);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }

    public static void post_act_author_bat(PreparedStatement pre, int p_id, int aa_id) {
        try {
            pre.setInt(1, p_id);
            pre.setInt(2, aa_id);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }

    public static void reply_bat(PreparedStatement pre, int r_id, int father_r_id, String content, int star, int r_author) {
        try {
            pre.setInt(1, r_id);
            pre.setInt(2, father_r_id);
            pre.setString(3, content);
            pre.setInt(4, star);
            pre.setInt(5, r_author);
            pre.addBatch();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }
}
