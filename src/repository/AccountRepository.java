package repository;

import model.BankAccount;
import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class AccountRepository {
    private final SQLiteDataSource dataSource;

    public AccountRepository(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(BankAccount bankAccount) {
        String number = bankAccount.getNumber();
        String pin = bankAccount.getPIN();
        String values = number + ", " + pin;

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO card (number, pin) VALUES(" + values + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getBalance(String cardNumber) {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            ResultSet resultSet =
                    statement.executeQuery("SELECT * FROM card WHERE number = " + cardNumber);
            resultSet.next();
            return resultSet.getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isEmpty() {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            ResultSet resultSet =
                    statement.executeQuery("SELECT * FROM card");
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public BankAccount getByNumber(String number) {
        BankAccount bankAccount = new BankAccount();
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            ResultSet resultSet =
                    statement.executeQuery("SELECT * FROM card WHERE number = " + number);
            resultSet.next();
            bankAccount.setNumber(resultSet.getString("number"));
            bankAccount.setPIN(resultSet.getString("pin"));
            bankAccount.setBalance(resultSet.getInt("balance"));
        } catch (SQLException e) {
            return null;
        }
        return bankAccount;
    }

    public void deleteByNumber(String number) {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM card WHERE number = " + number);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeBalanceOfNumber(String number, Integer income) {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE card SET balance = balance +" + income + " WHERE number = " + number);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
