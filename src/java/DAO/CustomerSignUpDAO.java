/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import model.CustomerModel;

/**
 *
 * @author vignesh-pt5186
 */
public class CustomerSignUpDAO {
    
    public static ArrayList<String> fetchBank() throws SQLException, ClassNotFoundException {
        ArrayList<String> distinctBanks = new ArrayList<>();
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select distinct bank_name from bank_schema.bank_details";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                distinctBanks.add(resultSet.getString("bank_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return distinctBanks;
    }
    
    public static boolean validateBankName(String bankName) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select * from bank_schema.bank_details where bank_name=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bankName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static ArrayList<String> fetchBranchForBank(String bankName) throws SQLException, ClassNotFoundException {
        ArrayList<String> bankBranches = new ArrayList<>();
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select branch_name from bank_schema.bank_details where bank_name='" + bankName + "'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bankBranches.add(resultSet.getString("branch_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return bankBranches;
    }
    
    public static boolean validateBankBranch(String bankName,String branchName) throws SQLException, ClassNotFoundException {
        return fetchBranchForBank(bankName).contains(branchName);
    }
    
    public static boolean validateUserPan(String userPan) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select * from customer_schema.customer_details where pan_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userPan);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static boolean validateUserAadhar(String userAadhar) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select * from customer_schema.customer_details where aadhar=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userAadhar);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static boolean validateUserPhone(String userPhone) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select * from customer_schema.customer_details where phone_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userPhone);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static boolean isPhoneNumberExist(String userPhone, String oldPhone) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select phone_num from customer_schema.customer_details where phone_num=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userPhone);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next() && !oldPhone.equals(userPhone));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static int getBankID(String bankName, String branchName) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select bank_id from bank_schema.bank_details where bank_name=? and branch_name=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bankName);
            statement.setString(2, branchName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
                return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static boolean createNewCustomer(CustomerModel customerModel) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "insert into customer_schema.customer_details (cust_name, cust_login_id, cust_pass, pan_num, aadhar, phone_num, address, bank_id) values (?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customerModel.getUserName());
            statement.setString(2, customerModel.getUserId());
            statement.setString(3, customerModel.getUserPass());
            statement.setString(4, customerModel.getUserPan());
            statement.setString(5, customerModel.getUserAadhar());
            statement.setString(6, customerModel.getUserPhone());
            statement.setString(7, customerModel.getUserAddress());
            int bankId = getBankID(customerModel.getUsersBank(), customerModel.getUsersBankBranch());
            statement.setInt(8, bankId);
            statement.executeUpdate();
            return generateAccount(getCustomerId(customerModel.getUserId()), bankId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static int getCustomerId(String cust_login_id) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "select cust_id from customer_schema.customer_details where cust_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cust_login_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
                return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static boolean generateAccount(int cust_id,int bank_id) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "insert into account_schema.account_details (cust_id, bank_id, amount , account_num) values (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cust_id);
            statement.setInt(2, bank_id);
            statement.setFloat(3, 0);
            statement.setString(4, generateAccountNumber());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
    public static String generateAccountNumber() {
        String numID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        String acc_num = numID.substring(numID.length() - 11);
        return acc_num;
    }
}
