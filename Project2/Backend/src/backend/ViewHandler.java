package backend;

import com.google.gson.Gson;
import instance.ViewManager;
import model.Post;
import model.Reply;
import spark.Request;
import spark.Response;

public class ViewHandler {
    static ViewManager viewInstance;

    public static String getPostById(Request request, Response response) {
        int p_id = Integer.parseInt(request.headers("p_id"));
        int va_id = Integer.parseInt(request.headers("va_id"));

        Post p = getViewInstance().getPost(p_id, va_id);
        return new Gson().toJson(p);
    }

    public static String getReplyById(Request request, Response response) {
        int r_id1 = Integer.parseInt(request.headers("r_id1"));
        int va_id = Integer.parseInt(request.headers("va_id"));

        Reply r = getViewInstance().getReply(r_id1, va_id);
        return new Gson().toJson(r);
    }

    public static String getReply2ById(Request request, Response response) {
        int r_id2 = Integer.parseInt(request.headers("r_id2"));
        int va_id = Integer.parseInt(request.headers("va_id"));

        Reply r2 = getViewInstance().getReply2(r_id2, va_id);
        return new Gson().toJson(r2);
    }


    public static ViewManager getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new ViewManager();
        }
        return viewInstance;
    }
}
