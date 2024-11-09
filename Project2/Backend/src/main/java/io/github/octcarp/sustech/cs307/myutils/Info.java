package io.github.octcarp.sustech.cs307.myutils;

import com.alibaba.fastjson.JSON;
import io.github.octcarp.sustech.cs307.loader.models.Post;
import io.github.octcarp.sustech.cs307.loader.models.Reply;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Info {
    public static void infoInit() {
        readFiles();
    }

    public static String url;
    public static String user;
    public static String pwd;
    public static String dropCreate;
    public static String gainUsers;
    public static String enTrigger;
    public static String disTrigger;

    public static int serverPort;

    public static List<Post> posts;
    public static List<Reply> replies;

    private static void readFiles() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(
                    Objects.requireNonNull(Info.class.getClassLoader().getResourceAsStream("paths.properties"))));
            url = prop.getProperty("dbURL");
            user = prop.getProperty("username");
            pwd = prop.getProperty("password");
            serverPort = Integer.parseInt(prop.getProperty("server.port"));

            posts = JSON.parseArray(Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource(prop.getProperty("postF")).toURI()
            )), Post.class);
            replies = JSON.parseArray(Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource("replyF").toURI(
                    )
            )), Reply.class);

            dropCreate = Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource("dropCreateF").toURI()));
            gainUsers= Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource("gainUserF").toURI()));
            enTrigger = Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource("enTriggersF").toURI()));
            disTrigger = Files.readString(Paths.get(
                    Info.class.getClassLoader().getResource("disTriggersF").toURI()));

        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
