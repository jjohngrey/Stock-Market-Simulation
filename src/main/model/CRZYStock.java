package model;

import java.util.Random;

public class CrzyStock extends Stock {

    // REQUIRES: The price must be greater than or equal to 0. The ticker must have
    // only 4 letters and are capitalized.
    // MODIFIES: this
    // EFFECTS: Creates a stock with a ticker symbol and price
    public CrzyStock(int price) {
        super("CRZY", price);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Updates the stock price by a random amount
    public int updateStockPrice() { 
        Random r = new Random(); 
        int price = super.getPrice();
        int amount = 0;
        amount = r.nextInt(6) + 1;
        int random = Math.random() >= 0.5 ? +1 : -1;
        price += amount * random;
        if (price < 1) {
            price = 1;
            return 1;
        } else {
            return price;
        }
    } 
}
