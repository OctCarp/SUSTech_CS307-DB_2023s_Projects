package backend;

import instance.CountId;
import spark.Request;
import spark.Response;

import static spark.Spark.*;


public class BackendServer {
    public static void main(String[] args) {
        CountId.init();

        port(22307);
        post("/reg/ck_id", UserHandler::ck_identity);
        post("/reg/regi", UserHandler::regMain);
        post("/login", UserHandler::checkLogin);

        post("/normal/own", NormalHandler::getAuthorOpts);

        post("normal/authorOpt/ck", OptHandler::ckAuthorOpt);
        post("normal/authorOpt/do", OptHandler::doAuthorOpt);
        post("normal/authorOpt/un", OptHandler::undoAuthorOpt);
        post("normal/postOpt/ck", OptHandler::ckPostOpt);
        post("normal/postOpt/do", OptHandler::doPostOpt);
        post("normal/postOpt/un", OptHandler::undoPostOpt);

        post("/normal/send/post", SendHandler::sendPost);
        post("/normal/send/reply", SendHandler::sendReply);
        post("/normal/send/reply2", SendHandler::sendReply2);

        post("/view/post", ViewHandler::getPostById);
        post("/view/reply", ViewHandler::getReplyById);
        post("/view/reply2", ViewHandler::getReply2ById);
        post("/view/all_posts", ViewHandler::getAllPost);
        post("/view/all_posts_obt", ViewHandler::getAllPostobt);
        post("/view/post_replies", ViewHandler::getpostreplies);
        post("/view/reply_replies", ViewHandler::getreplyreplies);
        post("/view/hot_list", ViewHandler::gethotlist);
        post("/view/multi_parameter_search", ViewHandler::multi_parameter_search);


        after((Request request, Response response) -> {
            response.header("Content-Type", "application/json");
        });
    }
}
