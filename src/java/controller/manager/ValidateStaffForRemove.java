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
//@WebServlet(name = "ValidateStaffForRemoveServlet", urlPatterns = {"/staffvalidate"})
public class ValidateStaffForRemove implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            if (utilities.Validator.isUserManager((String) request.getSession().getAttribute("currentUser"), request, response)) {
                String jsonData = request.getReader().readLine();
                JSONObject jsonObj = new JSONObject(jsonData);
//                    EmployeeModel employee = StaffDAO.getStaffNameRoleAndBankID(jsonObj.getString("userID"));
//                    int managerBankID = StaffDAO.getStaffBankID((String) request.getSession().getAttribute("currentUser"));
                    if (utilities.Validator.deleteStaffValidator(jsonObj, StaffDAO.getStaffBankID((String) request.getSession().getAttribute("currentUser")), response)) {
                        utilities.Util.setStatusCodeMessage(200, "userName", StaffDAO.getStaffNameRoleAndBankID(jsonObj.getString("userID")).getEmpName(), response);
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