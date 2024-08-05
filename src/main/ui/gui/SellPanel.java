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
import model.Market;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class SellPanel extends JPanel implements ActionListener {

    private JLabel sellLabel;
    private JTextField sellField;

    private HistoryPanel historyPanel;
    private UserPanel userPanel;
	private Market market;
	
	// Constructs a score panel
	// effects: sets the background colour and draws the initial labels;
	//          updates this with the game whose score is to be displayed
	public SellPanel(Market market, UserPanel mp, HistoryPanel hp) {
		this.market = market;
        this.userPanel = mp;
        this.historyPanel = hp;

		setBackground(new Color(180, 180, 180));

        JButton btnSell = new JButton("Sell");
        btnSell.setActionCommand("Sell");
        btnSell.addActionListener(this); 
                                    // Sets "this" object as an action listener for btn
                                     // so that when the btn is clicked,
                                     // this.actionPerformed(ActionEvent e) will be called.
                                     // You could also set a different object, if you wanted
                                     // a different object to respond to the button click
        
		sellLabel = new JLabel("Sell: ");
        sellField = new JTextField(5);
        
        add(sellLabel);
        add(sellField);
        add(btnSell);
	}

	//This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sell")) {
            sellStock();
            userPanel.checkBalance();
            userPanel.checkShares();
            sellField.setText("");
            historyPanel.produceHistory();
        }
    }

    // MODIFIES: User
    // EFFECTS: Checks if user has enough shares to sell, if they do, sells shares
    // and adds to order history
    // otherwise, does nothing
    public void sellStock() {
        Stock selected = market.getStock();
        System.out.print("Enter number of shares to sell: ");
        int volume = Integer.valueOf(sellField.getText());
        int price = selected.getPrice();
        String ticker = selected.getTicker();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (market.getUser().getShareAmount() >= volume) { // if share amount are sufficient
                market.getUser().increaseBalance(totalCost); // receive money
                market.getUser().decreaseShares(volume); // drop shares
                Order order = new Order(selected, ticker, price, volume, false); // create new order
                market.getUser().addToOrderHistory(order); // add to orderHistory
                System.out.println("You have sold " + volume + " shares of " 
                        + market.getStock().getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have that many shares to sell.");
            }
        }
    }
}
