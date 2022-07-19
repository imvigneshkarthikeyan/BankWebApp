/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.CustomerModel;

/**
 *
 * @author vignesh-pt5186
 */
public class CustomerDAO {
    
    public static boolean validateUser(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select * from customer_schema.customer_details where cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    public static int validateUser(String userId, String userPass) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select cust_pass from customer_schema.customer_details where cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            } else if (!resultSet.getString("cust_pass").equals(userPass)) {
                return -1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static boolean editCustomer(String userId, String userPass, String userPhone, String userAddress) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "update customer_schema.customer_details set cust_pass=?, phone_num=?, address=? where cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userPass);
            statement.setString(2, userPhone);
            statement.setString(3, userAddress);
            statement.setString(4, userId);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static boolean validateAccountNumber(String accNum) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select account_num from account_schema.account_details where account_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accNum);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static boolean validateAccountAndBank(String accNum, int bankID) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select bank_id from account_schema.account_details where account_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accNum);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int accBankID = resultSet.getInt("bank_id");
            return accBankID==bankID;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
      
    public static void addToTransactionTable(int custId, int bankId, int amount,String fromAccNum, String accNumTo, String note) throws SQLException {
        Connection connection = null;
        //Add Total Amount to DB
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "insert into account_schema.transactions_table(cust_id, bank_id, from_acc_no, to_acc_no, amount_transfered, date_time, trans_note) values(?,?,?,?,?, to_char(now(), 'DD-MM-YYYY | HH24:MI'),?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, custId);
            statement.setInt(2, bankId);
            statement.setString(3, fromAccNum);
            statement.setString(4, accNumTo);
            statement.setInt(5, amount);
            statement.setString(6, note);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

}
