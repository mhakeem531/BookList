package com.example.hakeem.booklist;

import java.util.ArrayList;

/**
 * Created by hakeem on 9/10/17.
 */

public class Book {
    

    private String bookTitle;
    //private String[] authorsNames;
    private ArrayList<String> authorsNames;
    private String publisher;
    private String publishDate;
    private String bookLanguage;
    private String price;
    private boolean availablePDF;
    private String previewLink;
    private String coverImagePhotoLink;

    public Book(String bookTitle, ArrayList<String> authorsNames, String publisher, String publishDate, String bookLanguage, String price, boolean availablePDF, String previewLink, String coverImagePhotoLink) {
        this.bookTitle = bookTitle;
        this.authorsNames = authorsNames;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.bookLanguage = bookLanguage;
        this.price = price;
        this.availablePDF = availablePDF;
        this.previewLink = previewLink;
        this.coverImagePhotoLink = coverImagePhotoLink;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public void setauthorsNames(ArrayList<String> authorsNames) {
        this.authorsNames = authorsNames;
    }

  

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    

    public void setAvailablePDF(boolean availablePDF) {
        this.availablePDF = availablePDF;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public void setCoverImagePhotoLink(String coverImagePhotoLink) {
        this.coverImagePhotoLink = coverImagePhotoLink;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public ArrayList<String> getAuthorsNames() {
        return authorsNames;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public String getPrice() {
        return price;
    }

    public String getCoverImagePhotoLink() {
        return coverImagePhotoLink;
    }

    public boolean isAvailablePDF() {
        return availablePDF;
    }

    public String getPreviewLink() {
        return previewLink;
    }
}

