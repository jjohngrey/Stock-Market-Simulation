package model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStock {
    private Stock testStock;
    private Stock testStock2;
    
    @BeforeEach
    void runBefore() {
        testStock = new CrzyStock(5);
        testStock2 = new TameStock(5);
    }

    @Test
    void testConstructor() {
        assertEquals("CRZY", testStock.getTicker());
        assertEquals(5, testStock.getPrice());
        assertEquals("TAME", testStock2.getTicker());
        assertEquals(5, testStock2.getPrice());
    }

    @Test
    // Tests if new stock price is different
    void testUpdateStockPrice() {          
        int currentPrice = testStock.getPrice();
        int futurePrice = testStock.updateStockPrice();
        int difference = currentPrice - futurePrice;
        assertNotEquals(difference, 0);

        int currentPrice2 = testStock2.getPrice();
        int futurePrice2 = testStock2.updateStockPrice();
        int difference2 = currentPrice2 - futurePrice2;
        assertNotEquals(difference2, 0);
    }
}
