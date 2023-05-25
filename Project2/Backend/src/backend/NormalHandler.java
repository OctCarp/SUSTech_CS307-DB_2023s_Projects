package backend;

import com.google.gson.Gson;
import instance.NormalManager;
import spark.Request;
import spark.Response;

public class NormalHandler {
    static NormalManager normalInstance;

    public static String getAuthorOpts(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        String type = request.headers("type");

        return switch (type) {
            case "info" -> new Gson().toJson(getNormalInstance().getAuthorInfo(a_id));
            case "post" -> new Gson().toJson(getNormalInstance().getMyPosts(a_id));
            case "reply" -> new Gson().toJson(getNormalInstance().getMyReply(a_id));
            case "reply2" -> new Gson().toJson(getNormalInstance().getMyReply2(a_id));
            case "liked" -> new Gson().toJson(getNormalInstance().getLiked(a_id));
            case "shared" -> new Gson().toJson(getNormalInstance().getShared(a_id));
            case "favorited" -> new Gson().toJson(getNormalInstance().getFavorited(a_id));
            case "follow" -> new Gson().toJson(getNormalInstance().getFollowed(a_id));
            case "block" -> new Gson().toJson(getNormalInstance().getBlocked(a_id));

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static NormalManager getNormalInstance() {
        if (normalInstance == null) {
            normalInstance = new NormalManager();
        }

        return normalInstance;
    }
}
