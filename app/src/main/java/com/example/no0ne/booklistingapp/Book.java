package com.example.no0ne.booklistingapp;

/**
 * Created by no0ne on 8/29/17.
 */
public class Book {

    private String mTitle;
    private String mAuthor;

    public Book(String title, String author) {
//        Log.d("***NOTICE***", "Book() is called");

        mTitle = title;
        mAuthor = author;
//        Log.d("***NOTICE***", "Title: " + title + ", Author: " + author + "\n");
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
