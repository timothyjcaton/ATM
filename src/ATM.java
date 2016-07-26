import java.io.IOException;
import java.util.Scanner;

/**
 * Created by tjc4h on 6/23/2016.
 */

public class ATM
{
    private static Scanner sc = new Scanner(System.in);
    private static Account myAcct = new Account();

    public static void main (String[] args) throws IOException {
        topMenu();
    }

    public static void topMenu() throws IOException {
        boolean exit = false;
        int input;

        while(!exit)
        {
            System.out.println("Please choose one of the following:");
            System.out.println("1.) Populate Accounts");
            System.out.println("2.) Load Account");
            System.out.println("3.) Access Account");
            System.out.println("4.) Exit");

            input = sc.nextInt();
            sc.nextLine();

            switch(input)
            {
                case 1:
                    myAcct.addAccount();
                    break;
                case 2:
                    int acct;
                    System.out.println("Please choose account 1, 2 or 3:");
                    acct = sc.nextInt();
                    myAcct.loadAccount(acct);
                    break;
                case 3:
                    myAcct.accessAccount();
                    System.out.println("Please choose account 1, 2 or 3:");
                    acct = sc.nextInt();
                    myAcct.loadAccount(acct);
                    break;
                case 4:
                    exit = true;
                    myAcct.saveAccount();
                    break;
            }
        }
    }
}
