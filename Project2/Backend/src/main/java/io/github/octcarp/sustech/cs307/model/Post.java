package io.github.octcarp.sustech.cs307.model;

public class Post {
    private int p_id;
    private String title;
    private int a_id;
    private String a_name;
    private String content;
    private String time;
    private String city;
    private String[] tags;

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
