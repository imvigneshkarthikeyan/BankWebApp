/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AccountModel;
/**
 *
 * @author vignesh-pt5186
 */
public class MoneyDAO {

    
    public static AccountModel getAccountDetailsWithAccountNumber(String accNum) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_schema.account_details.account_id, account_details.account_num, account_details.amount, "
                    + "account_details.bank_id, account_details.cust_id, customer_details.cust_login_id from account_schema.account_details "
                    + "left join customer_schema.customer_details on account_details.cust_id=customer_details.cust_id "
                    + "where account_details.account_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accNum);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AccountModel accountModel = new AccountModel(resultSet.getInt("cust_id"), resultSet.getString("cust_login_id"), resultSet.getInt("bank_id") , resultSet.getString("account_num"), resultSet.getInt("amount"));
                return accountModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
    
    public static int getAmountWithAccountNumber(String accNum) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_details.amount from account_schema.account_details where account_details.account_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accNum);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return -1;
    }
    
    
    public static String getAccountNumberWithUserID(String userID) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_details.account_num from account_schema.account_details left join customer_schema.customer_details "
                    + "on account_details.cust_id=customer_details.cust_id where customer_details.cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
//                AccountModel accountModel = new AccountModel(resultSet.getInt("cust_id"), resultSet.getString("cust_login_id"), resultSet.getInt("bank_id"), resultSet.getString("account_num"), resultSet.getInt("amount"));
                return resultSet.getString("account_num");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
    
    public static AccountModel getAccountDetailsWithUserID(String userID) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_schema.account_details.account_id, account_details.account_num, account_details.amount, "
                    + "account_details.bank_id, account_details.cust_id, customer_details.cust_login_id from account_schema.account_details "
                    + "left join customer_schema.customer_details on account_details.cust_id=customer_details.cust_id "
                    + "where customer_details.cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AccountModel accountModel = new AccountModel(resultSet.getInt("cust_id"), resultSet.getString("cust_login_id"), resultSet.getInt("bank_id"), resultSet.getString("account_num"), resultSet.getInt("amount"));
                return accountModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
    
    public static int getAmountWithUserID(String userID) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_details.amount from account_schema.account_details left join customer_schema.customer_details "
                    + "on account_details.cust_id=customer_details.cust_id where customer_details.cust_login_id=?";
//            String query = "select account_schema.account_details.account_id, account_details.account_num, account_details.amount, account_details.bank_id, account_details.cust_id, customer_details.cust_login_id from account_schema.account_details left join customer_schema.customer_details on account_details.cust_id=customer_details.cust_id where customer_details.cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return -1;
    }
    
}
