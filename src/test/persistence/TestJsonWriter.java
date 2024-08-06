package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonWriter extends TestJson {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Test User");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was egixpected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("Test User");
            JsonWriter writer = new JsonWriter("./data/tests/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/tests/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals("Test User", user.getUsername());
            assertEquals(0, user.getCrzyShareAmount());
            assertEquals(10000, user.getBalance());
            assertEquals(0, user.numOrders());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("John");
            Stock stock = new CrzyStock(10);
            Order order1 = new Order(stock, "CRZY", 10, 20, true);
            Order order2 = new Order(stock, "CRZY", 10, 15, false);

            user.addToOrderHistory(order1);
            user.addToOrderHistory(order2);
            JsonWriter writer = new JsonWriter("./data/tests/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/tests/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("John", user.getUsername());
            List<Order> order = user.getOrderHistory();
            assertEquals(2, order.size());
            checkOrder(stock, 20, true, order.get(0));
            checkOrder(stock, 15, false, order.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}