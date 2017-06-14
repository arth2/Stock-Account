//Arth Baghel
//6-12-15
//Mr. Soin
//Period 1
package accountSystem;
      
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
       
public class StockRunner{
    static Scanner a = new Scanner(System.in);
    static NumberFormat fmt=NumberFormat.getCurrencyInstance(new Locale("en","us"));
    static final String ERROR = "Operation aborted.\n";
       
    public static void main(String[] args) throws IOException{
        System.out.println("One moment please, while stock data is fetched from Yahoo! Finance...\n");
        BankAccount uBank;
        StockAccount uStock = new StockAccount();
        if(resume(uStock)){ //calls resume to see if user has already made their account
            System.out.println("Welcome back. Your previous bank account and stock account data is being restored.");
            ArrayList<String> temp = uStock.readFile("bank_status.txt");
            uBank = new BankAccount(new Double(temp.get(0))); //resumes bank account
            uStock.updateStocks(); //updates prices of purchased stocks
               
            ArrayList<String> holder = uStock.readFile("stock_status.txt");
            double money = new Double(holder.get(0).substring(0, holder.get(0).indexOf("|")));
            uStock = new StockAccount(money);
            holder.set(0, holder.get(0).substring(holder.get(0).indexOf("|")+1)); //finds balance in stock account
            int i = 0;
            String utility = "";
            while(holder.get(0).indexOf("|") != -1){ //adds all stocks in ArrayList<Stock> stocks from 0 - n-1
                utility = holder.get(0).substring(0, holder.get(0).indexOf("|"));
                uStock.addStock(uStock.makeStock(utility.substring(0, utility.indexOf("\t"))));
                uStock.getStock(i).addShares(new Integer(holder.get(0).substring(holder.get(0).indexOf("\t")+1, holder.get(0).indexOf("|")))-1); //adds appropriate number of shares
                holder.set(0, holder.get(0).substring(holder.get(0).indexOf("|")+1));
                i++;
            }
            utility = holder.get(0);
            uStock.addStock(uStock.makeStock(utility.substring(0, utility.indexOf("\t")))); //adds in final stock
            uStock.getStock(i++).addShares(new Integer(utility.substring(utility.indexOf("\t")+1))-1);
        }
        else{ //if nothing was done previously, as in files are empty, creates new accounts
            System.out.println("Our records show no previous activity from this location. A new bank account and stock account are being made for you now.\n");
            uStock = new StockAccount();
            uBank = new BankAccount();
        }
            //populates Results.txt    
            uStock.populateResults(false);
              
        System.out.println("Welcome to the Account Management System\n");
        int input = 1;
        boolean control = true;
        while(control){ //while loop for indefinite execution of menu
            String[] options = {"Stock Portfolio Account", "Bank Account" , "Exit"};
            input = NumberedMenu("Please select an account to access", options); //prints menu and gets input
            if(input == 1)
                stockAccount(uStock); //calls method containing user end of stock account
            else if(input == 2)
                bankAccount(uBank, uStock); //calls method containing user end of bank account
            else if(input == 3)
                control = false; //ends loop
        }
        System.out.println("Thank you for using the Account Management System. Goodbye.");
        uBank.updateStatus(); //updates what users bank balance is
        uStock.updateStatus(); //updates stock balance and what stocks they own
               
    }
           
