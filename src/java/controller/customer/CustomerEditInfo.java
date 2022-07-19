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
//EditInfo
//@WebServlet(name = "CustomerEditInfoServlet", urlPatterns = {"/editinfo"})
public class CustomerEditInfo implements Action, ServletRequestAware,ServletResponseAware {

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    
    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userId = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserCustomer(userId, response)) {
                String jsonData = request.getReader().readLine();
                JSONObject jsonObj = new JSONObject(jsonData);
                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "select * from customer_schema.customer_details where cust_login_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                if (utilities.Validator.validatorForEditInfo(jsonObj, resultSet.getString("cust_pass"), resultSet.getString("phone_num"), resultSet.getString("address"), response)) {
                    boolean resultOfEditCustomer = CustomerDAO.editCustomer(userId, jsonObj.getString("userPass"), jsonObj.getString("userPhone"), jsonObj.getString("userAddress"));
                    if (!resultOfEditCustomer) {
                        utilities.Util.setStatusCodeMessage(409, "errorMessage", "Error occured, Please try again.", response);
                    } else {
                        utilities.Util.setStatusCodeMessage(201, "successMessage", "User updated successfully!", response);
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
