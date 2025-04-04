package ui.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Event;
import model.EventLog;
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

    private JLabel balanceLabel;

    private JLabel crzyStockPriceLabel;
    private JLabel crzyShareLabel;

    private JLabel tameStockPriceLabel;
    private JLabel tameShareLabel;

    private Market market;
    private HistoryPanel historyPanel;
    private CrzyPanel crzyPanel;
    private TamePanel tamePanel;
    private User user;
    
    private Font p;
    private Font strong;
    

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    // updates this with the game whose score is to be displayed
    public UserPanel(Market market, HistoryPanel hp, CrzyPanel cp, TamePanel tp) {
        this.market = market;
        this.historyPanel = hp;
        this.crzyPanel = cp;
        this.tamePanel = tp;
        this.user = market.getUser();
        this.p = new Font("Tahoma", Font.PLAIN, 14);
        this.strong = new Font("Tahoma", Font.BOLD, 14);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setBackground(new Color(27,38,59));

        JButton btnSave = new JButton("Save Current Game");
        JButton btnLoad = new JButton("Load Previous Game");
        btnSave.setActionCommand("Save");
        btnLoad.setActionCommand("Load");
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);
        btnSave.setFont(strong);
        btnLoad.setFont(strong);

        balanceLabel = new JLabel("Current Balance: $" + user.getBalance() + "   ");
        balanceLabel.setFont(strong);
        balanceLabel.setForeground(Color.WHITE);
        add(balanceLabel);
        
        addCrzyLabel();
        addTameLabel();

        add(btnSave);
        add(btnLoad);
    }

    // MODIFIES: this
    // EFFECTS: sets crzy panel to give crzy panel
    public void setCrzyPanel(CrzyPanel cp) {
        this.crzyPanel = cp;
    }

    // MODIFIES: this
    // EFFECTS: sets tame panel to give tame panel
    public void setTamePanel(TamePanel tp) {
        this.tamePanel = tp;
    }

    // EFFECTS: adds crzy details
    public void addCrzyLabel() {
        crzyStockPriceLabel = new JLabel("CRZY Price: $" + market.getCrzyStock().getPrice() + "   ");
        updateStockPrice();
        crzyStockPriceLabel.setFont(p);
        crzyStockPriceLabel.setForeground(Color.WHITE);
        add(crzyStockPriceLabel);
        crzyShareLabel = new JLabel("   CRZY Shares: " + user.getCrzyShareAmount() + "   ");
        crzyShareLabel.setFont(strong);
        crzyShareLabel.setForeground(Color.WHITE);
        add(crzyShareLabel);
    }

    // EFFECTS: adds tame details
    public void addTameLabel() {
        tameStockPriceLabel = new JLabel("TAME Price: $" + market.getTameStock().getPrice() + "   ");
        updateStockPrice();
        tameStockPriceLabel.setFont(strong);
        tameStockPriceLabel.setForeground(Color.WHITE);
        add(tameStockPriceLabel);
        tameShareLabel = new JLabel("   TAME Shares: " + user.getTameShareAmount());
        tameShareLabel.setFont(strong);
        tameShareLabel.setForeground(Color.WHITE);
        add(tameShareLabel);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveUser();
            EventLog.getInstance().logEvent(new Event("File has been saved"));
        } else if (e.getActionCommand().equals("Load")) {
            loadUser();
            checkBalance();
            checkCrzyShares();
            checkTameShares();
            crzyPanel.setUser(user);
            tamePanel.setUser(user);
            historyPanel.setUser(user);
            historyPanel.produceHistory(user);
            EventLog.getInstance().logEvent(new Event("File has been loaded"));
        }
    }

    // EFFECTS: Prints user current balance
    public void checkBalance() {
        balanceLabel.setText("Current Balance: $" + user.getBalance());
    }

    // EFFECTS: Prints user owned shares of crzy
    public void checkCrzyShares() {
        crzyShareLabel.setText("CRZY Shares: " + user.getCrzyShareAmount());
    }

    // EFFECTS: Prints user owned shares of tame
    public void checkTameShares() {
        tameShareLabel.setText("TAME Shares: " + user.getTameShareAmount());
    }

    // EFFECTS: updates crzy stock price on UI
    public void updateStockPrice() {
        int delay = 1500; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                market.getCrzyStock().updateStockPrice();
                crzyStockPriceLabel.setText("CRZY Price: $" + market.getCrzyStock().getPrice());
                crzyStockPriceLabel.setFont(strong);
                market.getTameStock().updateStockPrice();
                tameStockPriceLabel.setText("TAME Price: $" + market.getTameStock().getPrice());
                tameStockPriceLabel.setFont(strong);
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
