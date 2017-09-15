package com.example.hakeem.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by hakeem on 9/15/17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {


    private  String queryUrl = "";

    public BookLoader(Context context, String url) {
        super(context);
        this.queryUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (queryUrl == null) {
            return null;
        }
        ArrayList<Book> result = QueryUtils.fetchBookData(this.queryUrl);
        return result;
    }
}

