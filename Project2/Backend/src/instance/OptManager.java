package instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OptManager {
    PreparedStatement blockedC;
    PreparedStatement blockedI;
    PreparedStatement blockedD;

    PreparedStatement followedC;
    PreparedStatement favoritedC;
    PreparedStatement likedC;
    PreparedStatement sharedC;

    PreparedStatement followedI;
    PreparedStatement sharedI;
    PreparedStatement likedI;
    PreparedStatement favoritedI;

    PreparedStatement followedD;
    PreparedStatement sharedD;
    PreparedStatement likedD;
    PreparedStatement favoritedD;

    public boolean hasLiked(int p_id, int a_id) {
        try {
            likedC.setInt(1, p_id);
            likedC.setInt(2, a_id);

            return likedC.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasShared(int p_id, int a_id) {
        try {
            sharedC.setInt(1, p_id);
            sharedC.setInt(2, a_id);

            return sharedC.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasFavorited(int p_id, int a_id) {
        try {
            favoritedC.setInt(1, p_id);
            favoritedC.setInt(2, a_id);

            return favoritedC.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasFollowed(int a_id, int fa_id) {
        try {
            followedC.setInt(1, a_id);
            followedC.setInt(2, fa_id);

            return followedC.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasBlocked(int a_id, int ba_id) {
        try {
            blockedC.setInt(1, a_id);
            blockedC.setInt(2, ba_id);

            return blockedC.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unLiked(int p_id, int a_id) {
        try {
            likedD.setInt(1, p_id);
            likedD.setInt(2, a_id);

            return likedD.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unShared(int p_id, int a_id) {
        try {
            sharedD.setInt(1, p_id);
            sharedD.setInt(2, a_id);

            return sharedD.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unFavorited(int p_id, int a_id) {
        try {
            favoritedD.setInt(1, p_id);
            favoritedD.setInt(2, a_id);

            return favoritedD.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unFollow(int a_id, int fa_id) {
        try {
            followedD.setInt(1, a_id);
            followedD.setInt(2, fa_id);

            return followedD.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean unBlock(int a_id, int ba_id) {
        try {
            blockedD.setInt(1, a_id);
            blockedD.setInt(2, ba_id);

            return blockedD.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean doLiked(int p_id, int a_id) {
        try {
            likedI.setInt(1, p_id);
            likedI.setInt(2, a_id);

            return likedI.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doShared(int p_id, int a_id) {
        try {
            sharedI.setInt(1, p_id);
            sharedI.setInt(2, a_id);

            return sharedI.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doFavorited(int p_id, int a_id) {
        try {
            favoritedI.setInt(1, p_id);
            favoritedI.setInt(2, a_id);

            return favoritedI.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doFollow(int a_id, int fa_id) {
        try {
            followedI.setInt(1, a_id);
            followedI.setInt(2, fa_id);

            return followedI.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean doBlock(int a_id, int ba_id) {
        try {
            blockedI.setInt(1, a_id);
            blockedI.setInt(2, ba_id);

            return blockedI.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public OptManager() {
        Connection conn = getConn();
        try {
            likedC = conn.prepareStatement(
                    "SELECT * FROM liked WHERE p_id = ? AND liked_id = ?"
            );
            sharedC = conn.prepareStatement(
                    "SELECT * FROM shared WHERE p_id = ? AND shared_id = ?"
            );
            favoritedC = conn.prepareStatement(
                    "SELECT * FROM favorited WHERE p_id = ? AND favorited_id = ?"
            );
            followedC = conn.prepareStatement(
                    "SELECT * FROM followed WHERE a_id = ? AND followed_id = ?"
            );
            blockedC = conn.prepareStatement(
                    "SELECT * FROM blocked WHERE a_id = ? AND blocked_id = ?"
            );
            likedD = conn.prepareStatement(
                    "DELETE FROM liked WHERE p_id = ? AND liked_id = ?"
            );
            sharedD = conn.prepareStatement(
                    "DELETE FROM shared WHERE p_id = ? AND shared_id = ?"
            );
            favoritedD = conn.prepareStatement(
                    "DELETE FROM favorited WHERE p_id = ? AND favorited_id = ?"
            );
            followedD = conn.prepareStatement(
                    "DELETE FROM followed WHERE a_id = ? AND followed_id = ?"
            );
            blockedD = conn.prepareStatement(
                    "DELETE FROM blocked WHERE a_id = ? AND blocked_id = ?"
            );
            likedI = conn.prepareStatement(
                    "INSERT INTO liked (p_id, liked_id) VALUES (?, ?)"
            );
            sharedI = conn.prepareStatement(
                    "INSERT INTO shared (p_id, shared_id) VALUES (?, ?)"
            );
            favoritedI = conn.prepareStatement(
                    "INSERT INTO favorited (p_id, favorited_id) VALUES (?, ?)"
            );
            followedI = conn.prepareStatement(
                    "INSERT INTO followed (a_id, followed_id) VALUES (?, ?)"
            );
            blockedI = conn.prepareStatement(
                    "INSERT INTO blocked (a_id, blocked_id) VALUES (?, ?)"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConn() {
        return ConnectionManager.getOptConn();
    }
}
