package myutils;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class MyUt {
    private static Random r = new Random();

    public static Timestamp randomDate() {
        return new Timestamp(1000000000000L + r.nextLong(680000000000L));
    }

    private static String randomByLength(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
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

    public static void initDatabase(Connection conn) throws SQLException {
        Statement Statement = conn.createStatement();
        conn.setAutoCommit(false);
        Statement.executeUpdate(Info.dropCreate);
        conn.commit();
        conn.setAutoCommit(true);
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
