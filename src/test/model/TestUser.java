package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {
    private User testUser;
    private Order testOrder1;
    private Stock testStock;
    
    
    @BeforeEach
    void runBefore() {
        testUser = new User("John");
        testStock = new CrzyStock(5);
        testOrder1 = new Order(testStock, "CRZY", 5, 500, true);

    }

    @Test
    void testConstructor() {
        assertEquals("John", testUser.getUsername());
        assertEquals(10000, testUser.getBalance());
        assertEquals(0, testUser.getCrzyShareAmount());
        assertEquals(0, testUser.numOrders());
    }

    @Test
    // Tests if balance increases
    void testIncreaseBalance() {        
        testUser.increaseBalance(200);
        assertEquals(10200, testUser.getBalance());
    }

    @Test
    // Tests if balance decreases
    void testDecreaseBalance() {        
        testUser.decreaseBalance(200);
        assertEquals(9800, testUser.getBalance());
    }

    @Test
    // Tests if share increases
    void testIncreaseShares() {        
        testUser.increaseCrzyShares(200);
        assertEquals(200, testUser.getCrzyShareAmount());
        testUser.increaseTameShares(200);
        assertEquals(200, testUser.getTameShareAmount());
    }

    @Test
    // Tests if share decreases
    void testDecreaseShares() {        
        testUser.increaseCrzyShares(200);
        testUser.decreaseCrzyShares(190);
        assertEquals(10, testUser.getCrzyShareAmount());
        testUser.increaseTameShares(200);
        testUser.decreaseTameShares(190);
        assertEquals(10, testUser.getTameShareAmount());
    }

    @Test
    // Tests if share decreases
    void testAddToOrderHistory() {        
        List<Order> orderHistory = testUser.getOrderHistory();
        testUser.addToOrderHistory(testOrder1);
        assertEquals(testOrder1, orderHistory.get(0));
        assertEquals(1, testUser.numOrders());
    }
}
