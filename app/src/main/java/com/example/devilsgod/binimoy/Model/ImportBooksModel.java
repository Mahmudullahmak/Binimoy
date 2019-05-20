package com.example.devilsgod.binimoy.Model;

public class ImportBooksModel {
    private String bookName;
    private String bookUrl;

    public ImportBooksModel() {
    }

    public ImportBooksModel(String bookName, String bookUrl) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
