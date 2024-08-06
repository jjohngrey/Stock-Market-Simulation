package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMarket {
    private Market market;
    
    @BeforeEach
    void runBefore() {
        market = new Market();
    }

    @Test
    void testConstructor() {
        assertEquals(market.getCrzyStock().getTicker(), "CRZY");
        assertEquals(market.getCrzyStock().getPrice(), 20);
        assertEquals(market.getTameStock().getTicker(), "TAME");
        assertEquals(market.getTameStock().getPrice(), 10);
        assertEquals(market.getUser().getUsername(), "John");
    }
}
