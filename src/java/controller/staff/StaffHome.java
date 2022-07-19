/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import DAO.StaffDAO;
import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
//StaffDetails
//@WebServlet(name = "StaffHomeServlet", urlPatterns = {"/getstaffdata"})
public class StaffHome implements Action, ServletRequestAware,ServletResponseAware {
 
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    
    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserBankEmployee(userID, response)) {
                JSONObject json = new JSONObject();

                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "  SELECT bank_staff_details.emp_name, bank_staff_details.role_id,"
                        + " bank_details.bank_name, bank_details.branch_name"
                        + " FROM bank_schema.bank_staff_details"
                        + " LEFT JOIN bank_schema.bank_details ON bank_staff_details.bank_id = bank_details.bank_id "
                        + " where bank_staff_details.emp_login_id=?";
//            String query = "select * from emp_bank_details where emp_login_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userID);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();

                json.put("name", resultSet.getString("emp_name"));
                if (resultSet.getInt("role_id") == 1) {
                    json.put("roleName", "Manager");
                } else {
                    json.put("roleName", "Staff");
                }
                json.put("bankName", resultSet.getString("bank_name"));
                json.put("branchName", resultSet.getString("branch_name"));
                response.getWriter().write(json.toString());

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
