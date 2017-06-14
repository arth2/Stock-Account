//Arth Baghel
//6-12-15
//Mr. Soin
//Period 1
package accountSystem;
       
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
       
@SuppressWarnings("unused")
public class StockAccount extends Account {
    private ArrayList<Stock> stocks;
    private double money;
    DecimalFormat dft = new DecimalFormat("0.##");
     
     
    //default constructor
    public StockAccount() {
        stocks = new ArrayList<Stock>();
        money = 10000.00;
    }
     
    //constructor for resuming progress, takes previous balance
    public StockAccount(double money){
        this.money = money;
        stocks = new ArrayList<Stock>();
    }
     
    //returns read-only balance
    public double balance(){
        return money;
    }
     
    //buys a stock given a symbol, number of shares to buy, and threshold of how much to spend
    public void buy(String str, int shareNumber, double spending) {
        if(shareNumber < 0){ //data validation
            System.out.println("Error. You can only purchase postive numbers of stocks" + ERROR);
            return;
        }
        //ensures proper stock symbol ticker
        String temp = str.toUpperCase();
        Stock s = makeStock(temp); //invokes makeStock to create a stock out of the ticker
        double price = s.getPrice()*shareNumber;
        if(stocks.size() < 1 && price < money && price < spending){ //first check, if this is the first stock ever purchased on the account
            stocks.add(new Stock(s));
            stocks.get(0).addShares(shareNumber-1); //adds the original stock in the line above, and then the rest of the shares here
            money-=price;
            System.out.println("Sucessfully purchased " + shareNumber + " shares of " + s.getTicker() + " for " + fmt.format(price) + ".\n");
            recordHistory("Successfully purchased " + shareNumber + " shares of " + s.getTicker() + "\n", "stock_transaction_history.txt", true); //records history
        }
        else if(price < money && price < spending){
            if(findStockind(temp) != -1){
                stocks.get(findStockind(temp)).addShares(shareNumber);
                money -= price;
                System.out.println("Successfully purchased " + shareNumber + " shares of " + str.toUpperCase() + " for " + fmt.format(price) + ".\n");
                recordHistory("Sucessfully purchased " + shareNumber + " shares of " + s.getTicker() + "\n", "stock_transaction_history.txt", true);
            }
            else{
                stocks.add(s);
                stocks.get(stocks.size()-1).addShares(shareNumber-1);
                money -= price;
                System.out.println("Successfully purhased " + shareNumber + " shares of " + str.toUpperCase() + " for " + fmt.format(price) + ".\n");
                recordHistory("Successfully purchased " + shareNumber + " shares of " + s.getTicker() + "\n", "stock_transaction_history.txt", true);
            }
        }
        else if(price > spending)
            System.out.println("\nError. The cost of this purchase is " + fmt.format(price) + " but you were only willing to spend " + fmt.format(spending) + "." + ERROR + "\n");
        else
            System.out.println("Error. The cost of this purchase is " + fmt.format(price) + " but you were only have " + fmt.format(money) +  " in your bank account." +ERROR + "\n");
    }
     
    //method to sell stock shares. takes symbol, number of shares to sell, and minimum selling price
    public void sell(String at, int sharesToSell, double price){
        double profit = 0;
        String a = at.toUpperCase(); //again, verifies stock ticker
        if(sharesToSell < 0){ //data validation
            System.out.println("Error. You cannot sell negative amounts of a stock." + ERROR);
            return;
        }
        if(findStockind(a) != -1){ //calls findStockind, which returns the index of a stock in stocks, given its ticker. If it does not exist, returns -1
            int temp = stocks.get(findStockind(a)).getShares(); //gets the number of shares of the stock available, if it exists
            if(stocks.get(findStockind(a)).getPrice() > price){
                if(temp >= sharesToSell){
                    profit = stocks.get(findStockind(a)).getPrice()*sharesToSell; //calculates profit, then adds it to balance
                    money += profit;
                    if(sharesToSell == temp)
                        stocks.remove(findStockind(a)); //checks if all shares of a stock have been sold, and if so, removes it wholly from stocks
                    else
                        stocks.get(findStockind(a)).sellShares(sharesToSell); //otherwise, removes the appropriate number of shares
                    System.out.println("Successfully sold " + sharesToSell + " shares of " + a + " for " + fmt.format(profit));
                    System.out.println("Your account balance is now " + fmt.format(money) + "\n");
                    recordHistory(("Sold " + sharesToSell + " shares of " + a + "\n"), "stock_transaction_history.txt", true); //records history
                    return;
            }
            //error statements
            else{
                System.out.println("Error: You tried selling " + sharesToSell + " shares, but only have " + temp + " shares");
                return;
            }
            }
            else
                System.out.println("The current selling price of " + a + " was lower than your minimum threshold." + ERROR);
        }
        System.out.println("Error: You do not have that stock in your portfolio." + ERROR);
    }
         
