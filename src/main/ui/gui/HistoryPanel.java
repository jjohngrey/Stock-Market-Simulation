package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import model.Order;
import model.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class HistoryPanel extends JPanel implements ActionListener {

    private JTable tableHistory;
    private DefaultTableModel tableModel;

    private User user;

    private JTabbedPane tabbedPane;
    private JPanel allOrdersPanel;
    private JPanel crzyOrdersPanel;
    private JPanel tameOrdersPanel;
    private JPanel graphPanel;

    // Constructs a history panel
    // EFFECTS: sets the background colour and draws the initial labels
    // updates this with the order histories
    public HistoryPanel(User user) {
        this.user = user;
        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220,220,220), 2, true));
        setLayout(new BorderLayout(12,12));

        // Set modern, square tab style
        UIManager.put("TabbedPane.selected", Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("TabbedPane.unselectedBackground", Color.WHITE);
        UIManager.put("TabbedPane.background", Color.WHITE);
        UIManager.put("TabbedPane.borderHightlightColor", new Color(200,200,200));
        UIManager.put("TabbedPane.light", new Color(200,200,200));
        UIManager.put("TabbedPane.shadow", new Color(200,200,200));
        UIManager.put("TabbedPane.darkShadow", new Color(200,200,200));
        UIManager.put("TabbedPane.tabAreaBackground", Color.WHITE);
        UIManager.put("TabbedPane.focus", new Color(200,200,200));
        UIManager.put("TabbedPane.selectedForeground", Color.BLACK);
        UIManager.put("TabbedPane.foreground", Color.BLACK);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return 40; // taller tabs
            }
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                         int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(new Color(25, 118, 210)); // blue border for selected tab
                } else {
                    g.setColor(new Color(200,200,200)); // gray border for others
                }
                g.drawRect(x, y, w, h-1); // square border
            }
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
                                              Rectangle iconRect, Rectangle textRect, boolean isSelected) {
                // No focus indicator
            }
        });
        allOrdersPanel = createTablePanel();
        crzyOrdersPanel = createTablePanel();
        tameOrdersPanel = createTablePanel();
        graphPanel = new JPanel(new BorderLayout());
        graphPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("All Orders", allOrdersPanel);
        tabbedPane.addTab("CRZY Orders", crzyOrdersPanel);
        tabbedPane.addTab("TAME Orders", tameOrdersPanel);
        tabbedPane.addTab("Graph", graphPanel);
        add(tabbedPane, BorderLayout.CENTER);
        produceHistory(user);
        updateGraph();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int idx = tabbedPane.getSelectedIndex();
                if (idx == 0) {
                    produceHistory(user);
                } else if (idx == 1) {
                    produceCrzyHistory(user);
                } else if (idx == 2) {
                    produceTameHistory(user);
                }
            }
        });
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Stock");
        model.addColumn("Price");
        model.addColumn("Volume");
        model.addColumn("Total");
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setRowHeight(28);
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: For order in order history, prints the order
    public void produceHistory(User user) {
        fillTable((JPanel)tabbedPane.getComponentAt(0), user.getOrderHistory(), null);
    }

    // EFFECTS: For order in order history, prints the order
    public void produceCrzyHistory(User user) {
        fillTable((JPanel)tabbedPane.getComponentAt(1), user.getOrderHistory(), "CRZY");
    }

    // EFFECTS: For order in order history, prints the order
    public void produceTameHistory(User user) {
        fillTable((JPanel)tabbedPane.getComponentAt(2), user.getOrderHistory(), "TAME");
    }

    private void fillTable(JPanel panel, List<Order> orders, String tickerFilter) {
        JScrollPane sp = (JScrollPane)panel.getComponent(0);
        JTable table = (JTable)sp.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for (Order order : orders) {
            if (tickerFilter == null || order.getTicker().equals(tickerFilter)) {
                String type = order.getOrderType() ? "Buy" : "Sell";
                String stock = order.getTicker();
                String price = "$" + order.getPrice();
                int volume = order.getShares();
                int total = order.getPrice() * order.getShares();
                model.addRow(new Object[]{type, stock, price, volume, "$"+total});
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the user to the loaded user
    public void setUser(User user) {
        this.user = user;
    }

    public void actionPerformed(ActionEvent e) {
        // No longer needed, handled by tab selection
    }

    public void updateGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Double> portfolioHistory = calculatePortfolioHistory(user);
        for (int i = 0; i < portfolioHistory.size(); i++) {
            dataset.addValue(portfolioHistory.get(i), "Portfolio Balance", Integer.toString(i));
        }
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Portfolio Balance",
                "Order #",
                "Balance ($)",
                dataset
        );
        // Set line color to blue
        org.jfree.chart.plot.CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE); // Make plot area background white
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer = new org.jfree.chart.renderer.category.LineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(25, 118, 210));
        plot.setRenderer(renderer);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setBackground(Color.WHITE);
        graphPanel.removeAll();
        graphPanel.add(chartPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    // Returns a list of portfolio balances after each order (starting at 10,000)
    private ArrayList<Double> calculatePortfolioHistory(User user) {
        ArrayList<Double> history = new ArrayList<>();
        double startingBalance = 10000.0;
        double cash = startingBalance;
        ArrayList<Order> orders = new ArrayList<>(user.getOrderHistory());
        ArrayList<Order> owned = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getOrderType()) { // Buy
                cash -= order.getPrice() * order.getShares();
                owned.add(order);
            } else { // Sell
                cash += order.getPrice() * order.getShares();
                // Remove sold shares from owned (FIFO)
                int sharesToSell = order.getShares();
                for (int j = 0; j < owned.size() && sharesToSell > 0; ) {
                    Order buyOrder = owned.get(j);
                    if (buyOrder.getTicker().equals(order.getTicker())) {
                        int buyShares = buyOrder.getShares();
                        if (buyShares > sharesToSell) {
                            buyOrder = new Order(buyOrder.getStock(), buyOrder.getTicker(), buyOrder.getPrice(), buyShares - sharesToSell, true);
                            owned.set(j, buyOrder);
                            sharesToSell = 0;
                        } else {
                            sharesToSell -= buyShares;
                            owned.remove(j);
                            continue;
                        }
                    }
                    j++;
                }
            }
            // Calculate portfolio value: cash + (current shares * current price)
            double portfolio = cash;
            int crzyShares = 0;
            int tameShares = 0;
            for (Order o : owned) {
                if (o.getTicker().equals("CRZY")) {
                    crzyShares += o.getShares();
                } else if (o.getTicker().equals("TAME")) {
                    tameShares += o.getShares();
                }
            }
            // Use current price for each stock
            model.Market market = null;
            try {
                java.lang.reflect.Field marketField = user.getClass().getDeclaredField("market");
                marketField.setAccessible(true);
                market = (model.Market) marketField.get(user);
            } catch (Exception e) {
                // fallback: do nothing
            }
            if (market != null) {
                portfolio += crzyShares * market.getCrzyStock().getPrice();
                portfolio += tameShares * market.getTameStock().getPrice();
            }
            history.add(portfolio);
        }
        if (history.isEmpty()) {
            history.add(startingBalance);
        }
        return history;
    }
}
