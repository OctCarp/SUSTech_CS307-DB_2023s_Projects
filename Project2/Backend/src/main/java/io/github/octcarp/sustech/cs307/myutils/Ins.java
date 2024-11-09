package io.github.octcarp.sustech.cs307.myutils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Ins {

    public static void author_bat(PreparedStatement pre, int a_id, String name, String identity,
                                  String passwd, String phone, Timestamp reg_time) {
        try {
            pre.setInt(1, a_id);
            pre.setString(2, name);
            pre.setString(3, identity);
            pre.setString(4, passwd);
            pre.setString(5, phone);
            pre.setTimestamp(6, reg_time);
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

    public static void post_view_bat(PreparedStatement pre, int p_id, int view_count) {
        try {
            pre.setInt(1, p_id);
            pre.setInt(2, view_count);
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

    public static void post_cate_bat(PreparedStatement pre, int p_id, String cate) {
        try {
            pre.setInt(1, p_id);
            pre.setString(2, cate);
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
