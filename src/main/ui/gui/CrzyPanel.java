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
import javax.swing.JOptionPane;
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
public class CrzyPanel extends JPanel implements ActionListener {

    private JLabel buyCrzyLabel;
    private JTextField buyCrzyField;

    private JLabel sellCrzyLabel;
    private JTextField sellCrzyField;

    private Market market;
    private UserPanel userPanel;
    private HistoryPanel historyPanel;
    private User user;

    // Constructs a crzy stock panel
    // EFFECTS: sets the background colour and draws the initial labels
    // updates with the share amounts and price
    public CrzyPanel(Market market, UserPanel up, HistoryPanel hp, User user) {
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

        JLabel header = new JLabel("CRZY Stock");
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(Color.decode("#222222"));
        add(header, gbc);

        gbc.gridy++;
        JPanel buyPanel = new JPanel(new GridLayout(1,2,8,0));
        buyPanel.setBackground(Color.WHITE);
        buyCrzyField = new JTextField(5);
        buyCrzyField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnBuyCrzy = new JButton("Buy");
        styleButton(btnBuyCrzy);
        btnBuyCrzy.setActionCommand("Buy CRZY");
        btnBuyCrzy.addActionListener(this);
        buyPanel.add(buyCrzyField);
        buyPanel.add(btnBuyCrzy);
        add(buyPanel, gbc);

        gbc.gridy++;
        JPanel sellPanel = new JPanel(new GridLayout(1,2,8,0));
        sellPanel.setBackground(Color.WHITE);
        sellCrzyField = new JTextField(5);
        sellCrzyField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnSellCrzy = new JButton("Sell");
        styleButton(btnSellCrzy);
        btnSellCrzy.setActionCommand("Sell CRZY");
        btnSellCrzy.addActionListener(this);
        sellPanel.add(sellCrzyField);
        sellPanel.add(btnSellCrzy);
        add(sellPanel, gbc);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buy CRZY")) {
            buyCrzyStock();
            userPanel.checkBalance();
            userPanel.checkCrzyShares();
            buyCrzyField.setText("");
            historyPanel.produceHistory(user);
            historyPanel.updateGraph();
        } else if (e.getActionCommand().equals("Sell CRZY")) {
            sellCrzyStock();
            userPanel.checkBalance();
            userPanel.checkCrzyShares();
            sellCrzyField.setText("");
            historyPanel.produceHistory(user);
            historyPanel.updateGraph();
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
                JOptionPane.showMessageDialog(this,
                        "You have bought " + volume + " shares of " + market.getCrzyStock().getTicker() + " at $" + price + " price.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You do not have sufficient funds.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough shares to sell, if they do, sells shares
    // and adds to order history
    // otherwise, does nothing
    public void sellCrzyStock() {
        Stock selected = market.getCrzyStock();
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
                JOptionPane.showMessageDialog(this,
                        "You have sold " + volume + " shares of " + market.getCrzyStock().getTicker() + " at $" + price + " price.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You do not have that many shares to sell.",
                        "Error", JOptionPane.ERROR_MESSAGE);
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
