package com.example.no0ne.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;

    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mEmptyTextView;
    private View loadingIndicator;
    private ListView bookListView;

    private BookAdapter mAdapter;

    private String search = null;

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=java&maxResults=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        mSearchButton = (Button) findViewById(R.id.button_search);
        mSearchEditText = (EditText) findViewById(R.id.edit_text_search);
        bookListView = (ListView) findViewById(R.id.list);
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = String.valueOf(mSearchEditText.getText());
                Log.d("***NOTICE***", search);

                //BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q="+search+"&maxResults=10";
            }
        });

        bookListView.setEmptyView(mEmptyTextView);

        mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, BOOK_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.d("***NOTICE***", "onLoadFinished is called");
        Toast.makeText(this, "" + books, Toast.LENGTH_LONG).show();

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyTextView.setText(R.string.no_internet_connection);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
