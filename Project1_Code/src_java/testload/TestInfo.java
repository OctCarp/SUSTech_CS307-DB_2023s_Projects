package testload;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class TestInfo {
    public static void init() {
        readTestFiles();
    }

    static String testUrl;
    static String testClearAuthor;

    static String insFormat = "INSERT INTO authors (a_id, author_name, author_registration_time, author_id, author_phone_number) VALUES (%s, '%s', '%s', '%s', '%s');";
    static String preIns = "INSERT INTO authors (a_id, author_name, author_registration_time, author_id, author_phone_number) VALUES (?, ?, ?, ?, ?)";

    private static void readTestFiles() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(new FileInputStream("./resource/test_paths.properties")));

            testUrl = prop.getProperty("testURL");
            testClearAuthor = Files.readString(Path.of(prop.getProperty("authorTestF")));
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }
}