    public void displayStockPrice(String str){
        String use = str.toUpperCase(); //verifies ticker case matching
        ArrayList<String> tickers = new ArrayList<String>();
        tickers = readFile("Results.txt"); //reads all the stocks present in Results.txt
        boolean found = false;
        for(String s : tickers)
            if(use.equals(s.substring(0, s.indexOf(':')))){ //checks to see if ticker in a given line of Results.txt matches
                System.out.println("Current price for " + use + ": $" + dft.format(new Double(s.substring(s.indexOf(':') + 1))) + "\n"); //gives price
                found = true;
            }
        if(!found)
            System.out.println("Error. Invalid stock name.\n");
    }
     
    //method to add a stock to the portfolio, used in resuming from a text file
    public void addStock(Stock s){
        stocks.add(s);
    }
     
    //transfers money from stock account to bank account
    public double withdraw(double amt){
        if(amt < money){
            money-=amt;
            return amt;
        }
        System.out.println("Error: You tried withdrawing " + fmt.format(amt) + " from your Stock Account, but only have " + fmt.format(money) + " in your account." + ERROR);
        return 0;
    }
     
    //transfers money from bank account to stock account
    public void deposit(double amt){
        if(amt > 0.0){
            money+=amt;
            return;
        }
        System.out.println("Error. You cannot deposit a negative amount of money." + ERROR);
    }
     
    //returns the index of a stock in stocks, given its ticker
    public int findStockind(String s){
        for(int i = 0; i< stocks.size(); i++)
            if(stocks.get(i).getTicker().equals(s))
                return i;
        return -1; //if the stock is not found, returns -1 to indicate so
    }
     
    //searches if a stock is present in stocks, based upon the ticker
    public Stock findStock(String s){
        for(int i = 0; i< stocks.size(); i++)
            if(stocks.get(i).getTicker().equals(s))
                return stocks.get(i);
        return null;
    }
     
    //displays the portfolio
    public void display() {
        System.out.println("\nBalance: " + fmt.format(money));
        System.out.println("\t\tCURRENT STOCKS: ");
        System.out.println("Company Symbol:\tShares\tPrice per share\tTotal Value");
        for(Stock s : stocks)
            System.out.println(s);
        System.out.println();
    }
     
    //creates a Stock object given a string ticker
    public Stock makeStock(String s){
        s = s.toUpperCase();
        ArrayList<String> stks = readFile("Results.txt");
        Stock res;
        Double temp;
        for(String a : stks){
            if(s.equals(a.substring(0, a.indexOf(":")))){ //checks if string ticker exists within Results.txt, and if so, creates it
                res = new Stock(s, new Double((a.substring(a.indexOf(':')+1, a.length()))));
                return res;
            }
        }
        System.out.println("Error: You did not enter a valid stock symbol.");
        return null;
    }
     
    //updates the prices of all purchased stocks
    public void updateStocks(){
        for(Stock s : stocks)
            s = makeStock(s.getTicker()); //invokes makeStock to use the same tickers of existing stocks, but check in Results.txt for the new prices
    }
     
    //method to write to stock_status.txt to provide basic information on balance and stocks purchased to allow resuming from a previous execution
    public void updateStatus() throws IOException{
        writeToFile("stock_status.txt", new Double(money).toString(), false); //writes the current balance to stock_status, overwriting the previous contents
        if(stocks.size() > 0){
            ArrayList<Stock> stArray = new ArrayList<Stock>();
            stArray = stocks;
            for(Stock s: stArray){
                //writes specialized stock string equivalents to file, separated by |'s
                writeToFile("stock_status.txt", "|"+(s.statusString()), true);
            }
        }
    }
     
    //prints out transaction history
    public void history(){
         System.out.println("\t\tHISTORY:");
         for(String s : readFile("stock_transaction_history.txt"))
             System.out.println(s);
         System.out.println();
    }
     
    //returns the stock at index i
    public Stock getStock(int i) {
        return stocks.get(i);
    }
           
}
