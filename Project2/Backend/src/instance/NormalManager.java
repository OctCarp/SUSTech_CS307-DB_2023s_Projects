package instance;

import model.IdCon;

import java.sql.*;
import java.util.ArrayList;

public class NormalManager {
    PreparedStatement allPostQ;
    PreparedStatement allReplyQ;
    PreparedStatement allReply2Q;
    PreparedStatement followedQ;
    PreparedStatement blockedQ;
    PreparedStatement favoritedQ;
    PreparedStatement likedQ;
    PreparedStatement sharedQ;

    public IdCon[] getMyPosts(int a_id) {
        ResultSet resSet;
        try {
            allPostQ.setInt(1, a_id);
            resSet = allPostQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("p_id"), resSet.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getMyReply(int a_id) {
        ResultSet resSet;
        try {
            allReplyQ.setInt(1, a_id);
            resSet = allReplyQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("r_id1"),
                        resSet.getString("r_content")
                ));
            }
            IdCon[] Array = new IdCon[out.size()];

            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getMyReply2(int a_id) {
        ResultSet resSet;
        try {
            allReply2Q.setInt(1, a_id);
            resSet = allReply2Q.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("r_id2"),
                        resSet.getString("r2_content")
                ));
            }
            IdCon[] Array = new IdCon[out.size()];

            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getBlocked(int a_id) {
        ResultSet resSet;
        try {
            blockedQ.setInt(1, a_id);
            resSet = blockedQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("a_id"), resSet.getString("a_name")));
            }
            IdCon[] Array = new IdCon[out.size()];

            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getFollowed(int a_id) {
        ResultSet resSet;
        try {
            followedQ.setInt(1, a_id);
            resSet = followedQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("a_id"), resSet.getString("a_name")));
            }
            IdCon[] Array = new IdCon[out.size()];

            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public IdCon[] getFavorited(int a_id) {
        ResultSet resSet;
        try {
            favoritedQ.setInt(1, a_id);
            resSet = favoritedQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("p_id"), resSet.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getShared(int a_id) {
        ResultSet resSet;
        try {
            sharedQ.setInt(1, a_id);
            resSet = sharedQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("p_id"), resSet.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IdCon[] getLiked(int a_id) {
        ResultSet resSet;
        try {
            likedQ.setInt(1, a_id);
            resSet = likedQ.executeQuery();

            ArrayList<IdCon> out = new ArrayList<>();
            while (resSet.next()) {
                out.add(new IdCon(resSet.getInt("p_id"), resSet.getString("title")));
            }
            IdCon[] Array = new IdCon[out.size()];
            return out.toArray(Array);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public NormalManager() {
        Connection conn = getConn();
        try {
            allPostQ = conn.prepareStatement(
                    "SELECT p.p_id, p.title FROM posts p LEFT JOIN authors a " +
                            "ON a.a_id = p.a_id WHERE a.a_id = ?"
            );
            allReplyQ = getConn().prepareStatement(
                    "SELECT r.p_id, r.r_id1, r.r_content FROM replies r LEFT JOIN authors a " +
                            "ON a.a_id = r.r_a_id WHERE a.a_id = ?"
            );
            allReply2Q = getConn().prepareStatement(
                    "SELECT r.r_id1, r.r_id2, r.r2_content FROM sub_replies r " +
                            "LEFT JOIN authors a " +
                            "ON a.a_id = r.r2_a_id WHERE a.a_id = ?"
            );
            favoritedQ = getConn().prepareStatement(
                    "SELECT p.p_id, p.title FROM favorited fa LEFT JOIN authors a ON a.a_id = fa.favorited_id\n" +
                            "LEFT JOIN posts p on fa.p_id = p.p_id WHERE a.a_id = ? "
            );
            followedQ = getConn().prepareStatement(
                    "SELECT a2.a_id, a2.a_name FROM followed f LEFT JOIN authors a1 ON a1.a_id = f.a_id\n" +
                            "LEFT JOIN authors a2 ON a2.a_id = f.followed_id WHERE a1.a_id = ?"
            );
            blockedQ = getConn().prepareStatement(
                    "SELECT a2.a_id, a2.a_name FROM blocked b LEFT JOIN authors a1 ON a1.a_id = b.a_id\n" +
                            "LEFT JOIN authors a2 ON a2.a_id = b.blocked_id WHERE a1.a_id = ?"
            );
            likedQ = getConn().prepareStatement(
                    "SELECT p.p_id, p.title FROM liked l LEFT JOIN authors a ON a.a_id = l.liked_id\n" +
                            "LEFT JOIN posts p on l.p_id = p.p_id WHERE a.a_id = ? "
            );
            sharedQ = getConn().prepareStatement(
                    "SELECT p.p_id, p.title FROM shared s LEFT JOIN authors a ON a.a_id = s.shared_id\n" +
                            "LEFT JOIN posts p on s.p_id = p.p_id WHERE a.a_id = ? "
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConn() {
        return ConnectionManager.getViewConn();
    }
}
