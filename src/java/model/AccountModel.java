/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.CustomerDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author vignesh-pt5186
 */
public class AccountModel {
    
    private int custID;
    private String custLoginID;
    private int bankID;
    private String accountNumber;
    private int amount;

    
    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }
    
    public String getCustLoginID() {
        return custLoginID;
    }

    public void setCustLoginID(String custLoginID) {
        this.custLoginID = custLoginID;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AccountModel(int custID, String custLoginID, int bankID, String accountNumber, int amount) {
        this.custID = custID;
        this.custLoginID = custLoginID;
        this.bankID = bankID;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
    
    public boolean addMoney(int amountToBeAdded, String accNumFrom, String note) throws SQLException {
        if (amountToBeAdded <= 1000000 && amountToBeAdded > 0) {
            int totalAmount = getAmount()+ amountToBeAdded;
            CustomerDAO.addToTransactionTable(getCustID(), getBankID(), amountToBeAdded, accNumFrom, getAccountNumber(), note);
            return updateAmount(totalAmount);
        }
        return false;
    }

    public boolean withdrawMoney(int amountToWithdraw, String accNumTo, String note) throws SQLException {
        if (getAmount()>= amountToWithdraw && amountToWithdraw <= 1000000) {
            int totalAmount = getAmount() - amountToWithdraw;
            CustomerDAO.addToTransactionTable(getCustID(), getBankID(), -amountToWithdraw, getAccountNumber(), accNumTo, note);
            return updateAmount(totalAmount);
        }
        return false;

    }

    public boolean updateAmount(int newAmount) throws SQLException {
        Connection connection = null;
        //Add Total Amount to DB
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "update account_schema.account_details set amount=? where cust_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newAmount);
            statement.setInt(2, getCustID());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

}
