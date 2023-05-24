package com.example.btl_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private int id;
    private User user;
    private Book book;
    private int amount;
    private int sold;

    public Cart(int id, User user, Book book, int amount, int sold) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.sold = sold;
    }

    public Cart(User user, Book book, int amount, int sold) {
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.sold = sold;
    }
    public Cart(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
