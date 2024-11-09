package io.github.octcarp.sustech.cs307.service;

import com.google.gson.Gson;
import io.github.octcarp.sustech.cs307.instance.SendManager;
import spark.Request;
import spark.Response;

public class SendHandler {
    static SendManager sendInstance;

    public static String sendPost(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        String title = request.headers("title");
        String content = request.headers("content");
        String city = request.headers("city");
        String[] cates = new Gson().fromJson(request.headers("cate"), String[].class);

        return String.valueOf(getSendInstance().sendPost(a_id, title, content, city, cates));
    }

    public static String sendReply(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        int p_id = Integer.parseInt(request.headers("p_id"));
        String content = request.headers("content");

        return String.valueOf(getSendInstance().sendReply(p_id, a_id, content));
    }

    public static String sendReply2(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        int r_id1 = Integer.parseInt(request.headers("r_id1"));
        String content = request.headers("content");

        return String.valueOf(getSendInstance().sendSubReply(r_id1, a_id, content));
    }

    private static SendManager getSendInstance() {
        if (sendInstance == null) {
            sendInstance = new SendManager();
        }

        return sendInstance;
    }
}
