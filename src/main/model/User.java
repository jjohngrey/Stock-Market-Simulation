package model;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents an order on the market with
// a stock ticker symbol, current price, amount of shares, and buying or selling
public class User implements Writable {
    private String username;
    private int balance;

    private int shares;
    private List<Stock> ownedStocks; // used for multiple stocks... not implemented yet
    private List<Order> orderHistory;

    // MODIFIES: this
    // EFFECTS: Creates a username with given name and a starting balance of $10,000
    public User(String username) {
        this.username = username;
        this.balance = 10000;
        this.shares = 0;
        this.ownedStocks = new ArrayList<Stock>();
        this.orderHistory = new ArrayList<Order>();
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets balance to amount
    public void setBalance(int amount) {
        this.balance = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Increases balance by the given amount
    public int increaseBalance(int amount) {
        return balance += amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Decreases balance by the given amount
    public int decreaseBalance(int amount) {
        return balance -= amount;
    }

    // REQUIRES: shares >= 0
    // MODIFIES: this
    // EFFECTS: sets shares to share amount
    public void setShares(int shares) {
        this.shares = shares;
    }
    
    // REQUIRES: shares >= 0
    // MODIFIES: this
    // EFFECTS: Increases shares by the given shares
    public void increaseShares(int shares) {
        this.shares += shares;
    }
    
    // REQUIRES: shares >= 0
    // MODIFIES: this
    // EFFECTS: Decreases shares by the given shares
    public void decreaseShares(int shares) {
        this.shares -= shares;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given order to the list of orders committed by user
    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public int numOrders() {
        return orderHistory.size();
    }

    // GET METHODS
    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

    public int getShareAmount() {
        return shares;
    }
    
    public List<Stock> getOwnedStocks() {
        return ownedStocks;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("balance", balance);
        json.put("shares", shares);
        json.put("ownedStocks", ownedStocks);
        json.put("orderHistory", orderHistoryToJson());
        return json;
    }

    // EFFECTS: returns orderHistory in this user as a JSON array
    private JSONArray orderHistoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Order order : orderHistory) {
            jsonArray.put(order.toJson());
        }

        return jsonArray;
    }    
}

