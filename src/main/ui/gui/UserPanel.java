package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Market;
import model.User;

import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class UserPanel extends JPanel implements ActionListener {

    private static final String JSON_STORE = "./data/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JLabel stockPriceLabel;
    private JLabel balanceLabel;
    private JLabel shareLabel;
    private JLabel iconImage;

    private Market market;
    private HistoryPanel historyPanel;
    private BuyPanel buyPanel;
    private SellPanel sellPanel;
    private User user;
    

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    // updates this with the game whose score is to be displayed
    public UserPanel(Market market, HistoryPanel hp, BuyPanel bp, SellPanel sp) {
        this.market = market;
        this.historyPanel = hp;
        // this.buyPanel = bp;
        // this.sellPanel = sp;
        this.user = market.getUser();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setBackground(new Color(180, 180, 180));

        JButton btnSave = new JButton("Save");
        JButton btnLoad = new JButton("Load");
        btnSave.setActionCommand("Save");
        btnLoad.setActionCommand("Load");
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);
        // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click

        balanceLabel = new JLabel("Current Balance: $" + user.getBalance());
        add(balanceLabel);
        stockPriceLabel = new JLabel("CRZY Price: $" + market.getStock().getPrice());
        updatePrice();
        add(stockPriceLabel);
        shareLabel = new JLabel("CRZY Shares: " + user.getShareAmount());
        add(shareLabel);

        add(makeImage(), BorderLayout.CENTER);
        add(btnSave);
        add(btnLoad);
    }

    public JScrollPane makeImage() {
        String name = "/Users/johngrey/Downloads/CPSC 210/Personal Project/ProjectStarter/src/main/ui/images/icon.png";
        ImageIcon image = new ImageIcon(name);
        JLabel imageLabel = new JLabel(image);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveUser();
        } else if (e.getActionCommand().equals("Load")) {
            loadUser();
            checkBalance();
            checkShares();
            historyPanel.setUser(user);
            historyPanel.produceHistory(user);
        } else if (e.getActionCommand().equals("Quit")) {
            // idk
        }
    }

    // EFFECTS: Prints user current balance
    public void checkBalance() {
        balanceLabel.setText("Current Balance: $" + user.getBalance());
    }

    // EFFECTS: Prints user owned shares
    public void checkShares() {
        shareLabel.setText("CRZY Shares: " + user.getShareAmount());
    }

    // EFFECTS: updates stock price on UI
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

    // EFFECTS: saves the user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved " + user.getUsername() + " from " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + user.getUsername() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
