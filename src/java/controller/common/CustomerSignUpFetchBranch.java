/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import DAO.CustomerSignUpDAO;
import com.opensymphony.xwork2.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerSignUpFetchBranch", urlPatterns = {"/fetchbranch"})
public class CustomerSignUpFetchBranch implements Action, ServletRequestAware,ServletResponseAware {
    
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    @Override
    public String execute() throws ServletException, IOException {
        try {
            String jsonData = request.getReader().readLine();
            JSONObject jsonObj = new JSONObject(jsonData);
            if (utilities.Validator.bankNameValidatorForSignUp(jsonObj, response)) {
                JSONArray jsonArray = new JSONArray();
                ArrayList<String> list = CustomerSignUpDAO.fetchBranchForBank(jsonObj.getString("bankName"));
                for (String e : list) {
                    jsonArray.put(e);
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
