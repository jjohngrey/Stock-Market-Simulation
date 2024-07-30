package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an order on the market with
// a stock ticker symbol, current price, amount of shares, and buying or selling
public class Order implements Writable {
    private Stock stock;
    private String ticker;                 // stock ticker
    private int price;                     // price at which the stock is bought/sold
    private int shares;                    // shares traded
    private boolean orderType;             // true if buy/long, false if sell/short

    // REQUIRES: Shares must be >= 0
    // MODIFIES: this
    // EFFECTS: Creates an order with given stock, shares and ordertype
    public Order(Stock stock, String ticker, int price, int shares, boolean orderType) {
        this.stock = stock;
        this.ticker = ticker;
        this.price = price;
        this.shares = shares;
        this.orderType = orderType;
    }

    // GET METHODS
    public Stock getStock() {
        return stock;
    }

    public String getTicker() {
        return ticker;
    }

    public int getPrice() {
        return price;
    }

    public int getShares() {
        return shares;
    }

    public boolean getOrderType() {
        return orderType;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stock", stock);
        json.put("ticker", ticker);
        json.put("price", price);
        json.put("shares", shares);
        json.put("orderType", orderType);
        return json;
    }
}

