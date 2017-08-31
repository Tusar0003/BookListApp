package com.example.no0ne.booklistingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mEmptyTextView;
    private View loadingIndicator;
    private ListView bookListView;

    private BookAdapter mAdapter;

    private String BOOK_REQUEST_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        mSearchButton = (Button) findViewById(R.id.button_search);
        mSearchEditText = (EditText) findViewById(R.id.edit_text_search);
        bookListView = (ListView) findViewById(R.id.list);
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);

        bookListView.setEmptyView(mEmptyTextView);
        mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentBook = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentBook.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = String.valueOf(mSearchEditText.getText());
                BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + search + "&maxResults=10";

                InputMethodManager methodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                BookAsyncTask task = new BookAsyncTask();
                task.execute(BOOK_REQUEST_URL);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isConnected) {
            loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            Toast.makeText(this, "Successfully Connected to the Internet", Toast.LENGTH_SHORT).show();
        } else {
            loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    private void updateUi(List<Book> books) {
        if (books == null && books.isEmpty()) {
            mEmptyTextView.setText(R.string.no_data);
        }

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();
        mAdapter.addAll(books);
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> books = QueryUtils.fetchEarthquakeData(urls[0]);

            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            if (books == null) {
                return;
            }

            updateUi(books);
        }
    }
}
