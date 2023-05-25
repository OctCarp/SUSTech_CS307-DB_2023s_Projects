package model;

import java.sql.Timestamp;

public class Author {
    int a_id;
    String identity;

    String name;
    String phone;
    String reg;

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }
}
