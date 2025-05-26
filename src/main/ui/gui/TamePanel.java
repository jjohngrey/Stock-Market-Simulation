package ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import model.Market;
import model.Order;
import model.Stock;
import model.User;

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

        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220,220,220), 2, true));
        setPreferredSize(new Dimension(200, 140));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel header = new JLabel("TAME Stock");
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(Color.decode("#222222"));
        add(header, gbc);

        gbc.gridy++;
        JPanel buyPanel = new JPanel(new GridLayout(1,2,8,0));
        buyPanel.setBackground(Color.WHITE);
        buyTameField = new JTextField(5);
        buyTameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnBuyTame = new JButton("Buy");
        styleButton(btnBuyTame);
        btnBuyTame.setActionCommand("Buy TAME");
        btnBuyTame.addActionListener(this);
        buyPanel.add(buyTameField);
        buyPanel.add(btnBuyTame);
        add(buyPanel, gbc);

        gbc.gridy++;
        JPanel sellPanel = new JPanel(new GridLayout(1,2,8,0));
        sellPanel.setBackground(Color.WHITE);
        sellTameField = new JTextField(5);
        sellTameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnSellTame = new JButton("Sell");
        styleButton(btnSellTame);
        btnSellTame.setActionCommand("Sell TAME");
        btnSellTame.addActionListener(this);
        sellPanel.add(sellTameField);
        sellPanel.add(btnSellTame);
        add(sellPanel, gbc);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buy TAME")) {
            buyTameStock();
            userPanel.checkBalance();
            userPanel.checkTameShares();
            buyTameField.setText("");
            historyPanel.produceHistory(user);
            historyPanel.updateGraph();
        } else if (e.getActionCommand().equals("Sell TAME")) {
            sellTameStock();
            userPanel.checkBalance();
            userPanel.checkTameShares();
            sellTameField.setText("");
            historyPanel.produceHistory(user);
            historyPanel.updateGraph();
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

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(Color.WHITE);
        btn.setBorder(new LineBorder(new Color(200,200,200), 1));
        btn.setFocusPainted(false);
    }
}
