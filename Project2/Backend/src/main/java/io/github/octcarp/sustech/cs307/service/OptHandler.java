package io.github.octcarp.sustech.cs307.service;

import io.github.octcarp.sustech.cs307.instance.OptManager;
import spark.Request;
import spark.Response;

public class OptHandler {
    static OptManager optInstance;

    public static String ckAuthorOpt(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        int a_id2 = Integer.parseInt(request.headers("a_id2"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "follow" -> getOptInstance().hasFollowed(a_id, a_id2);
            case "block" -> getOptInstance().hasBlocked(a_id, a_id2);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };


        return String.valueOf(res);
    }

    public static String undoAuthorOpt(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        int a_id2 = Integer.parseInt(request.headers("a_id2"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "follow" -> getOptInstance().unFollow(a_id, a_id2);
            case "block" -> getOptInstance().unBlock(a_id, a_id2);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        return String.valueOf(res);
    }

    public static String doAuthorOpt(Request request, Response response) {
        int a_id = Integer.parseInt(request.headers("a_id"));
        int a_id2 = Integer.parseInt(request.headers("a_id2"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "follow" -> getOptInstance().doFollow(a_id, a_id2);
            case "block" -> getOptInstance().doBlock(a_id, a_id2);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        return String.valueOf(res);
    }

    public static String ckPostOpt(Request request, Response response) {
        int p_id = Integer.parseInt(request.headers("p_id"));
        int a_id = Integer.parseInt(request.headers("a_id"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "liked" -> getOptInstance().hasLiked(p_id, a_id);
            case "shared" -> getOptInstance().hasShared(p_id, a_id);
            case "favorited" -> getOptInstance().hasFavorited(p_id, a_id);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        return String.valueOf(res);
    }

    public static String undoPostOpt(Request request, Response response) {
        int p_id = Integer.parseInt(request.headers("p_id"));
        int a_id = Integer.parseInt(request.headers("a_id"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "liked" -> getOptInstance().unLiked(p_id, a_id);
            case "shared" -> getOptInstance().unShared(p_id, a_id);
            case "favorited" -> getOptInstance().unFavorited(p_id, a_id);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        return String.valueOf(res);
    }

    public static String doPostOpt(Request request, Response response) {
        int p_id = Integer.parseInt(request.headers("p_id"));
        int a_id = Integer.parseInt(request.headers("a_id"));
        String type = request.headers("type");

        boolean res;
        res = switch (type) {
            case "liked" -> getOptInstance().doLiked(p_id, a_id);
            case "shared" -> getOptInstance().doShared(p_id, a_id);
            case "favorited" -> getOptInstance().doFavorited(p_id, a_id);

            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        return String.valueOf(res);
    }

    private static OptManager getOptInstance() {
        if (optInstance == null) {
            optInstance = new OptManager();
        }

        return optInstance;
    }
}

