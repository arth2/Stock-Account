package accountSystem;
 
import java.util.Scanner;
 
public class SimpleMenu {
 
    public int NumberedMenu(String Title, String[] Options) {
        System.out.println(Title);
        for(int i=0;    i<Options.length;   i++)
            System.out.println(i+". "+Options[i]);
        int choice;
        choice=getIntInput(1,new String[] {"Your Choice: "}, Options.length,false)[0];
        return choice;
    }
     
    public int[] getIntInput(int args, String[] Names, int length,boolean Close) {
        int[] Input = new int[args];
        int tmp;
        Scanner UserInput = new Scanner(System.in);
        for (int i = 0; i < args; i++) {
            System.out.print(Names[i]);
            while (true) {
                if (UserInput.hasNextInt()) {
                    tmp = UserInput.nextInt();
                    if (tmp<length) {
                        Input[i]=tmp;
                    }
                    break;
                } else
                    System.out.println("That is not an integer, try again.");
                UserInput.next();
            }
        }
        if (Close)
            UserInput.close();
        return Input;
    }
}
