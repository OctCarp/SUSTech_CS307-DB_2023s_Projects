package backend;

import instance.*;
import spark.Request;
import spark.Response;

public class UserHandler {
    static UserManager userManagerInstance;

    public static String checkLogin(Request request, Response response) {
        String identity = request.headers("identity");
        String passwd = request.headers("passwd");

        return String.valueOf(getUserManagerInstance().ckLogin(identity, passwd));
    }

    public static String ck_identity(Request request, Response response) {
        String identity = request.queryParams("identity");

        return String.valueOf(getUserManagerInstance().hasIdentity(identity));
    }

    public static String regMain(Request request, Response response) {
        String name = request.queryParams("name");
        String identity = request.queryParams("identity");
        String passwd = request.queryParams("passwd");
        String phone = request.queryParams("phone");

        return String.valueOf(getUserManagerInstance().reg(name, identity, passwd, phone));
    }

    public static UserManager getUserManagerInstance() {
        if (userManagerInstance == null) {
            userManagerInstance = new UserManager();
        }

        return userManagerInstance;
    }
}

