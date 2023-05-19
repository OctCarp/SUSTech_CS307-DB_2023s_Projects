package loader.myutils;

import com.alibaba.fastjson.JSON;
import loader.models.Post;
import loader.models.Reply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class Info {
    public static void infoInit() {
        readFiles();
    }

    public static String url;
    public static String user;
    public static String pwd;
    public static String dropCreate;
    public static String enTrigger;
    public static String disTrigger;
    public static List<Post> posts;
    public static List<Reply> replies;

    private static void readFiles() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(new FileInputStream("././resource/paths.properties")));
            url = prop.getProperty("dbURL");
            user = prop.getProperty("username");
            pwd = prop.getProperty("password");

            posts = JSON.parseArray(Files.readString(Path.of(prop.getProperty("postF"))), Post.class);
            replies = JSON.parseArray(Files.readString(Path.of(prop.getProperty("replyF"))), Reply.class);

            dropCreate = Files.readString(Path.of(prop.getProperty("dropCreateF")));
            enTrigger = Files.readString(Path.of(prop.getProperty("enTriggersF")));
            disTrigger = Files.readString(Path.of(prop.getProperty("disTriggersF")));
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }
}
