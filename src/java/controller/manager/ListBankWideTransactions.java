/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

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
//@WebServlet(name = "ListBankWideTransactionsServket", urlPatterns = {"/listallbanktransactions"})
public class ListBankWideTransactions implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserManager(userID, request, response)) {
                JSONArray jsonArray = new JSONArray();
                int bankID = StaffDAO.getStaffBankID(userID);
                utilities.Util.getClassForName();
                Connection connection = utilities.Util.getConnection();
                String query = "select * from account_schema.transactions_table where bank_id='" + bankID + "' order by date_time desc";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    JSONObject json = new JSONObject();
                    json.put("date_time", resultSet.getString("date_time"));
                    json.put("from_acc_no", resultSet.getString("from_acc_no"));
                    json.put("to_acc_no", resultSet.getString("to_acc_no"));
                    json.put("amount_transfered", resultSet.getString("amount_transfered"));
                    json.put("trans_note", resultSet.getString("trans_note"));
                    jsonArray.put(json);
                }
                response.setStatus(200);
                response.getWriter().write(jsonArray.toString());
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
