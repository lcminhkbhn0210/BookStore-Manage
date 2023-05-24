package com.example.btl_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Bill implements Serializable {
    private int id;
    private List<Cart> carts;
    private String creation;
    private double total;

    public Bill(int id, List<Cart> carts, String creation, double total) {
        this.id = id;
        this.carts = carts;
        this.creation = creation;
        this.total = total;
    }

    public Bill(List<Cart> carts, String creation, double total) {
        this.carts = carts;
        this.creation = creation;
        this.total = total;
    }

    public Bill(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
