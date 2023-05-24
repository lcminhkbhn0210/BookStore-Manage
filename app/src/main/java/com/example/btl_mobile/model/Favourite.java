package com.example.btl_mobile.model;

import java.io.Serializable;

public class Favourite implements Serializable {
    private int id;
    private int userid;
    private int bookid;

    public Favourite(int id, int userid, int bookid) {
        this.id = id;
        this.userid = userid;
        this.bookid = bookid;
    }

    public Favourite(int userid, int bookid) {
        this.userid = userid;
        this.bookid = bookid;
    }

    public Favourite(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
}
