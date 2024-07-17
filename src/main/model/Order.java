package model;

// Represents an order on the market with
// a stock ticker symbol, current price, amount of shares, and buying or selling
public class Order {
    private Stock stock;
    private String ticker;                 // stock ticker
    private int price;                     // price at which the stock is bought/sold
    private int shares;                    // shares traded
    private boolean orderType;             // true if buy/long, false if sell/short

    // REQUIRES: Shares must be >= 0
    // MODIFIES: this
    // EFFECTS: Creates an order with given stock, shares and ordertype
    public Order(Stock stock, int shares, boolean orderType) {
        this.stock = stock;
        this.ticker = stock.getTicker();
        this.price = stock.getPrice();
        this.shares = shares;
        this.orderType = orderType;
    }

    // GET METHODS
    public Stock getStock() {
        return stock;
    }

    public int getShares() {
        return shares;
    }

    public boolean getOrderType() {
        return orderType;
    }

}

