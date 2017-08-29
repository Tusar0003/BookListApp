package com.example.no0ne.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

/**
 * Created by no0ne on 8/29/17.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("***NOTICE***", "getView() is called");

        View listItemView = convertView;

        if (listItemView == null) {
            return LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author);

        String title = currentBook.getTitle();
        String author = currentBook.getAuthor();

//        Log.d("***NOTICE***", "Title: " + title + ", Author: " + author + "\n");

        titleTextView.setText(title);
        authorTextView.setText(author);

        return listItemView;
    }
}
