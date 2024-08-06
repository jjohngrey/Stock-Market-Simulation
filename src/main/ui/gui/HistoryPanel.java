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

import model.Order;
import model.User;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class HistoryPanel extends JPanel implements ActionListener {

    private JTable tableHistory;
    private DefaultTableModel tableModel;

    private User user;

    // Constructs a history panel
    // EFFECTS: sets the background colour and draws the initial labels
    // updates this with the order histories
    public HistoryPanel(User user) {
        this.user = user;

        setBackground(new Color(180, 180, 180));

        JButton btnHistory = new JButton("History");
        btnHistory.setActionCommand("History");
        btnHistory.addActionListener(this);

        JButton btnCrzyHistory = new JButton("CRZY");
        btnCrzyHistory.setActionCommand("CRZY");
        btnCrzyHistory.addActionListener(this);

        JButton btnTameHistory = new JButton("TAME");
        btnTameHistory.setActionCommand("TAME");
        btnTameHistory.addActionListener(this);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Ticker");
        tableModel.addColumn("Price");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Order Status");

        tableHistory = new JTable(tableModel);
        tableHistory.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(tableHistory);

        add(btnHistory);
        add(btnCrzyHistory);
        add(btnTameHistory);
        add(sp);
    }

    // This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("History")) {
            produceHistory(user);
        } else if (e.getActionCommand().equals("CRZY")) {
            produceCrzyHistory(user);
        } else if (e.getActionCommand().equals("TAME")) {
            produceTameHistory(user);
        }
    }

    // EFFECTS: For order in order history, prints the order
    public void produceHistory(User user) {
        tableModel.setRowCount(0);
        List<Order> orderHistory = user.getOrderHistory();
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            if (order.getOrderType()) {
                Object[] obj = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(), "Bought" };
                tableModel.addRow(obj);
            } else {
                Object[] object = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(), "Sold" };
                tableModel.addRow(object);
            }

        }
    }

    // EFFECTS: For order in order history, prints the order
    public void produceCrzyHistory(User user) {
        tableModel.setRowCount(0);
        List<Order> orderHistory = user.getOrderHistory();
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            if (order.getTicker().equals("CRZY")) {
                if (order.getOrderType()) {
                    Object[] obj = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(),
                            "Bought" };
                    tableModel.addRow(obj);
                } else {
                    Object[] object = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(),
                            "Sold" };
                    tableModel.addRow(object);
                }
            }
        }
    }

    // EFFECTS: For order in order history, prints the order
    public void produceTameHistory(User user) {
        tableModel.setRowCount(0);
        List<Order> orderHistory = user.getOrderHistory();
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            if (order.getTicker().equals("TAME")) {
                if (order.getOrderType()) {
                    Object[] obj = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(),
                            "Bought" };
                    tableModel.addRow(obj);
                } else {
                    Object[] object = new Object[] { order.getTicker(), "$" + order.getPrice(), order.getShares(),
                            "Sold" };
                    tableModel.addRow(object);
                }
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: changes the user to the loaded user
    public void setUser(User user) {
        this.user = user;
    }
}
