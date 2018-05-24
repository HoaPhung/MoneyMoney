package com.example.be.model;

import java.sql.Time;

/**
 * Created by Admin on 5/10/2018.
 */

public class Expense {
    private int id;
    private int price;
    private String time;
    private  String note;
    private String pathImage;
    private String category;
    private float precent;

    public Expense(String category, float precent) {
        this.category = category;
        this.precent = precent;
    }

    public int getId() {
        return id;
    }

    public float getPrecent() {
        return precent;
    }

    public void setPrecent(float precent) {
        this.precent = precent;
    }

    public Expense(int price, String time, String category) {
        this.price = price;
        this.time = time;
        this.category = category;
    }

    public Expense(int price, String time, String note, String category) {
        this.price = price;
        this.time = time;
        this.note = note;
        this.category = category;
    }

    public Expense(int price, String time, String note, String pathImage, String category) {

        this.price = price;
        this.time = time;
        this.note = note;
        this.pathImage = pathImage;
        this.category = category;
    }

    public Expense(int id,int price, String time, String note, String pathImage, String category) {
        this.id = id;
        this.price = price;
        this.time = time;
        this.note = note;
        this.pathImage = pathImage;
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
