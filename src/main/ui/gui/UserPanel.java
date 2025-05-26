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
import javax.swing.border.LineBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

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
        this.p = new Font("Segoe UI", Font.PLAIN, 16);
        this.strong = new Font("Segoe UI", Font.BOLD, 22);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220,220,220), 2, true));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Balance panel (card, only balance label)
        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        balancePanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(224,224,224), 1, true),
            new EmptyBorder(28, 48, 28, 48)
        ));
        balancePanel.setLayout(new GridBagLayout());
        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.gridx = 0;
        bgbc.gridy = 0;
        bgbc.anchor = GridBagConstraints.WEST;
        balanceLabel = new JLabel("Current Balance: $" + user.getBalance());
        balanceLabel.setFont(strong);
        balanceLabel.setForeground(Color.decode("#222222"));
        balancePanel.add(balanceLabel, bgbc);
        add(balancePanel, gbc);

        // CRZY and TAME info panels (side by side, below balance)
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel stocksPanel = new JPanel(new GridLayout(1,2,16,0));
        stocksPanel.setBackground(Color.WHITE);
        JPanel crzyInfoPanel = new JPanel();
        crzyInfoPanel.setBackground(Color.WHITE);
        crzyInfoPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(224,224,224), 1, true),
            new EmptyBorder(28, 48, 28, 48)
        ));
        crzyInfoPanel.setLayout(new GridLayout(2,1,0,4));
        crzyStockPriceLabel = new JLabel("CRZY $" + market.getCrzyStock().getPrice());
        crzyStockPriceLabel.setFont(p);
        crzyStockPriceLabel.setForeground(Color.decode("#222222"));
        crzyShareLabel = new JLabel(user.getCrzyShareAmount() + " shares");
        crzyShareLabel.setFont(p);
        crzyShareLabel.setForeground(Color.decode("#222222"));
        crzyInfoPanel.add(crzyStockPriceLabel);
        crzyInfoPanel.add(crzyShareLabel);
        JPanel tameInfoPanel = new JPanel();
        tameInfoPanel.setBackground(Color.WHITE);
        tameInfoPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(224,224,224), 1, true),
            new EmptyBorder(28, 48, 28, 48)
        ));
        tameInfoPanel.setLayout(new GridLayout(2,1,0,4));
        tameStockPriceLabel = new JLabel("TAME $" + market.getTameStock().getPrice());
        tameStockPriceLabel.setFont(p);
        tameStockPriceLabel.setForeground(Color.decode("#222222"));
        tameShareLabel = new JLabel(user.getTameShareAmount() + " shares");
        tameShareLabel.setFont(p);
        tameShareLabel.setForeground(Color.decode("#222222"));
        tameInfoPanel.add(tameStockPriceLabel);
        tameInfoPanel.add(tameShareLabel);
        stocksPanel.add(crzyInfoPanel);
        stocksPanel.add(tameInfoPanel);
        gbc.gridx = 0;
        add(stocksPanel, gbc);

        // Save/Load panel (card, below stock cards)
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setBackground(Color.WHITE);
        saveLoadPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(224,224,224), 1, false),
            new EmptyBorder(20, 24, 20, 24)
        ));
        saveLoadPanel.setLayout(new GridLayout(1,2,0,0));
        JButton btnSave = new JButton("Save Current Game");
        styleButton(btnSave);
        btnSave.setActionCommand("Save");
        btnSave.addActionListener(this);
        JButton btnLoad = new JButton("Load Previous Game");
        styleButton(btnLoad);
        btnLoad.setActionCommand("Load");
        btnLoad.addActionListener(this);
        saveLoadPanel.add(btnSave);
        saveLoadPanel.add(btnLoad);
        add(saveLoadPanel, gbc);
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
        crzyShareLabel.setText(user.getCrzyShareAmount() + " shares");
    }

    // EFFECTS: Prints user owned shares of tame
    public void checkTameShares() {
        tameShareLabel.setText(user.getTameShareAmount() + " shares");
    }

    // EFFECTS: updates crzy stock price on UI
    public void updateStockPrice() {
        int delay = 1500; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                market.getCrzyStock().updateStockPrice();
                crzyStockPriceLabel.setText("CRZY $" + market.getCrzyStock().getPrice());
                crzyStockPriceLabel.setFont(strong);
                market.getTameStock().updateStockPrice();
                tameStockPriceLabel.setText("TAME $" + market.getTameStock().getPrice());
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

    private void styleButton(JButton btn) {
        btn.setFont(p);
        btn.setBackground(Color.WHITE);
        btn.setBorder(new LineBorder(new Color(200,200,200), 1));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(12, 32, 12, 32));
    }
}
