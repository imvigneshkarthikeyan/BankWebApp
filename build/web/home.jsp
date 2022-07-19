<%-- 
    Document   : home
    Created on : 4 Jul, 2022, 9:13:14 AM
    Author     : vignesh-pt5186
--%>

<%@page import="DAO.CustomerDAO"%>
<%@page import="model.CustomerModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
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
                <button id="HomeBtn" style="width:16.6%" onclick="openCustomerHome()">Home</button>
                <button style="width:16.6%" onclick="openCustomerTransferFund()">Transfer Money</button>
                <button style="width:16.6%" onclick="openCustomerShowBalance()">Show Balance</button>
                <button style="width:16.6%" onclick="openCustomerTransactions()">Transaction History</button>
                <button style="width:16.6%" onclick="openCustomerInfoTab()">Edit Info</button>
                <button style="width:16.6%" onClick="logout()">Logout</button>
            </div>
        
        <div id="userInfo">
            <br> <div id="welcomeArea">
            </div>
            <br> <div id="mainDisplay">
                <p id="userInfo1"></p>
                <p id="userInfo2"></p>
                <p id="userInfo3"></p>
                <p id="userInfo4"></p>
                <p id="userInfo5"></p>
            </div> <br>
        </div>
            
        <div id="trasferMoney" style="display: none ">
            <h3>Transfer Money</h3>
            <div id="getAccNumDiv">
                <h4>Enter the account number to which the amount has to be transferred</h4>
                <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="accNumField" autocomplete="off" placeholder="Enter the account number to be transffered" onkeydown="limitAccNumForUser(this);" onkeyup="limitAccNumForUser(this);" onkeypress="return onlyNumberKey(event)">
                <br>
                <br>
                <button id="validateAccNumBtn" style="display: none" class="block" onclick="validateAccountNumberForTransfer()">Next >>> </button>
            </div>
            <div id="transferFundDiv" style="display: none">
                <h4 id="accNumToBeSent"></h4>
                <input style="width: 100%; padding: 14px 0px; text-align: center;"  type="text" id="moneyfield" autocomplete="off" placeholder="Enter amount to be transferred" onkeydown="limit(this);" onkeyup="limit(this);" onkeypress="return onlyNumberKey(event)">
                <br>
                <br>
                <input style="width: 75%; padding: 7px 0px; text-align: center;" type="text" id="notefield" autocomplete="off" placeholder="Enter the note for reference">
                <br>
                <br>
                <button class="block" onclick="transferFund()">Transfer Funds + </button>
            </div>
            <div id="msgForUser"></div>
        </div>
            
        <div id="showBalanceDiv" style="display: none">
            <h3>Balance Available</h3>
            <p id="balanceAvailableField" style="font-size:72px;"> </p>
        </div>
        
        <div id="transactionHistoryDiv" style="display: none">
            <h3>History of your Transactions</h3>
            <div id="custTransactionHistoryDiv">
                <!--Data from json-->
            </div>
        </div>
            
        <div id="myInfoDiv" style="display: none">
            <h3>Your Info</h3>
            <div id="custInfo">
                <br><p id="userPhoneNumField"></p> <br>
                <p id="userAddressField"></p> <br>
                <button id="makeEdit" class="btn2" onclick="makeEdit()">Click here to edit Info</button>
            </div>
            <div id="editDetails" style="display:none">
                <h4>Modify the fields to be updated</h4>
                
                <br> Password: <input style="width: 200px;" type="password" id="passField" value="" placeholder="Enter password to be updated" autocomplete="off"/>
                
                <input type="checkbox" onclick="showPassword()">
                <br>
                
                <br> Phone: <input style="width: 200px;" type="text" id="phoneField" value=""  onkeypress="return onlyNumberKey(event)" placeholder="Enter phone to be updated" autocomplete="off"/> <br>
                
                <br> Address: <input style="width: 200px;" type="text" id="addressField" value="" placeholder="Enter address to be updated" autocomplete="off"/> <br>
                
                <br>
                <button id="submitBtn" class="btn" onclick="editInfo()">Update</button>
            </div>  
            <div id="displayMsg"></div>
        </div>
        
        <%} else { 
            response.setStatus(401);
        %>
        <h2>Unauthorized access</h2>
        <p>Click the button to login</p>
        <button class="btn2" onclick="location.href = 'index.jsp';">Login</button><br>
        <%}%>
    </center>
    <script src="js/main.js"></script>
    </body>
</html>
