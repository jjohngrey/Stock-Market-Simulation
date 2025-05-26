package ui.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import model.Event;
import model.EventLog;
import model.Market;

public class StockMarketApp extends JFrame {

    private Market market;
    private UserPanel up;
    private CrzyPanel cp;
    private TamePanel tp;
    private HistoryPanel hp;

    public StockMarketApp() throws FileNotFoundException {
        super("Stock Market Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(24, 24, 24, 24));
        getContentPane().setBackground(Color.decode("#f7f7f7"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        market = new Market();
        hp = new HistoryPanel(market.getUser());
        up = new UserPanel(market, hp, cp, tp);
        cp = new CrzyPanel(market, up, hp, market.getUser());
        tp = new TamePanel(market, up, hp, market.getUser());
        up.setCrzyPanel(cp);
        up.setTamePanel(tp);

        // UserPanel (top left)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        add(up, gbc);

        // CrzyPanel (top right)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(cp, gbc);

        // TamePanel (top right, next to CrzyPanel)
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        add(tp, gbc);

        // HistoryPanel (bottom, spanning all columns)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weighty = 0.9;
        add(hp, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        makeWindowListener();

        up.updateStockPrice();
    }

    // EFFECTS: adds all the panels
    public void addPanels() {
        add(makeImage(), BorderLayout.CENTER);
        add(up);
        add(cp);
        add(tp);
        add(hp);
    }

    // EFFECTS: creates a window listener to print log when window is closed
    public void makeWindowListener() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                print(EventLog.getInstance().getEvents());
                dispose();
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: prints event log to console when application is quit
    public void print(Collection<Event> events) {
        for (Event e : events) {
            System.out.println(e.getDate().toString() + ": " + e.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a logo to add to the market app ui
    public JScrollPane makeImage() {
        String name = "/Users/johngrey/Downloads/CPSC 210/Personal Project/ProjectStarter/src/main/ui/images/icon.png";
        ImageIcon image = new ImageIcon(name);
        JLabel imageLabel = new JLabel(image);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

}