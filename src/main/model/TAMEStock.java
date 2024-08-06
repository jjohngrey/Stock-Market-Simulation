package model;

public class TameStock extends Stock {

    // REQUIRES: The price must be greater than or equal to 0. The ticker must have
    // only 4 letters and are capitalized.
    // MODIFIES: this
    // EFFECTS: Creates a stock with a ticker symbol and price
    public TameStock(int price) {
        super("TAME", price);
    }
}
