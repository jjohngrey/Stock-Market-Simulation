package ui.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import model.Market;
import model.Order;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class HistoryPanel extends JPanel implements ActionListener {

    private JTable tableHistory;
    private DefaultTableModel tableModel;

	private Market market;
	
	// Constructs a score panel
	// effects: sets the background colour and draws the initial labels;
	//          updates this with the game whose score is to be displayed
    public HistoryPanel(Market market) {
		this.market = market;

		setBackground(new Color(180, 180, 180));

        JButton btnHistory = new JButton("History");
        btnHistory.setActionCommand("History");
        btnHistory.addActionListener(this); 

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Ticker");
        tableModel.addColumn("Price");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Order Status");

        tableHistory = new JTable(tableModel);
        tableHistory.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(tableHistory);

        add(btnHistory);
        add(sp);
	}

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("History")) {
            produceHistory();
        }
    }

    // EFFECTS: For order in order history, prints the order
    public void produceHistory() {
        tableModel.setRowCount(0);
        List<Order> orderHistory = market.getUser().getOrderHistory();
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            if (order.getOrderType()) {
                tableModel.addRow(new Object[]{order.getTicker(), "$" + order.getPrice(), order.getShares(), "Bought"});
            } else {
                tableModel.addRow(new Object[]{order.getTicker(), "$" + order.getPrice(), order.getShares(), "Sold"});
            }
            
        }
    }
}
