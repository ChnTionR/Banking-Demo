import java.lang.NumberFormatException;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    Scanner input = new Scanner(System.in); // Declare a scanner object
    Accounts acc1 = new Accounts(11111, "123", "acc1", 0.0f);
    Accounts acc2 = new Accounts(22222, "234", "acc2", 0.0f);


    private void exit(){
        System.exit(0);
    }

    private Accounts startup(){
        Accounts tryLogin;
        String in = "1";
        int inNo = Integer.parseInt(in);

        while(inNo != acc1.accNo && inNo != acc2.accNo){ //while the right answer has not been given:
            try{
                System.out.println("enter your 5 dig acc no [(e)xit]:");
                in = input.nextLine();
                if(in.equalsIgnoreCase("e")) {
                    exit();
                }
                inNo = Integer.parseInt(in);
                if (inNo != acc1.accNo && inNo != acc2.accNo){ // if entered an unavailable no
                    System.out.println("that was not a valid no");
                }
            } catch (NumberFormatException e) { // if not an int
                System.out.println("your no should only include numbers");
            }
        }
        // detect which acc is the user logging on
        if(inNo == acc1.accNo){
            tryLogin = acc1;
        }else{
            tryLogin = acc2;
        }
        System.out.printf("enter the password for %s:\n", tryLogin.name);
        String inPass = input.nextLine();//get user input
        for(int i = 4; i>0; i--){ //4+1 attempts
            if(!tryLogin.pass.equals(inPass)){ //guessed incorrectly
                System.out.printf("wrong pass for %s. You have %d more attempt(s):\n", tryLogin.name, i);
                input.nextLine();//set cursor to the next line
                inPass = input.nextLine();
            }else{ //login successful
                return tryLogin;
            }
        }
        //ran out of attempts
        System.out.println("login failed");
        input.close();
        return null;
    }

    private void menu(Accounts loggedAcc){
        while(true) {
            System.out.printf("you are currently logged on %s\nyour savings:%.2f$\n", loggedAcc.name, loggedAcc.money);
            System.out.println("(d)eposit, (w)ithdraw, (e)xit");
            String action = input.nextLine(); //assign an action declarer
            switch (action){
                case "d":
                    deposit(loggedAcc);
                    break;
                case "w":
                    withdraw(loggedAcc);
                    break;
                case "e":
                    exit();
            }
        }
    }

    private void deposit(Accounts loggedAcc){
        System.out.println("enter the amount that you would like to deposit:");
        String amountStr = input.nextLine(); //get amount
        if(amountStr.contains("-")){
            System.out.println("you can't enter negative numbers");
            return;
        }
        try{
            //try to str => float
            float amountFloat = Float.parseFloat(amountStr);
            loggedAcc.money += amountFloat;
        } catch (NumberFormatException e) {
            System.out.println("you can't enter non-number characters");
        }
    }

    private void withdraw(Accounts loggedAcc){
        System.out.println("enter the amount that you would like to withdraw:");
        String amountStr = input.nextLine();//get amount
        if(amountStr.contains("-")){
            System.out.println("you can't enter negative numbers");
            return;
        }
        try{
            //try to str => float
            float amountFloat = Float.parseFloat(amountStr);
            loggedAcc.money -= amountFloat;
        } catch (NumberFormatException e) {
            System.out.println("you can't enter non-number characters");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();//create a Main object to run methods
        main.menu(Objects.requireNonNull(main.startup()));
    }

}