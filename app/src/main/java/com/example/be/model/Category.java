package com.example.be.model;

import com.example.be.database.MMDatabase;

/**
 * Created by Admin on 5/10/2018.
 */

public class Category {
    //private String color;
    //private String icon;
    //private String name;
    private int id;
    private String name;


    public Category(){

    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String name) {

        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
