/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import DAO.CustomerSignUpDAO;
import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CustomerModel;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerSignUpCreateNew", urlPatterns = {"/createnew"})
public class CustomerSignUpCreateNew implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String jsonData = request.getReader().readLine();
            JSONObject jsonObj = new JSONObject(jsonData);
            if (utilities.Validator.validatorForSignUp(jsonObj, response)) {
                CustomerModel customerModel = new CustomerModel(jsonObj.getString("userId"), jsonObj.getString("userPass"), jsonObj.getString("userName"), jsonObj.getString("userPan"), jsonObj.getString("userAadhar"), jsonObj.getString("userPhone"), jsonObj.getString("userAddress"), jsonObj.getString("userBankName"), jsonObj.getString("userBranchName"));
                boolean resultOfCustomerCreation = CustomerSignUpDAO.createNewCustomer(customerModel);
                if (!resultOfCustomerCreation) {
                    utilities.Util.setStatusCodeMessage(409, "errorMessage", "Error occured, Please try again.", response);
                } else {
                    utilities.Util.setStatusCodeMessage(201, "successMessage", "User created successfully! Select Go to Login button to login.", response);
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
