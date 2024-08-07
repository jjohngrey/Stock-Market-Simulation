# Stock Market Simulation

## What will the application do?
My project will **simulate a stock market with one made-up stock**. The user will be given time to buy/share any number of shares that they can afford with the starting investment as the made-up stock is simulated to rise/fall. The goal is to make as much profit as possible within the time limit. 

## Who will use it?
Users who want to learn about how a stock market operates on a microscale (daytraders)!

## Why is this project of interest to you?
This project is of interested to me because I did a stock market competition last year and really enjoyed it. I think it's very fun and it can simulate the market (considering I'm also a _business_ _major_).


## User Stories:
* As a user, I want to be able to create a buy order of X number of shares and add it to my order list.
* As a user, I want to be able to create a sell order of X number of shares and add it to my order list.
* As a user, I want to be able to view a list of my past orders, longs or shorts.
* As a user, I want to be able to view my profit/loss as I am making my trades.
* As a user, I want to be able to see the ticker prices as they are being updated.
* As a user, I want to be able to have the option to save my stock orders to file.
* As a user, I want to be able to have the option to load my stock orders from file.

## Instructions for Grader:
* You can generate the first required action related to adding multiple orders to a order history by buying/selling a positive number of stocks and pressing the buy/sell button or by loading in a previous game with a order history.
* You can generate the second required action related to adding multiple orders to a order history by clicking the CRZY or TAME button beside History to view only CRZY or only TAME stocks.
* You can locate my visual component by looking at the top of the application. There should be a bull with a stock symbol looking like a logo of the app.
* You can save the state of my application by pressing the save button after making some buy/sell changes.
* You can reload the state of my application by pressing the load button.

## Phase 4: Task 2
* Wed Aug 07 00:01:54 PDT 2024: Market opened with CRZY set at $20 and TAME set at $10.
* Wed Aug 07 00:01:57 PDT 2024: File has been saved
* Wed Aug 07 00:01:59 PDT 2024: Order with CRZY at 32 with 50 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:02 PDT 2024: Order with CRZY at 35 with 20 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:04 PDT 2024: Order with CRZY at 34 with 10 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:05 PDT 2024: Order with TAME at 9 with 50 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:07 PDT 2024: Order with TAME at 10 with 10 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:08 PDT 2024: Order with CRZY at 29 with 30 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:10 PDT 2024: File has been saved
* Wed Aug 07 00:02:11 PDT 2024: Order with CRZY at 32 with 50 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: Order with CRZY at 35 with 20 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: Order with CRZY at 34 with 10 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: Order with TAME at 9 with 50 number of shares of a true order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: Order with TAME at 10 with 10 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: Order with CRZY at 29 with 30 number of shares of a false order type (true is buy and false is sell).
* Wed Aug 07 00:02:11 PDT 2024: File has been loaded

## Phase 4: Task 3
* If I had more time, I would make the update stock price algorithm different for both CRZY and TAME. To do this, I could use an override to change up the numbers so that it would appear that the stocks would not only be moving differently but also at different rates (not within the same range).
* I think I would also make a Panel class that I could implement/extend because much of my code is repeated. I realized when I was creating the UML diagramn that much of it required the same classes (market, userPanel, user, etc). It seemed odd that I had designed it in such a way that each new panel required all this information individually when I could have just pulled it out and then implemented or extended it.
* One last thing that I wish I could help change/refactor was to pull out another Panel that could say something like StockPanel and then just does the buy/sell because they have a lot of duplicate code.

## TODO
* add insufficient funds pop up/not enough shares pop up
* add time limit