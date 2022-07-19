/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.CustomerDAO;
import java.sql.SQLException;


/**
 *
 * @author vignesh-pt5186
 */
public class CustomerModel {
    private String userId;
    private String userPass;
    private int custId;
    private String userName;
    private String userPan;
    private String userAadhar;
    private String userPhone;
    private String userAddress;

    //
    private int bankId;
    private String usersBank;
    private String usersBankBranch;
    private String userAccountNumber;
    private int userTotalAmount;
    
    public String getUsersBank() {
        return usersBank;
    }

    public void setUsersBank(String usersBank) {
        this.usersBank = usersBank;
    }

    public String getUsersBankBranch() {
        return usersBankBranch;
    }

    public void setUsersBankBranch(String usersBankBranch) {
        this.usersBankBranch = usersBankBranch;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }

    public int getUserTotalAmount() {
        return userTotalAmount;
    }

    
    public void setUserTotalAmount(int userTotalAmount) {
        this.userTotalAmount = userTotalAmount;
    }
    //
    
    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPan() {
        return userPan;
    }

    public void setUserPan(String userPan) {
        this.userPan = userPan;
    }

    public String getUserAadhar() {
        return userAadhar;
    }

    public void setUserAadhar(String userAadhar) {
        this.userAadhar = userAadhar;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    } 
  
    public CustomerModel(String userName, String userAadhar, String userPan, String usersBank, String usersBankBranch, String userAccountNumber) {
        this.userName = userName;
        this.userAadhar = userAadhar;
        this.userPan = userPan;
        this.usersBank = usersBank;
        this.usersBankBranch = usersBankBranch;
        this.userAccountNumber = userAccountNumber;
    }
    
    public CustomerModel(String userId, String userPhone, String userAddress, String userPass) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userPass = userPass;
    }
    
    public CustomerModel(String userId, String userPass, String userName, String userPan, String userAadhar, String userPhone, String userAddress, String usersBank, String usersBankBranch) {
        this.userId = userId;
        this.userPass = userPass;
        this.userName = userName;
        this.userPan = userPan;
        this.userAadhar = userAadhar;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.usersBank = usersBank;
        this.usersBankBranch = usersBankBranch;
    }
    
    public CustomerModel() {
        this.userId = getUserId();
        this.userPass = getUserPass();
        this.custId = getCustId();
        this.userName = getUserName();
        this.userPan = getUserPan();
        this.userAadhar = getUserAadhar();
        this.userPhone = getUserPhone();
        this.userAddress = getUserAddress();
        this.usersBank = getUsersBank();
        this.usersBankBranch = getUsersBankBranch();
        this.userAccountNumber = getUserAccountNumber();
        this.userTotalAmount = getUserTotalAmount();
    }
    
}
