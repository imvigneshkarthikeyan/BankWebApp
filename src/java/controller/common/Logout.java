/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import com.opensymphony.xwork2.Action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author vignesh-pt5186
 */
//@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class Logout implements Action, ServletRequestAware {
    
    private HttpServletRequest request = null;
 
    @Override
    public String execute() throws ServletException, IOException {
        request.getSession(false).invalidate();
        return null;
     }
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }


}
