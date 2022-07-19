<%-- 
    Document   : index
    Created on : 17 Jun, 2022, 10:11:43 AM
    Author     : vignesh-pt5186
--%>

<%@page import="DAO.CustomerDAO"%>
<%@page import="DAO.StaffDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank App</title>
        <link rel="stylesheet" href="style/style.css">
    </head>
    <body>
    <center>
       <div>
            <h1>Welcome to Bank Application!</h1>
            <%
                String userID = (String) request.getSession().getAttribute("currentUser");
                if (userID == null) {
            %>
            <div id="loginDivision">
                <h3>CUSTOMER LOGIN</h3>
                <h4>Enter your login id and password</h4>
                <br> Login ID: <input type="text "id="id" onkeyup="validateLogin();" autocomplete="off"/> <br>
                <br> Password: <input type="password" id="pass" autocomplete="off"/> <br>
                <br> <div id="msgForUser"></div> <br>
                <button class="btn" id="loginBtn" onclick="logIn()" style="display: none">Log In</button>
                <button class="btn2" onclick="location.href = 'signup.jsp';">Sign Up</button><br>
                <br><a style="color: blue; cursor: pointer; text-decoration: underline;" onclick="openStaffLogin()">Click here for Bank Staff Login</a>
            </div>
            <div id="loginDivisionForStaff" style="display: none;">
                <h3>BANK STAFF LOGIN</h3>
                <h4>Enter your login id and password</h4>
                <br> Login ID: <input type="text" id="idForStaff" onkeyup="validateLoginForStaff();" autocomplete="off"/> <br>
                <br> Password: <input type="password" id="passForStaff" autocomplete="off"/> <br>
                <br> <div id="msgForUserForStaff"></div> <br>
                <button class="btn" id="loginBtnForStaff" onclick="staffLogIn()" style="display: none">Log In</button><br>
                <br><a style="color: blue; cursor: pointer; text-decoration: underline;" onclick="openCustomerLogin()">Click here for Customer Login</a>
            </div>
        </div> 
            <%} else {
                if (CustomerDAO.validateUser(userID)) {
                response.sendRedirect("home.jsp");
            } else if (StaffDAO.validateUser(userID)) {
                response.sendRedirect("staffHome.jsp");
            } else {
                //Remove from session if user is deleted
                request.getSession().removeAttribute("currentUser");
                response.sendRedirect("index.jsp");
            }
        }%>
    </center>
    <script src="js/main.js"></script>
    </body>
</html>
