/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import DAO.MoneyDAO;
import DAO.CustomerDAO;
import DAO.StaffDAO;
import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AccountModel;
import model.CustomerModel;
import model.EmployeeModel;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "WithdrawMoneyServlet", urlPatterns = {"/withdraw"})
public class WithdrawMoney implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserStaff(userID, request, response)) {
                String jsonData = request.getReader().readLine();
                JSONObject jsonObj = new JSONObject(jsonData);
//                    int empBankID = StaffDAO.getStaffBankID(userID);
                if (utilities.Validator.accountAndAmountValidatorForWithdraw(jsonObj, StaffDAO.getStaffBankID(userID), response)) {
                    AccountModel accountModel = MoneyDAO.getAccountDetailsWithAccountNumber(jsonObj.getString("accNum"));
                    boolean resultOfWithdrawMoney = accountModel.withdrawMoney(jsonObj.getInt("amount"), jsonObj.getString("accNum"), jsonObj.getString("note"));
                    if (!resultOfWithdrawMoney) {
                        utilities.Util.setStatusCodeMessage(409, "errorMessage", "Error occured, Please try again.", response);
                    } else {
                        utilities.Util.setStatusCodeMessage(201, "successMessage", "Money withdrawn successfully!", response);
                    }
                }

            }
        } catch (Exception ex) {
            response.setStatus(500);
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    
}
