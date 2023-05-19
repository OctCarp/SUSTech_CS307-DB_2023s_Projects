package model;

public class Reply {
    int rs_id;
    int ori_id;

    int a_id;

    String ori_c;
    String a_name;
    String content;
    int stars;

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public void setRs_id(int rs_id) {
        this.rs_id = rs_id;
    }

    public void setOri_id(int ori_id) {
        this.ori_id = ori_id;
    }

    public void setOri_c(String ori_c) {
        this.ori_c = ori_c;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
