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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "ViewAllCustomerServlet", urlPatterns = {"/viewallcustomers"})
public class ViewAllCustomer implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserStaff(userID, request, response)) {
                JSONArray jsonArray = new JSONArray();
                int bankID = StaffDAO.getStaffBankID(userID);
                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "  SELECT account_details.account_num, customer_details.cust_name, customer_details.cust_login_id,"
                        + " customer_details.pan_num, customer_details.aadhar, customer_details.phone_num, customer_details.address,"
                        + " account_details.amount, customer_details.bank_id  FROM customer_schema.customer_details "
                        + " LEFT JOIN account_schema.account_details ON customer_details.cust_id = account_details.cust_id "
                        + " where customer_details.bank_id ='" + bankID + "'";
//                String query = "select * from cust_details_for_bank where bank_id='" + bankID + "'";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    JSONObject json = new JSONObject();
                    json.put("account_num", resultSet.getString("account_num"));
                    json.put("cust_name", resultSet.getString("cust_name"));
                    json.put("cust_login_id", resultSet.getString("cust_login_id"));
                    json.put("pan_num", resultSet.getString("pan_num"));
                    json.put("aadhar", resultSet.getString("aadhar"));
                    json.put("phone_num", resultSet.getString("phone_num"));
                    json.put("address", resultSet.getString("address"));
                    json.put("amount", resultSet.getString("amount"));
                    jsonArray.put(json);
                }
                response.setStatus(200);
                response.getWriter().write(jsonArray.toString());
            }
        } catch (Exception ex) {
            response.setStatus(500);
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
