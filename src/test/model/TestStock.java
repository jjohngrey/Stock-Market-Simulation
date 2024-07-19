package model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStock {
    private Stock testStock;
    
    @BeforeEach
    void runBefore() {
        testStock = new Stock("CRZY", 5);
    }

    @Test
    void testConstructor() {
        assertEquals("CRZY", testStock.getTicker());
        assertEquals(5, testStock.getPrice());
    }

    @Test
    // Tests if new stock price is different
    void testUpdateStockPrice() {          
        int currentPrice = testStock.getPrice();
        int futurePrice = testStock.updateStockPrice();
        int difference = currentPrice - futurePrice;
        assertNotEquals(difference, 0);
        assertTrue(difference == 1 || difference == -1);
    }
}
