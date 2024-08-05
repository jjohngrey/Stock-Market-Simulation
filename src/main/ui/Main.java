package ui;

import java.io.FileNotFoundException;

import ui.gui.StockMarketApp;

public class Main {
    public static void main(String[] args) {
        try {
            new StockMarketApp();
            new TerminalApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found.");
        }
        
    } 
}
