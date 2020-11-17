package view;

import model.BankAccount;
import repository.AccountRepository;
import util.Luhn;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountRepository accountRepository;

    public View(String fileName) {
        this.accountRepository = new AccountRepository(fileName);
    }

    public void start() {
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            System.out.print(">");
            int input = scanner.nextInt();
            System.out.println();

            switch (input) {
                case 1: //create
                    BankAccount account = new BankAccount();
                    accountRepository.add(account);
                    System.out.println("Your card has been created");
                    System.out.println("Your card number:");
                    System.out.println(account.getNumber());
                    System.out.println("Your card PIN:");
                    System.out.println(account.getPIN());
                    System.out.println();
                    break;

                case 2: //login
                    if (accountRepository.isEmpty()) {
                        break;
                    }

                    boolean auth = true;
                    System.out.println("Enter your card number:");
                    System.out.print(">");
                    String number = scanner.next();

                    if (accountRepository.getByNumber(number) == null) {
                        System.out.println("Such a card does not exist.\n");
                        break;
                    } else {
                        account = accountRepository.getByNumber(number);
                    }
                    //
                    String loggedPerson = account.getNumber();
                    //
                    if (!(account.getNumber().equals(number))) {
                        auth = false;
                        loggedPerson = null;
                    }

                    System.out.println("Enter your PIN:");
                    System.out.print(">");

                    if (!(account.getPIN().equals(scanner.next()))) {
                        auth = false;
                        loggedPerson = null;
                    }
                    if (auth) {
                        boolean loggedIn = true;
                        System.out.println("\nYou have successfully logged in!\n");
                        while (loggedIn) {
                            System.out.println("1. Balance");
                            System.out.println("2. Add income");
                            System.out.println("3. Do transfer");
                            System.out.println("4. Close account");
                            System.out.println("5. Log out");
                            System.out.println("0. Exit");
                            System.out.print(">");
                            input = scanner.nextInt();
                            System.out.println();

                            switch (input) {
                                case 1: //balance
                                    System.out.println("Balance:" + accountRepository.getBalance(loggedPerson) + " \n");
                                    break;

                                case 2: //add income
                                    System.out.println("Enter income:");
                                    System.out.print(">");
                                    int income = scanner.nextInt();

                                    accountRepository.changeBalanceOfNumber(loggedPerson, income);
                                    System.out.println("Income was added!\n");
                                    break;

                                case 3: //do transfer
                                    System.out.println("Transfer");
                                    System.out.println("Enter card number:");
                                    System.out.print(">");
                                    String transferNumber = scanner.next();

                                    if (!Luhn.isValid(transferNumber)) {
                                        System.out.println("Probably you made mistake in the card number. Please try again!\n");
                                        break;
                                    }
                                    if (accountRepository.getByNumber(transferNumber) == null) {
                                        System.out.println("Such a card does not exist.\n");
                                        break;
                                    }

                                    BankAccount receiver = accountRepository.getByNumber(transferNumber);

                                    if (account.getNumber().equals(transferNumber)) {
                                        System.out.println("You can't transfer money to the same account!\n");
                                        break;
                                    }
                                    if (accountRepository.getByNumber(transferNumber) == null) {
                                        System.out.println("Such a card does not exist.\n");
                                        break;
                                    }

                                    System.out.println("Enter how much money you want to transfer");
                                    System.out.print(">");
                                    int transfer = scanner.nextInt();
                                    account = accountRepository.getByNumber(loggedPerson);

                                    if (transfer > account.getBalance()) {
                                        System.out.println("Not enough money!\n");
                                        break;
                                    }

                                    accountRepository.changeBalanceOfNumber(loggedPerson, -transfer);
                                    accountRepository.changeBalanceOfNumber(receiver.getNumber(), transfer);
                                    System.out.println("Success!\n");
                                    break;

                                case 4: //close account
                                    accountRepository.deleteByNumber(loggedPerson);
                                    System.out.println("The account has been closed!\n");
                                    loggedIn = false;
                                    loggedPerson = null;
                                    break;

                                case 5: //logout
                                    System.out.println("You have successfully logged out!\n");
                                    loggedIn = false;
                                    loggedPerson = null;
                                    break;

                                case 0: //exit
                                    System.out.println("Bye!");
                                    return;

                                default:
                                    break;
                            }
                        }
                    } else {
                        System.out.println("\nWrong card number or PIN!\n");
                    }
                    break;

                case 0: //exit
                    System.out.println("Bye!");
                    return;

                default:
                    //continue
            }
        }
    }
}

//    Random random = new Random();
//    long MII = random.nextInt(10);
//    long BIN = random.nextInt(100_000);
//    long identifier = random.nextInt(1_000_000_000 - 100_000_000 + 1) + 100_000_000;
//    int checksum = random.nextInt(10);
//    long number = MII * 1_000_000_000_000_000L
//            + BIN * 10_000_000_000L
//            + identifier * 10
//            + checksum;
//    int PIN = random.nextInt(10_000 - 1_000 + 1) + 1_000;
//    account = new BankAccount(number,PIN);
