/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import DAO.CustomerDAO;
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
import model.CustomerModel;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerHomeServlet", urlPatterns = {"/customerhome"})

public class CustomerHome implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserCustomer(userID, response)) {
                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "SELECT customer_details.cust_name, customer_details.pan_num, "
                        + "customer_details.aadhar, customer_details.address, bank_details.bank_name, "
                        + "bank_details.branch_name, account_details.account_num FROM customer_schema.customer_details "
                        + "LEFT JOIN bank_schema.bank_details ON customer_details.bank_id = bank_details.bank_id LEFT JOIN account_schema.account_details "
                        + "ON customer_details.cust_id = account_details.cust_id where customer_details.cust_login_id=?";
//            String query = "select * from cust_bank_account_details where cust_login_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userID);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                JSONObject json = new JSONObject();
                json.put("userName", resultSet.getString("cust_name"));
                json.put("aadhar", resultSet.getString("aadhar"));
                json.put("pan", resultSet.getString("pan_num"));
                json.put("bankName", resultSet.getString("bank_name"));
                json.put("bankBranch", resultSet.getString("branch_name"));
                json.put("accountNum", resultSet.getString("account_num"));
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
