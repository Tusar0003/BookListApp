package com.example.no0ne.booklistingapp;

/**
 * Created by no0ne on 8/29/17.
 */
public class Book {

    private String mTitle;
    private String mAuthor;
    private String mUrl;

    public Book(String title, String author, String url) {
        mTitle = title;
        mAuthor = author;
        mUrl = url;
//        Log.d("***NOTICE***", "Title: " + title + ", Author: " + author + "\n");
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }
}
