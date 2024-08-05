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
public class BuyPanel extends JPanel implements ActionListener {

    private JLabel buyLabel;
    private JTextField buyField;

	private Market market;
    private UserPanel userPanel;
    private HistoryPanel historyPanel;
	
	// Constructs a score panel
	// effects: sets the background colour and draws the initial labels;
	//          updates this with the game whose score is to be displayed
	public BuyPanel(Market market, UserPanel mp, HistoryPanel hp) {
		this.market = market;
        this.userPanel = mp;
        this.historyPanel = hp;

		setBackground(new Color(180, 180, 180));
        setBounds(250, 250, 250, 250);

        JButton btnBuy = new JButton("Buy");
        btnBuy.setActionCommand("Buy");
        btnBuy.addActionListener(this); 
                                    // Sets "this" object as an action listener for btn
                                     // so that when the btn is clicked,
                                     // this.actionPerformed(ActionEvent e) will be called.
                                     // You could also set a different object, if you wanted
                                     // a different object to respond to the button click
        
		buyLabel = new JLabel("Buy: ");
        buyField = new JTextField(5);
        
        add(buyLabel);
        add(buyField);
        add(btnBuy);
	}

	//This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Buy")) {
            buyStock();
            userPanel.checkBalance();
            userPanel.checkShares();
            buyField.setText("");
            historyPanel.produceHistory();
        }
    }

	// MODIFIES: User
    // EFFECTS: Checks if user has enough money to buy, if they do, buys shares
    // and adds to order history
    // otherwise, does nothing
    public void buyStock() {
        Stock selected = market.getStock();
		int volume = Integer.valueOf(buyField.getText());
        String ticker = selected.getTicker();
        int price = selected.getPrice();

        if (volume >= 0) {
            int totalCost = volume * price;
            if (market.getUser().getBalance() >= totalCost) { // if funds are sufficient
                market.getUser().decreaseBalance(totalCost); // incur costs
                market.getUser().increaseShares(volume); // receive shares
                Order order = new Order(selected, ticker, price, volume, true); // create new order
                market.getUser().addToOrderHistory(order); // add to orderHistory
                System.out.println("You have bought " + volume + " shares of "
                        + market.getStock().getTicker() + " at $" + price + " price.");
                System.out.println("Order is successful.");
            } else {
                System.out.println("You do not have sufficient funds.");
            }
        }
    }
}
