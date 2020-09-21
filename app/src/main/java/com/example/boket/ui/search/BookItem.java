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
 * TODO: document your custom view class.
 */
public class BookItem extends View {
    private String text = "bruh";


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


    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BookItem, defStyle, 0);

        /*
        mExampleString = a.getString(
                R.styleable.BookItem_exampleString);
        mExampleColor = a.getColor(
                R.styleable.BookItem_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.BookItem_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.BookItem_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.BookItem_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        */


        a.recycle();

    }



    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setText(String exampleString) {
        text = exampleString;

    }

    

}
