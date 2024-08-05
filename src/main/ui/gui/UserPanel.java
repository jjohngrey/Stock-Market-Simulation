package ui.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Market;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class UserPanel extends JPanel implements ActionListener {

    private JLabel stockPriceLabel;
    private JLabel balanceLabel;
    private JLabel shareLabel;

    private Market market;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    // updates this with the game whose score is to be displayed
    public UserPanel(Market market) {
        this.market = market;

        setBackground(new Color(180, 180, 180));

        JButton btnSave = new JButton("Save");
        JButton btnLoad = new JButton("Load");
        JButton btnQuit = new JButton("Quit");
        btnSave.setActionCommand("Save");
        btnLoad.setActionCommand("Load");
        btnQuit.setActionCommand("Quit");
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);
        btnQuit.addActionListener(this);
        // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click

        balanceLabel = new JLabel("Current Balance: $" + market.getUser().getBalance());
        add(balanceLabel);
        stockPriceLabel = new JLabel("CRZY Price: $" + market.getStock().getPrice());
        updatePrice();
        add(stockPriceLabel);
        shareLabel = new JLabel("CRZY Shares: " + market.getUser().getShareAmount());
        add(shareLabel);

        add(btnSave);
        add(btnLoad);
        add(btnQuit);
    }

    // This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            // saveUser();
        } else if (e.getActionCommand().equals("Load")) {
            // loadUser();
        } else if (e.getActionCommand().equals("Quit")) {
            // idk
        }

    }

    // EFFECTS: Prints user current balance
    public void checkBalance() {
        balanceLabel.setText("Current Balance: $" + market.getUser().getBalance());
    }

    // EFFECTS: Prints user owned shares
    public void checkShares() {
        shareLabel.setText("CRZY Shares: " + market.getUser().getShareAmount());
    }

    public void updatePrice() {
        int delay = 1500; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                market.getStock().updateStockPrice();
                stockPriceLabel.setText("CRZY Price: $" + market.getStock().getPrice());
            }
        };
        new Timer(delay, taskPerformer).start();
    }
}
