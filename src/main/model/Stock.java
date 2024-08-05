package model;

import java.util.Random;

// Represents a stock on the market with
// a ticker symbol and price name
public class Stock {
    private String ticker;      // ticker symbol
    private int price;          // stock price
    

    // REQUIRES: The price must be greater than or equal to 0. The ticker must have only 4 letters and are capitalized.
    // MODIFIES: this
    // EFFECTS: Creates a stock with a ticker symbol and price
    public Stock(String ticker, int price) {
        this.ticker = ticker;
        this.price = price;
    }

    // MODIFIES: this
    // EFFECTS: Updates the stock price by a random amount
    public int updateStockPrice() { 
        Random r = new Random(); 
        price = getPrice();
        int amount = 0;
        amount = r.nextInt(3) + 1;
        int random = Math.random() >= 0.5 ? +1 : -1;
        price += amount * random;
        if (price < 0) {
            return 0;
        }
        return price;
    } 

    // GET METHODS
    public String getTicker() {
        return ticker;
    }

    public int getPrice() {
        return price;
    }

}

