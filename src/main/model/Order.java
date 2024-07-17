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

    // EFFECTS: Prints order
    public void printOrder(Order order) {
        System.out.println("Ticker name: " + ticker);
        int cost = price * shares;
        if (orderType) {
            System.out.println("Cost: -$" + cost);
            System.out.println("Volume: +" + shares + " shares");
        } else {
            System.out.println("Cost: +$" + cost);
            System.out.println("Volume: -" + shares + " shares");
        }
        System.out.println("Price: $" + price);
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

