package com.keatssalazar.groupchat;

/**
 * Created by jagdish on 22/12/17.
 */

public class InstantMessage {

    private String message;
    private String author;
    private String date;

    public InstantMessage(String message, String author,String date) {
        this.message = message;
        this.author = author;
        this.date=date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public InstantMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
