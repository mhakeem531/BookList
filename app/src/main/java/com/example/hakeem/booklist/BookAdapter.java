package com.example.hakeem.booklist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.x;
import static com.example.hakeem.booklist.BookListActivity.LOG_TAG;
//private String myQuery = SearchUi.getBookRequestUrl();

/**
 * Created by hakeem on 9/12/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public ImageView BookCoverImage;// = (ImageView) listItemView.findViewById(R.id.book_cover);
    public String CoverURL;

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Book currentBook = getItem(position);

        BookCoverImage = (ImageView) listItemView.findViewById(R.id.book_cover);
        CoverURL = currentBook.getCoverImagePhotoLink();
        ImageLoadTask task = new ImageLoadTask();
        task.execute();


        TextView BookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        BookTitle.setText(currentBook.getBookTitle());

        TextView BookAuthors = (TextView) listItemView.findViewById(R.id.book_authors);
        String names;
        ArrayList<String> x = currentBook.getAuthorsNames();
        names = x.get(0);
        if (x.size() > 1) {
            names += "\n" + x.get(1);
            ;
        }
        BookAuthors.setText(names);


        TextView BookLanguage = (TextView) listItemView.findViewById(R.id.book_language);
        BookLanguage.setText(currentBook.getBookLanguage());

        TextView BookBdfAvailability = (TextView) listItemView.findViewById(R.id.pdf_availability);
        if (currentBook.isAvailablePDF()) {
            BookBdfAvailability.setText("PDF : YES");
        } else {
            BookBdfAvailability.setText("PDF : NO");
        }

        TextView BookPublishData = (TextView) listItemView.findViewById(R.id.book_publish_date);
        BookPublishData.setText(currentBook.getPublishDate());

        TextView BookPublisher = (TextView) listItemView.findViewById(R.id.publisher);
        BookPublisher.setText(currentBook.getPublisher());

        TextView BookPrice = (TextView) listItemView.findViewById(R.id.price);
        BookPrice.setText(currentBook.getPrice());


        return listItemView;
    }


    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

//        private String url;
//        private ImageView imageView;
//
//        public ImageLoadTask(String url, ImageView imageView) {
//            this.url = url;
//            this.imageView = imageView;
//        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(CoverURL);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            BookCoverImage.setImageBitmap(result);
        }

    }
}
