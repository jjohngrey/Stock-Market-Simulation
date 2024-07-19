package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestOrder {
    private Order testOrder1;
    private Order testOrder2;
    private Stock testStock;
    
    @BeforeEach
    void runBefore() {
        testStock = new Stock("CRZY", 5);
        testOrder1 = new Order(testStock, 500, true);
        testOrder2 = new Order(testStock, 400, false);
    }

    @Test
    void testConstructor() {
        assertEquals(testStock, testOrder1.getStock());
        Stock stock = testOrder1.getStock();
        assertEquals("CRZY", stock.getTicker());
        assertEquals(5, stock.getPrice());
        assertEquals(500, testOrder1.getShares());
        assertTrue(testOrder1.getOrderType());
        assertFalse(testOrder2.getOrderType());
    }
}
