package model;

import util.Luhn;

import java.util.Random;

public class BankAccount {
    private String number;
    private String PIN;
    private int balance;

    public BankAccount() {
        this.number = generateNumber();
        this.PIN = generatePIN();
    }

    public static String generateNumber() {
        Random random = new Random();
        int identifier = random.nextInt(1_000_000_000 - 100_000_000 + 1)
                + 100_000_000; //9 digits
        long number = 400_000 * 1_000_000_000L
                + identifier; //15 digits
        int checksum = Luhn.getChecksum(number);
        number = number * 10 + checksum; //16 digits
        return String.valueOf(number);
    }

    public static String generatePIN() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10_000 - 1_000 + 1) + 1_000); //4 digits
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                ", number=" + number +
                ", PIN=" + PIN +
                ", balance=" + balance +
                '}';
    }
}
