package accountSystem;
      
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;
      
      
      
public abstract class Account {
    static NumberFormat fmt=NumberFormat.getCurrencyInstance(new Locale("en","us"));
    Scanner a = new Scanner(System.in); 
    final String ERROR = " Operation aborted.";
    static boolean offlineMode = false; 
    
     
     
    //Goes online to get real data for previous days closing value for a given stock symbol
    public static String fetchStockData(String symbol){
        String url = "http://real-chart.finance.yahoo.com/table.csv?s=" + symbol + "&a=" + Calendar.DATE
                +"&b=" + (Calendar.MONTH) + "&c=" + Calendar.YEAR 
                + "&d=" + Calendar.DATE + "&e=" + Calendar.MONTH 
                + "&f=" + Calendar.YEAR + "g=d&ignore=.csv";
 
        try {
            URL yahoo = new URL(url);
            URLConnection getYahoo = yahoo.openConnection();
            Scanner scn = new Scanner(getYahoo.getInputStream());
                  
            if(scn.hasNext())
                scn.nextLine();
            String res = scn.nextLine();
            res = res.substring(res.lastIndexOf(',') +1);
            return res;
        } catch (Exception e) {
            offlineMode = true;
            System.out.println("An error has occured. Either while attempting to access, or while"
                    + " accessing Yahoo! Stocks, an error occured. The operation has been aborted");
            return null;
        }
          
    }
          
     
    //Fills up Results.txt with values from internet, or leaves it alone in OfflineMode      
    public void populateResults(boolean offlineMode) throws IOException{
        if(!offlineMode){
            String file = "Results.txt";
            ArrayList<String> tickers = new ArrayList<String>();
            ArrayList<String> temps = new ArrayList<String>();
            tickers = readFile(file);
            for(String s : tickers)
                temps.add(s.substring(0, s.indexOf(":")));
            writeToFile("Results.txt", "", false);
            for(String s : temps){
                writeToFile("Results.txt", (s + ":" + fetchStockData(s)+"\n"), true);
            }
        }
    }
         
    public String offlineData(String symbol){
        ArrayList<String> tickers = new ArrayList<String>();
        tickers = readFile("Results.txt");
        for(String s : tickers){
            if(s.substring(0, s.indexOf(':')).equals(symbol)){
                String res = s;
                res = res.substring(res.lastIndexOf(','));
                return res;
            }
        }
        System.out.println("Error: You did not enter one of the approved stocks." + ERROR);
        return "";
    }
     
     
    //basic method called to write a given string, str, to a file, that can either overwrite or append to a file
    public static void writeToFile(String file, String str, boolean append) throws IOException{
        File test = new File(file);
        BufferedWriter writer = new BufferedWriter(new FileWriter(test, append));
        try {
            writer.write(str);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            writer.close();
            throw e1;
        }
        writer.close();
             
    }  
         
    public void recordHistory(String condition, String filePath, boolean append){
        Date date = new Date();
        String res = "";
        res += (new Timestamp(date.getTime()));
        res+= " : " + condition;
        try {
            writeToFile(filePath, res, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
         
    public ArrayList<String> readFile(String str){
        File f = new File(str);
        ArrayList<String> lines = new ArrayList<String>();
        try{ 
            a = new Scanner(f);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
        }
              
        while(a.hasNextLine())
            lines.add(a.nextLine());
        return lines;
    }
          
}
