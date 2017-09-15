package com.example.hakeem.booklist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class SearchUi extends AppCompatActivity {


    public static String BOOK_REQUEST_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ui);

        /**
         * add category of book (to search for) to the query URL
         **/
        ImageView goButton = (ImageView) findViewById(R.id.go_ahead);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder category = new StringBuilder();
                SearchView bookCategory = (SearchView) findViewById(R.id.search_category);

                category.append(bookCategory.getQuery());
                if (category == null || category.toString().equals("") || category.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please insert correct category", Toast.LENGTH_SHORT).show();
                } else {
                    BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
                    BOOK_REQUEST_URL += category.toString();
                    Toast.makeText(getApplicationContext(), category.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchUi.this, BookListActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    public static String getBookRequestUrl() {
        return BOOK_REQUEST_URL;
    }
}
