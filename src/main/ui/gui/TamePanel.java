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
public class TamePanel extends JPanel implements ActionListener {

    private JLabel buyTameLabel;
    private JTextField buyTameField;

    private JLabel sellTameLabel;
    private JTextField sellTameField;

    private HistoryPanel historyPanel;
    private UserPanel userPanel;
    private Market market;
    private User user;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    // updates this with the game whose score is to be displayed
    public TamePanel(Market market, UserPanel up, HistoryPanel hp, User user) {
        this.market = market;
        this.userPanel = up;
        this.historyPanel = hp;
        this.user = user;

        setBackground(new Color(180, 180, 180));
        setBounds(250, 250, 250, 250);

        JButton btnBuyTame = new JButton("+");
        btnBuyTame.setActionCommand("Buy TAME");
        btnBuyTame.addActionListener(this);
        buyTameLabel = new JLabel("Buy TAME: ");
        buyTameField = new JTextField(5);
        add(buyTameLabel);
        add(buyTameField);
        add(btnBuyTame);

        JButton btnSellTame = new JButton("-");
        btnSellTame.setActionCommand("Sell TAME");
        btnSellTame.addActionListener(this);
        sellTameLabel = new JLabel("Sell TAME: ");
        sellTameField = new JTextField(5);
        add(sellTameLabel);
        add(sellTameField);
        add(btnSellTame);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buy TAME")) {
            buyTameStock();
            userPanel.checkBalance();
            userPanel.checkTameShares();
            buyTameField.setText("");
            historyPanel.produceHistory(user);
        } else if (e.getActionCommand().equals("Sell TAME")) {
            sellTameStock();
            userPanel.checkBalance();
            userPanel.checkTameShares();
            sellTameField.setText("");
            historyPanel.produceHistory(user);
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough money to buy, if they do, buys shares
    // and adds to order history
    // otherwise, does nothing
    public void buyTameStock() {
        Stock selected = market.getTameStock();
        int volume = Integer.valueOf(buyTameField.getText());
        String ticker = selected.getTicker();
        int price = selected.getPrice();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getBalance() >= totalCost) { // if funds are sufficient
                user.decreaseBalance(totalCost); // incur costs
                user.increaseTameShares(volume); // receive shares
                Order order = new Order(selected, ticker, price, volume, true); // create new order
                user.addToOrderHistory(order); // add to orderHistory
                System.out.println("You have bought " + volume + " shares of "
                        + market.getTameStock().getTicker() + " at $" + price + " price.");
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
    public void sellTameStock() {
        Stock selected = market.getTameStock();
        System.out.print("Enter number of shares to sell: ");
        int volume = Integer.valueOf(sellTameField.getText());
        int price = selected.getPrice();
        String ticker = selected.getTicker();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (user.getTameShareAmount() >= volume) { // if share amount are sufficient
                user.increaseBalance(totalCost); // receive money
                user.decreaseTameShares(volume); // drop shares
                Order order = new Order(selected, ticker, price, volume, false); // create new order
                user.addToOrderHistory(order); // add to orderHistory
                System.out.println("You have sold " + volume + " shares of "
                        + market.getTameStock().getTicker() + " at $" + price + " price.");
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

    // MODIFIES: this
    // EFFECTS: changes the history to the loaded history
    public void setHistory(HistoryPanel hp) {
        this.historyPanel = hp;
    }
}
