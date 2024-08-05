package model;

public class Market {
    private User user;
    private Stock crzyStock;
    private Stock tameStock;
    
    // Constructs a market
	// EFFECTS:  creates a user with given name and stock with given stock
    public Market() {
        user = new User("John");
        crzyStock = new CRZYStock(20);
        tameStock = new TAMEStock(10);
    }

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
