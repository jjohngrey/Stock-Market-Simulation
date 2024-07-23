package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJson {
    protected void checkOrder(Stock stock, int shares, boolean orderType, Order order) {
        assertEquals(stock.getTicker(), order.getStock().getTicker());
        assertEquals(stock.getPrice(), order.getStock().getPrice());
        assertEquals(shares, order.getShares());
        assertEquals(orderType, order.getOrderType());
    }
}
