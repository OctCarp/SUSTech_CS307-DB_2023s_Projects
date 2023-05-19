package instance;

import java.sql.*;

public class CountId {
    private static int a_id_cnt;
    private static int c_id_cnt;
    private static int p_id_cnt;
    private static int r1_id_cnt;
    private static int r2_id_cnt;


    public static void init() {
        try {
            ResultSet res;
            Statement statement = getConn().createStatement();

            res = statement.executeQuery("SELECT max(a_id) as a_id_count FROM authors");
            res.next();
            a_id_cnt = res.getInt("a_id_count");

            res = statement.executeQuery("SELECT max(p_id) as p_id_count FROM posts");
            res.next();
            p_id_cnt = res.getInt("p_id_count");

            res = statement.executeQuery("SELECT max(r_id1) as r1_id_count FROM replies");
            res.next();
            r1_id_cnt = res.getInt("r1_id_count");

            res = statement.executeQuery("SELECT max(r_id2) as r2_id_count FROM sub_replies");
            res.next();
            r2_id_cnt = res.getInt("r2_id_count");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static int getAddP() {
        return ++p_id_cnt;
    }

    public static int getAddC() {
        return ++c_id_cnt;
    }

    public static int getAddA() {
        return ++a_id_cnt;
    }

    public static int getAddR1() {
        return ++r1_id_cnt;
    }

    public static int getAddR2() {
        return ++r2_id_cnt;
    }

    private static Connection getConn() {
        return ConnectionManager.getRootConnection();
    }
}
