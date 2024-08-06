package ui.gui;

import java.io.FileNotFoundException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
        // setSize(800, 800);
        ImageIcon image = new ImageIcon("icon.png");
        setIconImage(image.getImage());
        setPreferredSize(new Dimension(800, 800));
        
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));        
        setLayout(new FlowLayout());
    
        market = new Market();

        hp = new HistoryPanel(market.getUser());
        up = new UserPanel(market, hp, cp, tp);
        cp = new CrzyPanel(market, up, hp, market.getUser());
        tp = new TamePanel(market, up, hp, market.getUser());
        up.setCrzyPanel(cp);
        up.setTamePanel(tp);

        add(makeImage(), BorderLayout.CENTER);
        add(up);
        add(cp);
        add(tp);
        add(hp);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
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

}