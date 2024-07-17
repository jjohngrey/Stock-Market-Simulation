package ui;

import model.User;
import model.Order;
import model.Stock;

import java.util.Scanner;
import java.util.List;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.SECONDS;

// Stock simulation
public class App {
    private User user;
    private Stock stock;
    private Scanner input;

    // EFFECTS: runs the simulation application
    public App() {
        runMarket();
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        user = new User("John");
        stock = new Stock("CRZY", 10);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMarket() {
        boolean keepGoing = true;
        String command = null;

        init();

        LocalTime previousTime = LocalTime.now();
        while (keepGoing) {
            displayMenu();
            previousTime = updateStock(previousTime);
            command = input.next();
            command = command.toLowerCase();
            

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("b")) {
            buyStock();
        } else if (command.equals("s")) {
            sellStock();
        } else if (command.equals("h")) {
            viewHistory();
        } else if (command.equals("c")) {
            checkBalance();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> buy");
        System.out.println("\ts -> sell");
        System.out.println("\th -> view order history");
        System.out.println("\tc -> check balance");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough money to buy, if they do, buys shares
    //          and adds to order history
    //          otherwise, does nothing
    public void buyStock() {
        Stock selected = stock;
        System.out.print("Enter number of shares to buy: ");
        int volume = input.nextInt();
        int price = selected.getPrice();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getBalance() >= totalCost) {        // if funds are sufficient
                user.decreaseBalance(totalCost);
                user.increaseShares(volume);
                Order order = new Order(selected, volume, true);
                user.addToOrderHistory(order);
                System.out.println("You have bought " + volume + " shares of " + 
                    stock.getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have sufficient funds.");
            }
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough shares to sell, if they do, sells shares
    //          and adds to order history
    //          otherwise, does nothing
    public void sellStock() {
        Stock selected = stock;
        System.out.print("Enter number of shares to sell: ");
        int volume = input.nextInt();
        int price = selected.getPrice();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getShareAmount() >= volume) {        // if share amount are sufficient
                user.increaseBalance(totalCost);
                user.decreaseShares(volume);
                Order order = new Order(selected, volume, false);
                user.addToOrderHistory(order);
                System.out.println("You have sold " + volume + " shares of " + 
                    stock.getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have that many shares to sell.");
            }
        }
    }

    // EFFECTS: For order in order history, prints the order
    public void viewHistory() {
        List<Order> history = user.getOrderHistory();
        for (int i = 0; i < history.size(); i++) {
            Order order = history.get(i);
            printOrder(order);
        }
    }

    // EFFECTS: Prints user current balance
    public void checkBalance() {
        System.out.println("You have $" + user.getBalance() + "."); 
    }

    // EFFECTS: Updates stock price on the UI by checking previous time and current time
    public LocalTime updateStock(LocalTime previousTime) {
        LocalTime currentTime = LocalTime.now();
        long difference = SECONDS.between(previousTime, currentTime);

        if (difference > 5) {
            int newPrice = stock.updateStockPrice();
            System.out.println("CRZY is now $" + newPrice);
            return currentTime;
        } else {
            return previousTime;
        }
    }

    // EFFECTS: Prints order
    public void printOrder(Order order) {
        Stock s = order.getStock();
        System.out.println("Ticker name: " + s.getTicker());
        int cost = s.getPrice() * order.getShares();
        if (order.getOrderType()) {
            System.out.println("Cost: -$" + cost);
            System.out.println("Volume: +" + order.getShares() + " shares");
        } else {
            System.out.println("Cost: +$" + cost);
            System.out.println("Volume: -" + order.getShares() + " shares");
        }
        System.out.println("Price: $" + s.getPrice());
    }
}