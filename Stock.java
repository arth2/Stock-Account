//Arth Baghel
//6-12-15
//Mr. Soin
//Period 1
package accountSystem;
       
import java.text.*;
import java.util.*;
       
       
public class Stock{
           
    static NumberFormat fmt=NumberFormat.getCurrencyInstance(new Locale("en","us")); //formats doubles into $XX.xx format
    private String name; //stock ticker
    private double value; //stock price
    private int shares; //shares of stock purchased
       
    //default constructor      
    public Stock(){
        name ="";
        value = 0;
        shares = 0;
    }
     
    //overloaded constructors
    public Stock(String symbol, double price){
        name = symbol.toUpperCase();
        value = price;
        shares = 1;
    }
           
    public Stock(Stock s){
        name = s.getTicker();
        value = s.getPrice();
        shares = s.getShares();
    }
     
    //returns ticker, name
    public String getTicker(){
        return name;
    }
     
    //adds shares. used in purchasing
    public void addShares(int share){
        shares += share;
    }
     
    //returns number of shares. used in selling
    public int getShares(){
        return shares;
    }
     
    //removes shares. used in selling
    public void sellShares(int x){
        if(x < shares && x > 0)
            shares-=x;
    }
     
    //returns value of stock. used in buying and selling
    public double getPrice(){
        return value;
    }
    
    //toString, to print out information about stock
    public String toString(){
        double temp = value * shares;
        String res = name + "\t\t" + shares + "\t" + fmt.format(value) + "\t\t" + fmt.format(temp);
        return res;
    }
     
    //specialized toString that gives only name and shares. Used in resuming, since based on name and shares price can be found
    public String statusString(){
        String res = name + "\t" + shares;
        return res;
    }
           
}
