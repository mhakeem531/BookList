package com.example.hakeem.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = BookListActivity.class.getName();
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView EmptyStateTextView;
    private ProgressBar progress;
    private BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        adapter = new BookAdapter(this, new ArrayList<Book>());
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        EmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(EmptyStateTextView);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(adapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = adapter.getItem(position);
                String earthquakeURL = book.getPreviewLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(earthquakeURL));
                startActivity(i);

            }
        });
/*********************************
        BookAsyncTask task = new BookAsyncTask();
        task.execute(SearchUi.getBookRequestUrl());
 *********************************/
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            EmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Log.e(LOG_TAG, "Hello it's me from onCreateLoader");


        return new BookLoader(BookListActivity.this, SearchUi.getBookRequestUrl());


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> earthquakes) {
        // TODO: Update the UI with the result
        progress = (ProgressBar) findViewById(R.id.loading_spinner);
        progress.setVisibility(View.GONE);
        EmptyStateTextView.setText(R.string.no_books);
        adapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.addAll(earthquakes);
        }
        Log.e(LOG_TAG, "Hello it's me from onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        Log.e(LOG_TAG, "Hello it's me from onLoaderReset");
        // TODO: Loader reset, so we can clear out our existing data.
        adapter.clear();

    }

//    private class BookAsyncTask extends AsyncTask<String, Void, ArrayList<Book>> {
//
//        /**
//         * This method is invoked (or called) on a background thread, so we can perform
//         * long-running operations like making a network request.
//         * <p>
//         * It is NOT okay to update the UI from a background thread, so we just return an
//         * {@link Book} object as the result.
//         */
//        protected ArrayList<Book> doInBackground(String... urls) {
//
//
//            // Don't perform the request if there are no URLs, or the first URL is null.
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//            ArrayList<Book> result = QueryUtils.fetchBookData(urls[0]);
//            return result;
//        }
//
//        /**
//         * This method is invoked on the main UI thread after the background work has been
//         * completed.
//         * <p>
//         * It IS okay to modify the UI within this method. We take the {@link Book} object
//         * (which was returned from the doInBackground() method) and update the views on the screen.
//         */
//        protected void onPostExecute(ArrayList<Book> result) {
//            adapter.clear();
//            // If there is no result, do nothing.
//            if (result == null || result.isEmpty()) {
//                return;
//            }
//            adapter.addAll(result);
//
//        }
//
//
//    }



}
