package com.example.be.model;

import java.sql.Time;

public class InCome {
    int id;
    private int price;
    private String time;
    private  String note;
    private String pathImage;
    private String category;
    private float percent;

    public InCome(String category, float percent) {
        this.category = category;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getPercent() {

        return percent;
    }

    public InCome(int price, String time, String note, String pathImage, String category) {
        this.price = price;
        this.time = time;
        this.note = note;
        this.pathImage = pathImage;
        this.category = category;
    }
    public InCome(int id, int price, String time, String note, String pathImage, String category) {
        this.id = id;
        this.price = price;
        this.time = time;
        this.note = note;
        this.pathImage = pathImage;
        this.category = category;
    }

    public InCome(int price, String time, String category) {

        this.price = price;
        this.time = time;
        this.category = category;
    }

    public InCome(int price, String time, String note, String category) {

        this.price = price;
        this.time = time;
        this.note = note;
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

