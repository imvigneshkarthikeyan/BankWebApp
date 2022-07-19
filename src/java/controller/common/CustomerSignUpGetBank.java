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

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "CustomerSignUpGetBankServlet", urlPatterns = {"/fetchbank"})
public class CustomerSignUpGetBank implements Action,ServletResponseAware {

    private HttpServletResponse response = null;
    
    @Override
    public String execute() throws ServletException, IOException {
        try {
            JSONArray jsonArray = new JSONArray();
            ArrayList<String> list = CustomerSignUpDAO.fetchBank();
            for (String e : list) {
                jsonArray.put(e);
            }
            response.getWriter().println(jsonArray);
        } catch (Exception ex) {
            response.setStatus(500);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

}
