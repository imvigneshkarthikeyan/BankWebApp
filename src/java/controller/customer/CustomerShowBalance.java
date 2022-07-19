/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import DAO.CustomerDAO;
import DAO.MoneyDAO;
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
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerShowBalanceServlet", urlPatterns = {"/showbalance"})
public class CustomerShowBalance implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
  
    @Override
    public String execute() throws ServletException, IOException {
        try {
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (utilities.Validator.isUserCustomer(userID, response)) {
                int balance = MoneyDAO.getAmountWithUserID(userID);
                if (balance!=-1) {
                    JSONObject json = new JSONObject();
                    json.put("balance", balance);
                    response.setStatus(200);
                    response.getWriter().write(json.toString());
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
