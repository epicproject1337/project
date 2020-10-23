package com.example.boket.ui.bookSeller;

/**
 * @author Tarik Porobic
 * Class that represent a bookseller for a specific book
 * @since 2020-09-17
 */
public class ABookSeller {

    private String state, price, city;
    private String sellerEmail;
    private String bookSold;

    /**
     * @param bookSold the name of the book being sold
     * @param seller   the seller of the book
     * @param state    the state of the book
     * @param price    the price of the book
     * @param city     the city where the book is being sold
     */
    public ABookSeller(String bookSold, String seller, String state, String price, String city) {
        this.state = state;
        this.price = price;
        this.city = city;
        this.sellerEmail = seller;
        this.bookSold = bookSold;
    }

    /**
     * Returns the value so that the holder can present this information
     *
     * @return state of the book
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the value so that the holder can present this information
     *
     * @return price of the book
     */
    public String getPrice() {
        return price;
    }

    /**
     * Returns the value so that the holder can present this information
     *
     * @return the city where the book is sold at
     */
    public String getCity() {
        return city;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getBookSold() {
        return bookSold;
    }
}
