/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import DAO.CustomerDAO;
import DAO.CustomerSignUpDAO;
import DAO.MoneyDAO;
import DAO.StaffDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AccountModel;
import model.EmployeeModel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
public class Validator {
    
    public static boolean isUserManager(String userID, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, JSONException, IOException {
        if (request.getSession().getAttribute("currentUser") == null || StaffDAO.getStaffRoleID(userID) !=1) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
    
    public static boolean isUserStaff(String userID, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, JSONException, IOException {
        if (request.getSession().getAttribute("currentUser") == null || StaffDAO.getStaffRoleID(userID) != 2) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
    
    public static boolean isUserBankEmployee(String userID, HttpServletResponse response) throws SQLException, ClassNotFoundException, JSONException, IOException {
        if (userID == null || !StaffDAO.validateUser(userID)) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
    
    public static boolean isUserCustomer(String userID, HttpServletResponse response) throws SQLException, ClassNotFoundException, JSONException, IOException {
        if (userID == null || !CustomerDAO.validateUser(userID)) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
    
    public static boolean accountAndAmountValidatorForAdd(JSONObject jsonObj, int empBankID, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("amount") && jsonObj.has("accNum") && jsonObj.has("note")) {
            String accNum = jsonObj.getString("accNum");
            String note = jsonObj.getString("note");
//            AccountModel accountModel = MoneyDAO.getAccountDetailsWithAccountNumber(jsonObj.getString("accNum"));
            if (accNum.isEmpty()) {
                message = "Please enter the account number.";
            } else if (!utilities.Util.isDigit(accNum)) {
                message = "The account number is invalid, don't enter special characters.";
            } else if (!utilities.Util.digitValidator(accNum, 11)) {
                message = "The account number is invalid, please enter 11 digit account number.";
            } else if (!CustomerDAO.validateAccountNumber(accNum)) {
                message = "Invalid account number, please try again";
            } else if (!CustomerDAO.validateAccountAndBank(accNum, empBankID)) {
                message = "Please don't enter the account number from other bank/branch.";
            } else if (!utilities.Util.isDigit((String) jsonObj.get("amount"))) {
                message = "Don't enter special characters, Please enter a valid amount";
            } else if (jsonObj.getInt("amount") > 1000000) {
                message = "Try to deposit amount less than ₹1000000.";
            } else if (jsonObj.getInt("amount") < 1) {
                message = "Try to deposit amount minimum of than ₹1.";
            } else if (note.length() > 20) {
                message = "The note can be maximum 20 characters";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean accountAndAmountValidatorForWithdraw(JSONObject jsonObj, int empBankID, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message =  null;
        int statusCode = 400;
        if (jsonObj.has("amount") && jsonObj.has("accNum") && jsonObj.has("note")) {
            String accNum = jsonObj.getString("accNum");
            String note = jsonObj.getString("note");
//            AccountModel accountModel = MoneyDAO.getAccountDetailsWithAccountNumber(jsonObj.getString("accNum"));
            if (accNum.isEmpty()) {
                message = "Please enter the account number.";
            } else if (!utilities.Util.isDigit(accNum)) {
                message = "The account number is invalid, don't enter special characters.";
            } else if (!utilities.Util.digitValidator(accNum, 11)) {
                message = "The account number is invalid, please enter 11 digit account number.";
            } else if (!CustomerDAO.validateAccountNumber(accNum)) {
                message = "Invalid account number, please try again";
            } else if (!CustomerDAO.validateAccountAndBank(accNum, empBankID)) {
                message = "Please don't enter the account number from other bank/branch.";
            } else if (!utilities.Util.isDigit((String) jsonObj.get("amount"))) {
                message = "Don't enter special characters, Please enter a valid amount";
            }
            int balance = MoneyDAO.getAmountWithAccountNumber(accNum);
            if (balance < jsonObj.getInt("amount")) {
                message = "Try entering value less than your balance. Your Balance is " + balance;
            } else if (1000000 < jsonObj.getInt("amount")) {
                message = "Try to Withdraw maximum of ₹1000000";
            } else if (jsonObj.getInt("amount") < 1) {
                message = "Try to withdraw minimum ₹1";
            } else if (note.length() > 20) {
                message = "The note can be maximum 20 characters";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean staffIDValidator(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userID")) {
            String userID = jsonObj.getString("userID");
            if (userID.length() < 5 || userID.length() > 320) {
                message = "Login ID must be minimum 5 characters and maximum of 320 characters";
            } else if (!utilities.Util.isEmailValid(userID)) {
                message = "Enter a valid login ID";
            } else if (StaffDAO.validateUser(userID)) {
                statusCode = 409;
                message = "This login ID already exists, please try again with different ID";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean addNewStaffValidator(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userID") && jsonObj.has("userName") && jsonObj.has("pass")) {
            String userID = jsonObj.getString("userID");
            String userName = jsonObj.getString("userName");
            String userPass = jsonObj.getString("pass");
            if (userID.length() < 5 || userID.length() > 320) {
                message = "Login ID must be minimum 5 characters and maximum of 320 characters";
            } else if (!utilities.Util.isEmailValid(userID)) {
                message = "Enter a valid login ID";
            } else if (StaffDAO.validateUser(userID)) {
                statusCode = 409;
                message = "This login ID already exists, please try again with different ID";
            } else if (userName.length() < 1 || userName.length() > 30) {
                message = "The name should be minimum of 1 character to maximum of 30 characters only.";
            } else if (userPass.length() < 3 || userPass.length() > 16) {
                message = "Password should be minimum 3 characters and maximum of 16 characters";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        }         
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean deleteStaffValidator(JSONObject jsonObj, int managerBankID, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userID")) {
            EmployeeModel employee = StaffDAO.getStaffDetails(jsonObj.getString("userID"));
            if (!utilities.Util.isEmailValid(jsonObj.getString("userID"))) {
                message = "Enter a valid login ID";
            } else if (!StaffDAO.validateUser(jsonObj.getString("userID"))) {
                message = "This login ID doesn't exist, please try again with different ID";
            } else if (employee.getEmpBankID() != managerBankID || employee.getEmpRoleID() == 1) {
                statusCode = 403;
                message = "You don't have access to delete this ID, please try again with different ID";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean accountNumberValidator(JSONObject jsonObj, String userID, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("accNum")) {
            int empBankID = StaffDAO.getStaffBankID(userID);
            String accNum = jsonObj.getString("accNum");
//            AccountModel accountModel = MoneyDAO.getAccountDetailsWithAccountNumber(jsonObj.getString("accNum"));
            if (accNum.isEmpty()) {
                message = "Please enter account Number.";
            } else if (!utilities.Util.isDigit(accNum)) {
                message = "The account number is invalid, don't enter special characters.";
            } else if (!utilities.Util.digitValidator(accNum, 11)) {
                message = "The account number is invalid, please enter 11 digit account number.";
            } else if (!CustomerDAO.validateAccountNumber(accNum)) {
                message = "Invalid account number, please try again";
            } else if (!CustomerDAO.validateAccountAndBank(accNum, empBankID)) {
                message = "Please don't enter the account number from other bank/branch.";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean validatorForSignUp(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userName") && jsonObj.has("userId") && jsonObj.has("userPass") && jsonObj.has("userAadhar") && jsonObj.has("userPan") && jsonObj.has("userPhone") && jsonObj.has("userAddress") && jsonObj.has("userBankName") && jsonObj.has("userBranchName")) {
            
            String userName = jsonObj.getString("userName");
            String userId = jsonObj.getString("userId");
            String userPass = jsonObj.getString("userPass");
            String aadhar = jsonObj.getString("userAadhar");
            String pan = jsonObj.getString("userPan");
            String phone = jsonObj.getString("userPhone");
            String address = jsonObj.getString("userAddress");
            String bank = jsonObj.getString("userBankName");
            String branch = jsonObj.getString("userBranchName");

            if (userName.length() < 1 || userName.length() > 30) {
                message = "User Name should be minimum of 1 character to maximum of 30 characters";
            } else if (userId.length() < 5 || userId.length() > 320) {
                message = "Login ID must be minimum 5 characters and maximum of 320 characters";
            } else if (!utilities.Util.isEmailValid(userId)) {
                message = "Invalid mail id, Please try again.";
            } else if (CustomerDAO.validateUser(userId)) {
                message = "User already exists, go back and login or try creating different user id";
            } else if (userPass.length() < 3 || userPass.length() > 16) {
                message = "Password should be minimum 3 characters and maximum of 16 characters";
            } else if (!CustomerSignUpDAO.validateBankName(bank)) {
                message = "Bank doesn't exists, refresh and try again";
            } else if (!CustomerSignUpDAO.validateBankBranch(bank, branch)) {
                message = "The branch doesn't exist, please refresh and try again.";
            } else if (!utilities.Util.isDigit(phone)) {
                message = "Phone number should not have text, please enter number.";
            } else if (!utilities.Util.digitValidator(phone, 10)) {
                message = "Invalid phone number, Please try again(10 digits is valid).";
            } else if (!utilities.Util.digitValidator(pan, 10)) {
                message = "Invalid PAN number, Please try again(10 digits is valid).";
            } else if (!utilities.Util.isDigit(aadhar)) {
                message = "Aadhar should not have text, please enter number.";
            } else if (!utilities.Util.digitValidator(aadhar, 12)) {
                message = "Invalid Aadhar number, Please try again(12 digits is valid).";
            } else if (address.length() < 3 || address.length() > 50) {
                message = "Address should be minimum 3 characters and maximum 50 characters.";
            } else if (CustomerSignUpDAO.validateUserPan(pan)) {
                message = "PAN number already exists, go back and login or try giving different PAN number";
            } else if (CustomerSignUpDAO.validateUserAadhar(aadhar)) {
                message = "Aadhar number already exists, go back and login or try giving different Aadhar number";
            } else if (CustomerSignUpDAO.validateUserPhone(phone)) {
                message = "Phone number already exists, go back and login or try giving different phone number";
            }
            
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        }
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        }  else {
            return true;
        }
    }
    
    public static boolean bankNameValidatorForSignUp(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        if (jsonObj.has("bankName")) {
            if (!CustomerSignUpDAO.validateBankName(jsonObj.getString("bankName"))) {
                setErrorMsgStatus(409, "Bank doesn't exists, refresh and try again", response);
                return false;
            }
        } else {
            setErrorMsgStatus(503, "Please refresh and try again, couldn't get the data", response);
            return false;
        } 
        return true;
    }
    
    public static boolean branchNameValidatorForSignUp(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        if (jsonObj.has("bankName") && jsonObj.has("branchName")) {
            String bankName = jsonObj.getString("bankName");
            String branchName = jsonObj.getString("branchName");
            if (!CustomerSignUpDAO.validateBankBranch(bankName, branchName)) {
                setErrorMsgStatus(409, "The branch doesn't exist, please refresh and try again.", response);
                return false;
            }
        } else {
            setErrorMsgStatus(503, "Please refresh and try again, couldn't get the data", response);
            return false;
        } 
        return true;
    }
    
    public static boolean validatorForSignUpNameAndMail(JSONObject jsonObj,  HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userName") && jsonObj.has("userId")) {
            String userName = jsonObj.getString("userName");
            String userID = jsonObj.getString("userId");
            if (userName.length() < 1 || userName.length() > 30) {
                message = "User Name should be minimum of 1 character to maximum of 30 characters";
            } else if (userID.length() < 5 || userID.length() > 320) {
                message = "Login ID must be minimum 5 characters and maximum of 320 characters";
            } else if (!utilities.Util.isEmailValid(userID)) {
                message = "Invalid mail id, Please try again.";
            } else if (CustomerDAO.validateUser(userID)) {
                statusCode = 409;
                message = "User already exists, go back and login or try creating different user id";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        }     
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean validatorForLogin(JSONObject jsonObj, HttpServletResponse response) throws JSONException, IOException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userId") && jsonObj.has("userPass")) {
            String userId = jsonObj.getString("userId");
            String userPass = jsonObj.getString("userPass");
            if (userId.isEmpty() || userPass.isEmpty()) {
                message = "Enter all the details";
            } else if (!utilities.Util.isEmailValid(userId)) {
                message = "Enter a valid mail id";
            } else if (userPass.length() < 3 || userPass.length() > 16) {
                message = "Password should be minimum 3 characters and maximum of 16 characters";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        }
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
        
    }
    
    public static boolean validatorForAccountNumber(JSONObject jsonObj, String userAccountNum, HttpServletResponse response) throws JSONException, IOException, SQLException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("accNum")) {
            String accNum = jsonObj.getString("accNum");
            if (accNum.isEmpty()) {
                message = "Please enter account Number.";
            } else if (!utilities.Util.isDigit(accNum)) {
                message = "The account number is invalid, don't enter special characters.";
            } else if (!utilities.Util.digitValidator(accNum, 11)) {
                message = "The account number is invalid, please enter 11 digit account number.";
            } else if (!CustomerDAO.validateAccountNumber(accNum)) {
                message = "Invalid account number, please try again";
            } else if (accNum.equals(userAccountNum)) {
                message = "Please don't enter your account number";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        }  else {
            return true;
        }
    }
    
    public static boolean validatorForEditInfo(JSONObject jsonObj, String oldUserPass, String oldUserPhone, String oldUserAddress, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("userPass") && jsonObj.has("userPhone") && jsonObj.has("userAddress")) {
            String userPass = jsonObj.getString("userPass");
            String userPhone = jsonObj.getString("userPhone");
            String userAddress = jsonObj.getString("userAddress");
            if (userPass.isEmpty() || userPhone.isEmpty() || userAddress.isEmpty()) {
                message = "Please, enter all the details.";
            } else if (oldUserPass.equals(userPass) && oldUserPhone.equals(userPhone) && oldUserAddress.equals(userAddress)) {
                message = "Please modify any one of the details, to update";
            } else if (!utilities.Util.isDigit(userPhone)) {
                message = "Please enter valid mobile number";
            } else if (userPass.length() < 3 || userPass.length() > 16) {
                message = "Password should be minimum 3 characters and maximum of 16 characters";
            } else if (!utilities.Util.digitValidator(userPhone, 10)) {
                message = "Please enter valid 10 digit number";
            } else if (userAddress.length() < 3 || userAddress.length() > 50) {
                message = "Address should be minimum 3 characters and maximum of 50 characters";
//            } else if (CustomerSignUpDAO.validateUserPhone(userPhone) && !oldUserPhone.equals(userPhone)) {
            } else if (CustomerSignUpDAO.isPhoneNumberExist(userPhone, oldUserPhone)) {
                statusCode = 409;
                message = "This number already exists, Please try again.";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean validatorForTransferFund(JSONObject jsonObj, AccountModel accountModel, HttpServletResponse response) throws JSONException, IOException, SQLException, ClassNotFoundException {
        String message = null;
        int statusCode = 400;
        if (jsonObj.has("amount") && jsonObj.has("accNum") && jsonObj.has("note")) {
            String accNum = jsonObj.getString("accNum");
            String note = jsonObj.getString("note");
            if (jsonObj.getString("amount").isEmpty()) {
                message = "Please enter the amount.";
            } else if (!utilities.Util.isDigit((String) jsonObj.get("amount"))) {
                message = "Don't enter special characters, Please enter a valid amount";
            } else if (accNum.isEmpty()) {
                message = "Please enter account Number.";
            } else if (!utilities.Util.isDigit(accNum)) {
                message = "The account number is invalid, don't enter special characters.";
            } else if (!utilities.Util.digitValidator(accNum, 11)) {
                message = "The account number is invalid, please enter 11 digit account number.";
            } else if (!CustomerDAO.validateAccountNumber(accNum)) {
                message = "Invalid account number, please try again";
            } else if (accNum.equals(accountModel.getAccountNumber())) {
                message = "Please don't enter your account number";
            } else if (!utilities.Util.isDigit((String) jsonObj.get("amount"))) {
                message = "Don't enter special characters, Please enter a valid amount";
            } else if (jsonObj.getInt("amount") < 1) {
                message = "Please enter a minimum of ₹ 1.";
            } else if (accountModel.getAmount() < jsonObj.getInt("amount")) {
                message = "Try entering value less than your balance. Your Balance is " + accountModel.getAmount();
            } else if (1000000 < jsonObj.getInt("amount")) {
                message = "The maximum transaction is ₹1000000";
            } else if (note.length() > 20) {
                message = "The note can be maximum 20 characters";
            }
        } else {
            statusCode = 503;
            message = "Please refresh and try again, couldn't get the data";
        } 
        if (message != null) {
            setErrorMsgStatus(statusCode, message, response);
            return false;
        } else {
            return true;
        }
    }
    
    public static void setErrorMsgStatus(int statusCode, String message, HttpServletResponse response) throws JSONException, IOException {
        utilities.Util.setStatusCodeMessage(statusCode, "errorMessage", message, response);
    }
    
}
