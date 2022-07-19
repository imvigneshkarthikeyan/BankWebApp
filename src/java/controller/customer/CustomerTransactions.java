/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import DAO.CustomerDAO;
import DAO.MoneyDAO;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerTransactionsServlet", urlPatterns = {"/customerhistory"})
public class CustomerTransactions implements Action, ServletRequestAware,ServletResponseAware {

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    
    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserCustomer(userID, response)) {
                JSONArray jsonArray = new JSONArray();
                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "SELECT transactions_table.trans_id, transactions_table.from_acc_no, transactions_table.to_acc_no, "
                        + " transactions_table.amount_transfered, transactions_table.date_time, transactions_table.trans_note,"
                        + " customer_details.cust_login_id FROM account_schema.transactions_table"
                        + " LEFT JOIN customer_schema.customer_details ON transactions_table.cust_id = customer_details.cust_id "
                        + " where customer_details.cust_login_id='" + userID + "'order by date_time desc";
//                String query = "select * from cust_history_trans where cust_login_id='" + userID + "'";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                int balance = MoneyDAO.getAmountWithUserID(userID);
                if (balance!=-1) {
                    while (resultSet.next()) {
                        JSONObject json = new JSONObject();
                        json.put("date_time", resultSet.getString("date_time"));
                        json.put("from_acc_no", resultSet.getString("from_acc_no"));
                        json.put("to_acc_no", resultSet.getString("to_acc_no"));
                        json.put("amount_transfered", resultSet.getInt("amount_transfered"));
                        json.put("balance", balance);
                        balance = balance - resultSet.getInt("amount_transfered");
                        json.put("trans_note", resultSet.getString("trans_note"));
                        jsonArray.put(json);
                    }
                    response.setStatus(200);
                    response.getWriter().write(jsonArray.toString());
                } else {
                    utilities.Util.setStatusCodeMessage(400, "errorMessage", "Error occured in calcuating balance, try again!", response);
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