    @SuppressWarnings("resource")
    //user end for stock account
    public static void stockAccount(StockAccount uStock){
        System.out.println("\nWelcome to the Stock Account Management System");
        int input = 1;
        String[] options = {"Find the price of a stock", "Display your portfolio", 
                "Buy stock", "Sell stock", "View your account history", "Exit"};
        String symbol;
        int shares;
        while(input != 7){
            input = NumberedMenu("Enter the operation you would like to access", options);
            if(input == 1){
                //finds price of stock
                 System.out.println("Enter the symbol of the stock you would like to find information on.");
                 String str = (new Scanner(System.in).nextLine());
                 uStock.displayStockPrice(str);
            }
            else if(input == 2){
                //displays portfolio
                uStock.display();
            }
            else if(input == 3){
                //buy a stock
                System.out.println("\t\tBUY A STOCK:\nEnter the stock symbol for the stock you would like to purchase");
                symbol = new Scanner(System.in).nextLine();
                System.out.println("Enter the number of shares you would like to buy");
                shares = new Scanner(System.in).nextInt();
                System.out.println("Enter the max price you are willing to pay for this purchase");
                double max = new Scanner(System.in).nextDouble();
                uStock.buy(symbol, shares, max);
            }
            else if(input == 4){
                //sell stocks
                System.out.println("\t\tSELL SHARES:");
                System.out.println("Enter the symbol of the stock you would like to sell");
                symbol = (new Scanner(System.in).nextLine());
                System.out.println("Enter the number of shares you want to sell");
                shares = new Scanner(System.in).nextInt();
                System.out.println("Enter the minimum price you want each stock to sell for");
                double price = new Scanner(System.in).nextDouble();
                uStock.sell(symbol, shares, price); 
                      
            }
                  
            else if(input == 5){
                //view transaction history
                uStock.history();
            }
                  
            else if(input == 6){
                //exit menu
               break;
            }
        }
    }
          
    @SuppressWarnings("resource")
    //bank account user end
    public static void bankAccount(BankAccount uBank, StockAccount uStock){
        System.out.println("\nWelcome to the Bank Management System");
        String[] options = {"View account balance", "Deposit money", "Withdraw money", "Display history", "Exit"};
        int input = 0;
        boolean check = true;
        while(check){
            input = NumberedMenu("Enter your choice", options); //outputs menu options, gets input
            if(input == 1)
                uBank.display(); //balance
            if(input == 2){
                //deposit money
                System.out.println("Deposit money: Enter how much money you would like to transfer from your stock account to your bank account ");
                double temp = new Scanner(System.in).nextDouble();
                uBank.deposit(uStock.withdraw(temp), uStock);
            }
            if(input == 3){
                //withdraw money
                System.out.println("Withdraw money: Enter how much money you would like to transfer from your bank account to your stock account");
                double temp = new Scanner(System.in).nextDouble();
                uStock.deposit(uBank.withdraw(temp));
            }
            //transaction history
            if(input == 4)
                uBank.history();
            //exit menu
            if(input == 5)
                check = false;
        }
    }
     
    //checks to see if previous data of user activity exists
    public static boolean resume(StockAccount uStock){
        ArrayList<String> bankStuff = new ArrayList<String>();
        bankStuff = uStock.readFile("bank_status.txt");
        ArrayList<String> stockStuff = uStock.readFile("stock_status.txt");
        if(bankStuff.size() < 1 || stockStuff.size() < 1){
            return false;
        }
        return true;
    }
     
    //takes the title of the menu and options
    public static int NumberedMenu(String Title, String[] Options) {
        System.out.println(Title);
        for(int i = 0; i < Options.length; i++)
            System.out.println((i+1)+". "+Options[i]);
        int choice;
        choice=getIntInput(1,new String[] {"Your Choice: "}, Options.length,false)[0];
        return choice;
    }
           
    public static int[] getIntInput(int args, String[] Names, int length,boolean Close) {
        int[] Input = new int[args];
        int tmp;
        Scanner UserInput = new Scanner(System.in);
        for (int i = 0; i < args; i++) {
            System.out.print(Names[i]);
            while (true) {
                if (UserInput.hasNextInt()) { //checks if input was int to avoid runtime error
                    tmp = UserInput.nextInt();
                    if (tmp<=length && tmp>0) {
                        Input[i]=tmp;
                    }
                    break;
                } else
                    System.out.println("Error. That is not an integer. Please try again.");
                UserInput.next();
            }
        }
        if (Close)
            UserInput.close();
        return Input;
    }
           
}
