package ui.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Order;
import model.Stock;
import model.User;
import model.Market;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class CrzyPanel extends JPanel implements ActionListener {

    private JLabel buyCrzyLabel;
    private JTextField buyCrzyField;

    private JLabel sellCrzyLabel;
    private JTextField sellCrzyField;

    private Market market;
    private UserPanel userPanel;
    private HistoryPanel historyPanel;
    private User user;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    // updates this with the game whose score is to be displayed
    public CrzyPanel(Market market, UserPanel mp, HistoryPanel hp, User user) {
        this.market = market;
        this.userPanel = mp;
        this.historyPanel = hp;
        this.user = user;

        setBackground(new Color(180, 180, 180));
        setBounds(250, 250, 250, 250);

        JButton btnBuyCrzy = new JButton("+");
        btnBuyCrzy.setActionCommand("Buy CRZY");
        btnBuyCrzy.addActionListener(this);
        buyCrzyLabel = new JLabel("Buy CRZY: ");
        buyCrzyField = new JTextField(5);
        add(buyCrzyLabel);
        add(buyCrzyField);
        add(btnBuyCrzy);

        JButton btnSellCrzy = new JButton("-");
        btnSellCrzy.setActionCommand("Sell CRZY");
        btnSellCrzy.addActionListener(this);
        sellCrzyLabel = new JLabel("Sell CRZY: ");
        sellCrzyField = new JTextField(5);
        add(sellCrzyLabel);
        add(sellCrzyField);
        add(btnSellCrzy);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buy CRZY")) {
            buyCrzyStock();
            userPanel.checkBalance();
            userPanel.checkCrzyShares();
            buyCrzyField.setText("");
            historyPanel.produceHistory(user);
        } else if (e.getActionCommand().equals("Sell CRZY")) {
            sellCrzyStock();
            userPanel.checkBalance();
            userPanel.checkCrzyShares();
            sellCrzyField.setText("");
            historyPanel.produceHistory(user);
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough money to buy, if they do, buys shares
    // and adds to order history
    // otherwise, does nothing
    public void buyCrzyStock() {
        Stock selected = market.getCrzyStock();
        int volume = Integer.valueOf(buyCrzyField.getText());
        String ticker = selected.getTicker();
        int price = selected.getPrice();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getBalance() >= totalCost) { // if funds are sufficient
                user.decreaseBalance(totalCost); // incur costs
                user.increaseCrzyShares(volume); // receive shares
                Order order = new Order(selected, ticker, price, volume, true); // create new order
                user.addToOrderHistory(order); // add to orderHistory
                System.out.println("You have bought " + volume + " shares of "
                        + market.getCrzyStock().getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have sufficient funds.");
            }
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough shares to sell, if they do, sells shares
    // and adds to order history
    // otherwise, does nothing
    public void sellCrzyStock() {
        Stock selected = market.getCrzyStock();
        System.out.print("Enter number of shares to sell: ");
        int volume = Integer.valueOf(sellCrzyField.getText());
        int price = selected.getPrice();
        String ticker = selected.getTicker();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getCrzyShareAmount() >= volume) { // if share amount are sufficient
                user.increaseBalance(totalCost); // receive money
                user.decreaseCrzyShares(volume); // drop shares
                Order order = new Order(selected, ticker, price, volume, false); // create new order
                user.addToOrderHistory(order); // add to orderHistory
                System.out.println("You have sold " + volume + " shares of "
                        + market.getCrzyStock().getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have that many shares to sell.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the user to the loaded user
    public void setUser(User user) {
        this.user = user;
    }
}
