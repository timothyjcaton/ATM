import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.io.*;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tjc4h on 6/23/2016.
 */
public class Account implements Serializable
{
    public ArrayList<Account> accounts = new ArrayList<Account>();

    protected int firstdate;

    protected int seconddate;

    protected Calendar cal1 = new GregorianCalendar();

    protected Calendar cal2 = new GregorianCalendar();

    protected boolean dateflag = false;

    protected double rate;

    public double balance = 100;

    double currencyout;

    boolean accountsLoaded = false;

    public void addAccount()
    {
        accounts.add(new Account());

        accounts.add(new Account());

        accounts.add(new Account());

    }

    public void loadAccount(Integer acct) throws IOException
    {
        int input;
        input = acct;

            if (input == 1) {
                accounts.get(input - 1).menu();
            }
            if (input == 2) {
                accounts.get(input - 1).menu();
            }

            if (input == 3) {
                accounts.get(input - 1).menu();
            }

    }

    public void menu() throws IOException {
        double input;

        System.out.println("Please choose from the following options");
        System.out.println("1.) Deposit ");
        System.out.println("2.) Withdraw");
        System.out.println("3.) Check Balance");
        System.out.println("4.) Exit");
        Scanner sc = new Scanner(System.in);
        input = sc.nextInt();

        while(input != 4)
        {
            if (input == 1)
            {
                System.out.println("Please enter the amount you would like to deposit:");
                Scanner sc3 = new Scanner(System.in);
                input = sc3.nextDouble();
                deposit(input);
            }
            if (input == 2)
            {
                System.out.println("Please enter the amount you would like to withdraw:");
                Scanner sc4 = new Scanner(System.in);
                input = sc4.nextDouble();
                withdrawal(input);
            }
            if (input == 3)
            {
                System.out.println("Your balance is: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(balance(getBalance())));
            }
            else
            {
                //do nothing
            }

            System.out.println("Please choose from the following options");
            System.out.println("1.) Deposit ");
            System.out.println("2.) Withdraw");
            System.out.println("3.) Check Balance");
            System.out.println("4.) Exit");
            sc = new Scanner(System.in);
            input = sc.nextInt();
        }

    }

    public void deposit(double depositAmount) throws IOException {
        balance = getBalance() + depositAmount;
        System.out.println("Deposit of " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(depositAmount) + " processed\n");
    }

    public void withdrawal(double withdrawalAmount) throws IOException {
        if(balance - withdrawalAmount < 0)
        {
            System.out.println("Insufficient Funds\n");
        }

        if(balance - withdrawalAmount >= 0)
        {
            balance = getBalance() - withdrawalAmount;
            System.out.println("Withdrawal of " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(withdrawalAmount) + " processed\n");
        }
    }

    public void saveAccount()
    {
        try
        {
            FileOutputStream outStream = new FileOutputStream("D:filel.out");
            ObjectOutputStream os = new ObjectOutputStream(outStream);
            os.writeObject(accounts);
            os.flush();
            os.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void accessAccount()
    {

        try
        {
            FileInputStream inStream = new FileInputStream("D:filel.out");
            ObjectInputStream is = new ObjectInputStream(inStream);
            accounts = (ArrayList <Account>) is.readObject();
            is.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public double balance (double balance)
    {
            this.balance = balance;
            return balance;
    }

    public double getBalance() throws IOException
    {

        if (dateflag == true)
        {

            getDate2();

            getInterest();

        }

        else
        {

            getDate1();

        }

        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    private void getDate1() throws IOException

    {
        System.out.print("Enter todays date(mm/dd/yyyy): ");

        Scanner sc = new Scanner(System.in);

        String inputText = sc.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ParsePosition pos = new ParsePosition(0);


        Date date = formatter.parse(inputText, pos);

        cal1.setTime(date);

        firstdate = cal1.get(Calendar.DAY_OF_YEAR);

        dateflag = true;
    }


    private void getDate2() throws IOException

    {
        System.out.print("Enter todays date(mm/dd/yyyy): ");

        Scanner sc = new Scanner(System.in);

        String inputText = sc.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ParsePosition pos = new ParsePosition(0);

        Date date= new Date();

        date = formatter.parse(inputText, pos);

        cal2.setTime(date);

        seconddate = cal2.get(Calendar.DAY_OF_YEAR);

        if (firstdate > seconddate)
        {

            System.out.println("You must enter a future date.");

            getDate2();

        }
    }

    private void getInterest()

    {
        int datediff = seconddate - firstdate;

        rate = .10/365;

        double ratetime = Math.pow(1+rate,datediff);

        balance = balance * ratetime;

        firstdate = seconddate;
    }

    private String calcInterest() throws IOException
    {

        NumberFormat currencyFormatter;

        String currencyOut;

        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        currencyOut = currencyFormatter.format(balance);

        return currencyOut;
    }

}
