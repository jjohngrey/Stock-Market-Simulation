package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonReader extends TestJson {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/tests/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/tests/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("Test User", user.getUsername());
            assertEquals(0, user.numOrders());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/tests/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            Stock stock = new CrzyStock(10);
            assertEquals("John", user.getUsername());
            List<Order> order = user.getOrderHistory();
            assertEquals(2, order.size());
            checkOrder(stock, 20, true, order.get(0));
            checkOrder(stock, 15, false, order.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}