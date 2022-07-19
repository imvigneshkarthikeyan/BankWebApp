/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import DAO.CustomerDAO;
import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;


/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerLoginServlet", urlPatterns = {"/login"})
public class CustomerLogin implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    
    @Override
    public String execute() throws ServletException, IOException {

        try {
            String jsonData = request.getReader().readLine();
            JSONObject jsonObj = new JSONObject(jsonData);
            if (utilities.Validator.validatorForLogin(jsonObj, response)) {
                int result = CustomerDAO.validateUser(jsonObj.getString("userId"), jsonObj.getString("userPass"));
                switch (result) {
                    case 0:
                        utilities.Util.setStatusCodeMessage(400, "errorMessage", "Invalid User, try again or sign up if you are a new user", response);
                        break;
                    case -1:
                        utilities.Util.setStatusCodeMessage(400, "errorMessage", "Invalid Password", response);
                        break;
                    case 1:
                        utilities.Util.setStatusCodeMessage(200, "success", "Login success", response);
                        HttpSession session = request.getSession();
                        session.setAttribute("currentUser", jsonObj.getString("userId"));
                        break;
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
