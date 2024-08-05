package model;

public class Market {
    private User user;
    private Stock stock;
    
    // Constructs a market
	// EFFECTS:  creates a user with given name and stock with given stock
    public Market() {
        user = new User("John");
        stock = new Stock("CRZY", 10);
    }

    public User getUser() {
        return user;
    }

    public Stock getStock() {
        return stock;
    }


}
