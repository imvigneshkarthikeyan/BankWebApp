/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

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
import model.EmployeeModel;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "AddNewStaff", urlPatterns = {"/addstaff"})
public class AddNewStaff implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            if (utilities.Validator.isUserManager((String) request.getSession().getAttribute("currentUser"), request, response)) {
                String jsonData = request.getReader().readLine();
                JSONObject jsonObj = new JSONObject(jsonData);
//                    String userID = jsonObj.getString("userID");
//                    String userName = jsonObj.getString("userName");
//                    String userPass = jsonObj.getString("pass");
                if (utilities.Validator.addNewStaffValidator(jsonObj, response)) {
                    //Fetching bank ID of the manager
                    int managerBankID = StaffDAO.getStaffBankID((String) request.getSession().getAttribute("currentUser"));
                    EmployeeModel employeeModel = new EmployeeModel(jsonObj.getString("userID"), jsonObj.getString("userName"), jsonObj.getString("pass"), 2, managerBankID);
                    boolean resultOfStaffCreation = StaffDAO.createNewStaff(employeeModel);
                    if (!resultOfStaffCreation) {
                        utilities.Util.setStatusCodeMessage(409, "errorMessage", "Error occured, Please try again.", response);
                    } else {
                        utilities.Util.setStatusCodeMessage(201, "successMessage", "Staff created successfully! The Staff can login with these credentials!", response);
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
