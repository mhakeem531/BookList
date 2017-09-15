package com.example.hakeem.booklist;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.hakeem.booklist.BookListActivity.LOG_TAG;
import static com.example.hakeem.booklist.R.id.price;
import static com.example.hakeem.booklist.R.id.publisher;


/**
 * Created by hakeem on 9/10/17.
 */

public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            // Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                jsonResponse = "Error response code: " + urlConnection.getResponseCode();
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @NonNull
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Book> extractBookFeatureFromJson(String bookJSON) {

        // Create an empty ArrayList that we can start adding books to
        ArrayList<Book> books = new ArrayList<>();


        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        try {


            JSONObject reader = new JSONObject(bookJSON);
            JSONArray items = reader.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                JSONObject bookInfo = c.getJSONObject("volumeInfo");

                /**get book title*/
                String title;
                title = bookInfo.getString("title");
                Log.e(LOG_TAG, "title is" + title);

                /**get book authors names*/

                JSONArray authors = bookInfo.getJSONArray("authors");


                ArrayList<String> authorsNames = new ArrayList<>();
                for(int j = 0 ; j < authors.length() ; j++)
                {
                    authorsNames.add(authors.getString(j));
                }
                Log.e(LOG_TAG, "authors is" + authorsNames.get(0));
                /**get book publisher name*/
                String publisher;
                publisher = bookInfo.getString("publisher");
                Log.e(LOG_TAG, "publisher is" + publisher);


                /**get book publish date*/
                String publishDate;
                publishDate = bookInfo.getString("publishedDate");
                Log.e(LOG_TAG, "data is" + publishDate);


                /**get book cover image link*/
                JSONObject coverPhotoLinks = bookInfo.getJSONObject("imageLinks");
                String coverPhotoLink;
                coverPhotoLink = coverPhotoLinks.getString("smallThumbnail");
                Log.e(LOG_TAG, "photo link is" + coverPhotoLink);


                /**get book preview link*/
                String previewLink;
                previewLink = bookInfo.getString("previewLink");
                Log.e(LOG_TAG, "previewLink  is" + previewLink);


                /**get book language*/
                String bookLanguage;
                bookLanguage = bookInfo.getString("language");
                Log.e(LOG_TAG, "bookLanguage  is" + bookLanguage);

                /**get book price*/
                JSONObject salesInfo = c.getJSONObject("saleInfo");
                String saleability = salesInfo.getString("saleability");
                String price;
                if(saleability.equals("FOR_SALE"))
                {
                    JSONObject listPriceObj = salesInfo.getJSONObject("listPrice");
                    String countryCode = listPriceObj.getString("currencyCode");
                    double amount = listPriceObj.getDouble("amount");
                    price = handleAmount(amount) + " " + countryCode;
                }
                else{
                    price = "NOT_FOR_SALE";
                }




                Log.e(LOG_TAG, "price  is" + price);


                /**is book available as PDF or not*/
                JSONObject accessInfo = c.getJSONObject("accessInfo");
                JSONObject pdfObj = accessInfo.getJSONObject("pdf");
                boolean pdfAvailability;
                pdfAvailability = pdfObj.getBoolean("isAvailable");
                Log.e(LOG_TAG, "pdfAvailability  is" + pdfAvailability);

                books.add(new Book(title, authorsNames, publisher, publishDate, bookLanguage, price, pdfAvailability, previewLink, coverPhotoLink));
            }

            // If there are results in the features array
            if (items.length() > 0) {
                return books;

            }
            else{
                Log.e(LOG_TAG, "length is less than 1");
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the books JSON results", e);
        }
        return null;
    }

    /**
     * to handle the price amount of a book to get the price as a double with only two digits after
     * decimal point
     */
    private static String handleAmount(double amount) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(amount);
    }


    /**
     * Query the  data set and return an {@link Book} object to represent a single earthquake.
     */
    public static ArrayList<Book> fetchBookData(String requestUrl) {
        // Create URL object
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
             Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Book> books = extractBookFeatureFromJson(jsonResponse);

        // Return the {@link Books}
        return books;
    }


}
