//Arth Baghel
//6-12-15
//Mr. Soin
//Period 1
package accountSystem;
       
import java.io.IOException;
import java.util.ArrayList;
       
public class BankAccount extends Account{
    private double balance; //money in bank account
     
    //default constructor
    public BankAccount(){
        balance = 10000;
    }
     
    //overloaded constructor. used in resuming
    public BankAccount(double bal){
        balance = bal;
    }
     
    //outputs bank account balance
    public void display(){
        System.out.println("You have " + fmt.format(balance) + " in your account\n");
    }
     
     
    //transfers money from stock account to bank account
    public void deposit(double amount, StockAccount sa){
        if(amount < 0){ //data validation
            System.out.println("Error: Must deposit value greater than or equal to $0.00" + ERROR);
            return;
        }
        if(amount > sa.balance()){ //further data validation
            System.out.println("Error: You tried depositing " + fmt.format(amount) + "from your stock account, but only have" + fmt.format(sa.balance()) + ERROR);
            return;
        }
        balance += amount;
        String cond = fmt.format(amount) + " was deposited. Previous balance: " + fmt.format((balance-amount)) + " new balance: " + fmt.format(balance) + "\n";
        recordHistory(cond, "bank_transaction_history.txt", true); //history recorder
        System.out.println("Successfully deposited " + fmt.format(amount) + ". New bank balance: " + fmt.format(balance) + ".\n");
    }
     
    //returns read-only balance
    public double getMoney(){
        return balance;
    }
     
    //transfers money to stock account
    public double withdraw(double amount){
        if(amount > balance){ //data validation
            System.out.println("Error: Insufficent funds. \nYou tried withdrawing " + fmt.format(amount) + " but  only have " + fmt.format(balance) + " in your account");
            return 0;
        }else if(amount <= 0){ //data validation
            System.out.println("Error: Must withdraw amount greater than or equal to $0.00");
            return 0;
        }
        balance -= amount;
        String cond = fmt.format(amount) + " was withdrawn. Previous balance: " + fmt.format((amount+balance)) + " new balance: " + fmt.format(balance) + "\n";
        recordHistory(cond, "bank_transaction_history.txt", true); //history recorder
        System.out.println("Successfully transferred " + fmt.format(amount) + " from bank account to stock account. You now have " + fmt.format(balance) + " left in your bank account\n");
        return amount;
    }
     
    //prints out history
    public void history(){
        ArrayList<String> lines = readFile("bank_transaction_history.txt");
        for(String s : lines)
            System.out.println(s);
    }
    
    //method to write balance to text file to allow resuming the program from the point where it left off at last time
    public void updateStatus() throws IOException{
        writeToFile("bank_status.txt", "" + balance,  false);
    }
           
           
}
