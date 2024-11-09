package io.github.octcarp.sustech.cs307.instance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String baseUrl = "jdbc:postgresql://127.0.0.1:5432/CS307_Project2";

    private static final String baseUrl2 = "jdbc:opengauss://192.168.161.18:5432/CS307_Project2";
    private static final String rootUsername = "checker";
    private static final String rootPassword = "123456";
    private static final String rootPassword2 = "gauss@123";

    private final static String viewerName = "viewer";
    private final static String viewerPass = "viewer@123";
    private final static String opterName = "opter";
    private final static String opterPass = "opter@123";
    private final static String normalName = "normal";
    private final static String normalPass = "normal@123";
    private final static String senderName = "sender";
    private final static String senderPass = "sender@123";
    private final static String authorsName = "authors";
    private final static String authorsPass = "authors@123";

    private static Connection viewConn, normalConn, optConn, senderConn, authorsConn, rootConn;

    public static void closeAllConn() {
        try {
            if (viewConn != null) viewConn.close();
            if (normalConn != null) normalConn.close();
            if (rootConn != null) rootConn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Getters
     */
    public static Connection getViewConn() {
        if (viewConn == null) {
            try {
                viewConn = DriverManager.getConnection(baseUrl, viewerName, viewerPass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return viewConn;
    }

    public static Connection getOptConn() {
        if (optConn == null) {
            try {
                optConn = DriverManager.getConnection(baseUrl, opterName, opterPass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return optConn;
    }

    public static Connection getNormalConn() {
        if (normalConn == null) {
            try {
                normalConn = DriverManager.getConnection(baseUrl, normalName, normalPass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return normalConn;
    }

    public static Connection getSenderConn() {
        if (senderConn == null) {
            try {
                senderConn = DriverManager.getConnection(baseUrl, senderName, senderPass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return senderConn;
    }

    public static Connection getAuthorsConn() {
        if (authorsConn == null) {
            try {
                authorsConn = DriverManager.getConnection(baseUrl, authorsName, authorsPass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return authorsConn;
    }

    public static Connection getRootConnection() {
        /*
        Initialize
         */
        if (rootConn == null) {
            try {
                rootConn = DriverManager.getConnection(baseUrl, rootUsername, rootPassword);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return rootConn;
    }

}
