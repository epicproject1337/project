package com.example.boket.ui.search;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.boket.R;

/**
 * @author Oscar Bennet
 *
 * Item in recycler view representing a book
 *
 * @since 2020-09-21
 */
public class BookItem extends View {
    private String isbn;
    private String bookTitle;
    private String author;
    private String edition;
    private String publishedYear;
    private String image;

    public BookItem(Context context) {
        super(context);
        init(null, 0);
    }

    public BookItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BookItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    public BookItem(Context context, String isbn, String title, String author, String edition, String publishedYear, String image){
        super(context);
        init(null, 0);
        this.isbn = isbn;
        this.bookTitle = title;
        this.author = author;
        this.edition = edition;
        this.publishedYear = publishedYear;
        this.image = image;

    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BookItem, defStyle, 0);


        a.recycle();

    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String title) {
        bookTitle = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
