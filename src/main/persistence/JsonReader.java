package persistence;

import model.Order;
import model.Stock;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads user from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        User user = new User(username);
        addShares(user, jsonObject);
        addBalance(user, jsonObject);
        addOrderHistory(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses shares from JSON object and sets it for the user
    private void addShares(User user, JSONObject jsonObject) {
        int shares = jsonObject.getInt("shares");
        user.setShares(shares);
    }

    // MODIFIES: user
    // EFFECTS: parses balance from JSON object and sets it for the user
    private void addBalance(User user, JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        user.setBalance(balance);
    }

    // MODIFIES: user
    // EFFECTS: parses orderHistory from JSON object and adds them to yser
    private void addOrderHistory(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("orderHistory");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            addOrder(user, nextOrder);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses order from JSON object and adds it to user
    private void addOrder(User user, JSONObject jsonObject) {
        String ticker = jsonObject.getString("ticker");
        int price = jsonObject.getInt("price");
        Stock stock = new Stock(ticker, price);
        int shares = jsonObject.getInt("shares");
        boolean orderType = jsonObject.getBoolean("orderType");
        Order order = new Order(stock, shares, orderType);
        user.addToOrderHistory(order);
    }
}
