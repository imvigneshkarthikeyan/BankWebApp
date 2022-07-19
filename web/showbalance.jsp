<%-- 
    Document   : showbalance
    Created on : 16 Jul, 2022, 4:07:53 PM
    Author     : vignesh-pt5186
--%>

<%@page import="DAO.CustomerDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Balance</title>
        <link rel="stylesheet" href="style/style.css">
    </head>
    <body>
    <center>
        <%
            String userID = (String) request.getSession().getAttribute("currentUser");
            if (userID != null && CustomerDAO.validateUser(userID)) {
        %>
        <h1>Welcome to Bank Application!</h1>

        <div id="userFunctions" class="btn-group" style="width:100%;">
            <button id="HomeBtn" style="width:100%" onclick="location.href = 'home.jsp'">Go To Home</button>
        </div>
        <div id="showBalanceDiv">
            <h3>Balance Available</h3>
            <p id="balanceAvailableField" style="font-size:72px;"> </p>
        </div>
        <script>
        window.onload = function () {
            var request = new XMLHttpRequest();
            request.open("GET", "CustomerBalance", true);
            request.setRequestHeader("Content-type", "application/json");
            request.send();
            request.onload = function () {
                if (this.status === 401) {
                    location.href = 'index.jsp';
                } else if (this.status === 200) {
                    responseObj = JSON.parse(this.responseText);
                    document.getElementById("balanceAvailableField").innerHTML = "<p> ₹ " + responseObj.balance + "</p>";
                } else if (this.status === 500) {
                    redirectToErrorPage();
                }
            };
        };
        </script>
        <%} else {
            response.setStatus(401);
        %>
        <h2>Unauthorized access</h2>
        <p>Click the button to login</p>
        <button class="btn2" onclick="location.href = 'index.jsp';">Login</button><br>
        <%}%>
    </center>
    
</body>
</html>
