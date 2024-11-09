package io.github.octcarp.sustech.cs307.instance;

import java.sql.*;

public class UserManager {

    PreparedStatement authorI;
    PreparedStatement authorIDQ;
    PreparedStatement aIDQ;

    public int ckLogin(String identity, String passwd) {
        try {
            aIDQ.setString(1, identity);
            aIDQ.setString(2, passwd);
            ResultSet result = aIDQ.executeQuery();

            if (result.next()) {
                return result.getInt("a_id");
            }

            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean hasIdentity(String identity) {
        try {
            authorIDQ.setString(1, identity);
            ResultSet res = authorIDQ.executeQuery();

            return res.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean reg(String name, String identity, String passwd, String phone) {
        try {
            int a_id = CountId.getAddA();
            authorI.setInt(1, a_id);
            authorI.setString(2, name);
            authorI.setString(3, identity);
            authorI.setString(4, passwd);
            authorI.setString(5, phone);
            authorI.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            int result = authorI.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserManager() {
        try {
            authorI = getConn().prepareStatement(
                    "INSERT INTO authors (a_id, a_name, identity, passwd, a_phone, a_reg_time) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            authorIDQ = getConn().prepareStatement(
                    "SELECT * FROM authors WHERE identity = ?"
            );
            aIDQ = getConn().prepareStatement(
                    "SELECT a_id FROM authors WHERE  identity = ? AND passwd = ?"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Connection getConn() {
        return ConnectionManager.getAuthorsConn();
    }
}
