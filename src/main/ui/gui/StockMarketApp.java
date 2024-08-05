package ui.gui;

import java.io.FileNotFoundException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Market;

public class StockMarketApp extends JFrame {

    private Market market;
    private UserPanel up;
    private BuyPanel bp;
    private SellPanel sp;
    private HistoryPanel hp;

    public StockMarketApp() throws FileNotFoundException {
        super("Stock Market Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setSize(800, 800);
        ImageIcon image = new ImageIcon("icon.png");
        setIconImage(image.getImage());
        setPreferredSize(new Dimension(800, 800));
        
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
        setLayout(new FlowLayout());
        // setLayout(new GridLayout(3, 4));
    
        market = new Market();

        up = new UserPanel(market);
        hp = new HistoryPanel(market);
        bp = new BuyPanel(market, up, hp);
        sp = new SellPanel(market, up, hp);

        add(up);
        add(bp);
        add(sp);
        add(hp);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}