package instance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static String address = "127.0.0.1:5432/CS307_Project2";
    public static String baseUrl = "jdbc:postgresql://" + address;

    public static void updateBaseUrl() {
        baseUrl = "jdbc:postgresql://" + address;
    }


    public static String rootUsername = "checker";
    public static String rootPassword = "123456";

    static Connection viewConn, normalConn, rootConn;

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
                viewConn = DriverManager.getConnection(baseUrl, rootUsername, rootPassword);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return viewConn;
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
