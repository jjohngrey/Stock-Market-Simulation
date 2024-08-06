package model;

public class Market {
    private User user;
    private Stock crzyStock;
    private Stock tameStock;
    
    // Constructs a market
    // MODIFIES: this
	// EFFECTS:  creates a user with given name and stock with given stock
    public Market() {
        user = new User("John");
        crzyStock = new CrzyStock(20);
        tameStock = new TameStock(10);
    }

    // get methods
    public User getUser() {
        return user;
    }

    public Stock getCrzyStock() {
        return crzyStock;
    }

    public Stock getTameStock() {
        return tameStock;
    }
}
