package com.example.nthan.notelifeadvance.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nthan on 10/9/2017.
 */

public class Note implements Serializable{


    public static final String NOTE_COLOR_PURPLE = "#ce93d8";
    public static final String NOTE_COLOR_PINK = "#f8bbd0";
    public static final String NOTE_COLOR_BLUE = "#bbdefb";
    public static final String NOTE_COLOR_RED = "#ffccbc";
    public static final String NOTE_COLOR_GREEN = "#c8e6c9";

    private int id;
    private String title;
    private String content; // content of Note
    private String date_create;
    private String date_Update_last;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note( String title, String content, String date_create, String date_Update_last, String color) {
        this.content = content;
        this.date_create = date_create;
        this.title = title;
        this.date_Update_last = date_Update_last;
        this.color = color;
    }


    public Note(int id,  String title, String content, String date_create, String date_Update_last, String color) {
        this.id = id;
        this.content = content;
        this.date_create = date_create;
        this.title = title;
        this.date_Update_last = date_Update_last;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_Update_last() {
        return date_Update_last;
    }

    public void setDate_Update_last(String date_Update_last) {
        this.date_Update_last = date_Update_last;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
