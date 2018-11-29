package com.example.bean;

import java.io.Serializable;

public class Book implements Serializable {


    private static final long serialVersionUID = 1L;
    private String bookName;
    private String bookAutor;

    public Book() {
    }

    public Book(String bookName, String bookAutor) {
        this.bookName = bookName;
        this.bookAutor = bookAutor;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAutor() {
        return bookAutor;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAutor(String bookAutor) {
        this.bookAutor = bookAutor;
    }
}
