package testload;

import models.Post;
import models.Reply;
import myutils.Info;
import myutils.MyUt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLoaders {

    final int BATCH_SIZE = 1000;

    long startTime, endTime;
    int cnt = 0;

    Connection conn;
    List<Post> posts;
    List<Reply> replies;

    List<String> authors = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    List<String> phones = new ArrayList<>();

    AbstractLoaders() {
        posts = Info.posts;
        replies = Info.replies;
    }

    abstract void insertAuthor(int a_id, String author, Timestamp time, String author_id, String phone);

    abstract void testStart();

    abstract void closeConnect();

    void printMessage() {
        System.out.printf("%s:\n%d records successfully loaded\n", this, cnt);
        System.out.printf("Loading speed : %d records/s\n\n", (cnt * 1000L) / (endTime - startTime));
    }

    void getTestConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver.");
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(TestInfo.testUrl, Info.user, Info.pwd);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    void clearDataInit() {
        getTestConnection();

        Statement stmt0;
        if (conn != null) {
            try {
                stmt0 = conn.createStatement();
                stmt0.executeUpdate(TestInfo.testClearAuthor);
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        closeConnect();
    }

    private void ambAuthor(String name) {
        int id = authors.indexOf(name);
        if (id == -1) {
            id = authors.size();
            authors.add(name);

            insertAuthor(id, name, MyUt.randomDate(), MyUt.randStrByLenAdd(ids, 18), MyUt.randStrByLenAdd(phones, 11));
        }
    }

    void insLogic() {
        for (Post p : posts) {
            String author = p.getAuthor();
            String authorID = p.getAuthorID();
            String phone = p.getAuthorPhone();

            int a_id = authors.indexOf(author);
            if (a_id == -1) {
                a_id = authors.size();

                authors.add(author);
                ids.add(authorID);
                phones.add(phone);

                insertAuthor(a_id, author, Timestamp.valueOf(p.getAuthorRegistrationTime()), authorID, phone);
            }
        }

        for (Post p : posts) {
            for (String follower : p.getAuthorFollowedBy()) {
                ambAuthor(follower);
            }
            for (String sharer : p.getAuthorShared()) {
                ambAuthor(sharer);
            }
            for (String liker : p.getAuthorLiked()) {
                ambAuthor(liker);
            }
            for (String favor : p.getAuthorFavorited()) {
                ambAuthor(favor);
            }
        }

        String r1a_name = "";
        for (Reply reply : replies) {
            if (r1a_name.equals(reply.getReplyAuthor())) {
                ambAuthor(reply.getSecondaryReplyAuthor());
            } else {
                r1a_name = reply.getReplyAuthor();

                ambAuthor(r1a_name);
                ambAuthor(reply.getSecondaryReplyAuthor());
            }
        }
    }
}

abstract class NormalStmt extends AbstractLoaders {
    Statement stmt;

    @Override
    void closeConnect() {
        if (conn != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                conn.close();
                conn = null;
            } catch (Exception ignored) {
            }
        }
    }
}

abstract class PrepareStmt extends AbstractLoaders {
    PreparedStatement pStmt;

    @Override
    void closeConnect() {
        if (conn != null) {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
                conn.close();
                conn = null;
            } catch (SQLException se) {
                System.err.println(se);
            }
        }
    }

    void setPrepareStatement() {
        try {
            pStmt = conn.prepareStatement(TestInfo.preIns);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnect();
            System.exit(1);
        }
    }

    void prepareAuthor(int a_id, String name, Timestamp reg_time, String author_id, String phone) {
        try {
            pStmt.setInt(1, a_id);
            pStmt.setString(2, name);
            pStmt.setTimestamp(3, reg_time);
            pStmt.setString(4, author_id);
            pStmt.setString(5, phone);
            pStmt.executeUpdate();
        } catch (SQLException se) {
            System.err.println(se);
        }
    }
}
