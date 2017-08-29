package com.example.no0ne.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by no0ne on 8/29/17.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.d("***NOTICE***", "BookLoader() is called: " + mUrl);
    }

    @Override
    protected void onStartLoading() {
        Log.d("***NOTICE***", "onStartLoading() is called");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Book> books = QueryUtils.fetchEarthquakeData(mUrl);

        Log.d("***NOTICE***", "loadInBackground() is called: " + books);
        return books;
    }
}
