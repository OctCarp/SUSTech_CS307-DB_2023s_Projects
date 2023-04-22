package myutils;

import models.Post;
import models.Reply;

import com.alibaba.fastjson.JSON;

import java.io.*;
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
    public static String disTrigger;
    public static String createAuthorTest;
    public static List<Post> posts;
    public static List<Reply> replies;

    public static String iAuthor = "INSERT INTO authors (a_id, author_name, author_registration_time, author_id, author_phone_number) VALUES (?, ?, ?, ?, ?)";
    public static String iPost = "INSERT INTO posts (p_id, a_id, title, content, posting_time, posting_city) VALUES (?, ?, ?, ?, ?, ?)";
    public static String iCity = "INSERT INTO cities (city_name, city_country) VALUES (?, ?)";
    public static String iCate = "INSERT INTO category (c_id, category_name) VALUES (?, ?)";
    public static String iPostCate = "INSERT INTO post_category (p_id, c_id) VALUES (?, ?)";
    public static String iFollower = "INSERT INTO follower (a_id, follower_id) VALUES (?, ?)";
    public static String iFavorited = "INSERT INTO favorited (p_id, favorited_id) VALUES (?, ?)";
    public static String iLiked = "INSERT INTO liked (p_id, liked_id) VALUES (?, ?)";
    public static String iShare = "INSERT INTO shared (p_id, shared_id) VALUES (?, ?)";
    public static String iReply = "INSERT INTO replies (r_id1, p_id, reply_content, reply_stars, reply_author_id) VALUES (?, ?, ?, ?, ?)";
    public static String iSecReply = "INSERT INTO secondary_replies (r_id2, r_id1, secondary_reply_content, secondary_reply_stars, secondary_reply_author_id) VALUES (?, ?, ?, ?, ?)";

    private static void readFiles() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(new FileInputStream("./resource/paths.properties")));
            url = prop.getProperty("dbURL");
            user = prop.getProperty("username");
            pwd = prop.getProperty("password");

            posts = JSON.parseArray(Files.readString(Path.of(prop.getProperty("postF"))), Post.class);
            replies = JSON.parseArray(Files.readString(Path.of(prop.getProperty("replyF"))), Reply.class);

            dropCreate = Files.readString(Path.of(prop.getProperty("dropCreateF")));
            disTrigger = Files.readString(Path.of(prop.getProperty("disTriggersF")));
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }
}
