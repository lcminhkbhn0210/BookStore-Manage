package com.example.btl_mobile.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title, content,type,price,creation,author,img;

    public Book(int id, String title, String content, String type, String price, String creation, String author, String img) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.price = price;
        this.creation = creation;
        this.author = author;
        this.img = img;
    }





    public Book(String title, String content, String type, String price, String creation, String author, String img) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.price = price;
        this.creation = creation;
        this.author = author;
        this.img = img;
    }


    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
