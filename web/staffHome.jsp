<%-- 
    Document   : staffhome
    Created on : 4 Jul, 2022, 6:53:57 PM
    Author     : vignesh-pt5186
--%>

<%@page import="DAO.StaffDAO"%>
<%@page import="model.EmployeeModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Home Page</title>
        <link rel="stylesheet" href="style/style.css">
    </head>
    <body>
    <center>
        <%
            String userID = (String) request.getSession().getAttribute("currentUser");
                int roleID = StaffDAO.getStaffRoleID(userID);
                if (userID != null && StaffDAO.validateUser(userID)) {
        %>
        <h1>Welcome to Bank Application!</h1>
        <%if (roleID == 1) {
        %>
        <div id="userFunctions" class="btn-group" style="width:100%;">
            <button id="HomeBtn" style="width:16.6%" onClick="openManagerHome()">Home</button>
            <button style="width:16.6%" onclick="openManagerAddStaffs()">Add Staff</button>
            <button style="width:16.6%" onclick="openManagerDeleteStaffs()">Remove Staff</button>
            <button style="width:16.6%" onclick="openViewAllStaffs()">View All Staff</button>
            <button style="width:16.6%" onclick="openBankWideTransactions()">Bank Wide Transactions</button>
            <button style="width:16.6%" onClick="logout()">Logout</button>
        </div>
        
        <div id="bankManagerFunctions">
            <div id="addNewStaffDiv" style="display: none">
                <h3>Add New Staff</h3>
                <div id="getUserNameToAddDiv">
                    <h4>Enter the login ID for the staff to be added</h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="staffLoginIDfield" placeholder="Enter the login ID to be created for the staff" onkeyup="validateStaffLoginID();" autocomplete="off">
                    <br>
                    <br>
                    <button id="checkIfStaffIdExistBtn" style="display: none" class="block" onclick="validateStaffID()">Next >>> </button>
                </div>
                <div id="staffDetailsToAddDiv" style="display: none">
                    <h4 id="staffIDFromTextHeading"></h4>
                    <input style="width: 75%; padding: 7px 0px; text-align: center;" type="text" id="nameOfStaffToBeAdded" placeholder="Enter the name of the staff to be added" autocomplete="off">
                    <br>
                    <br>
                    <input style="width: 75%; padding: 7px 0px; text-align: center;" type="password" id="passwordOfStaffToBeAdded" placeholder="Enter the password for the staff's account" autocomplete="off">
                    <br>
                    <br>
                    <button class="block" onclick="addStaff()">Add Staff</button>
                </div>
                <div id="msgForUserInAddStaff"></div>
            </div>
            
            <div id="deleteStaffDiv" style="display: none">
                <h3>Delete Staff</h3>
                <div id="getUserNameToDeleteDiv">
                    <h4>Enter the login ID for the staff to be deleted</h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="staffToDeleteIDfield" placeholder="Enter the login ID of the user to be deleted" onkeyup="validateStaffLoginInDelete();" autocomplete="off">
                    <br>
                    <br>
                    <button id="checkIfStaffExistBtn" style="display: none" class="block" onclick="verifyStaff()">Verify</button>
                </div>
                <div id="empDetails" style="display: none" ></div>
                <button id="deleteStaffBtn" style="display: none" class="block" onclick="deleteStaff()">Delete</button>
                <div id="msgForUserInDeleteStaff"></div>
            </div>
            
            <div id="viewAllStaffsDiv" style="display: none">
                <h3>List of the all the employees in our Bank Branch</h3>
                <div id="listAllStaffsDiv">
                    <!--Data from json-->
                </div>
            </div>
                
            <div id="bankWideTransactionsDiv" style="display: none">
                <h3>History of the Bank wide Transactions</h3>
                <div id="listAllTransactions">
                    <!--Data from json-->
                </div>
            </div>
        </div>
        
        <%} else if (roleID == 2) {%>
        <div id="userFunctions" class="btn-group" style="width:100%;">
            <button id="HomeBtn" style="width:20%" onClick="openStaffHome()">Home</button>
            <button style="width:20%" onclick="openAddMoney()">Add Money</button>
            <button style="width:20%" onclick="openWithdrawMoney()">Withdraw Money</button>
            <button style="width:20%" onclick="openViewAllCustomers()">View All Customers</button>
            <button style="width:20%" onClick="logout()">Logout</button>
        </div>
        
        <div id="bankStaffFunctions"> 

            <div id="addMoneyDiv" style="display: none">
                <h3>Deposit Money</h3>
                <div id="getAccNumDivForAdd">
                    <h4>Enter the account number of the customer to which the amount has to be deposited</h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="accNumField" placeholder="Enter the account number to be deposited" autocomplete="off" onkeydown="limitAccNum(this);" onkeyup="limitAccNum(this);" onkeypress="return onlyNumberKey(event)">
                    <br>
                    <br>
                    <button id="validateAccNumBtn1" style="display: none" class="block" onclick="validateCustomerAccountNumber()">Next >>> </button>
                </div>
                <div id="moneyDivForAdd" style="display: none">
                    <h4 id="accNumToBeSent"></h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="moneyfield" placeholder="Enter amount to be deposited" autocomplete="off" onkeydown="limit(this);" onkeyup="limit(this);" onkeypress="return onlyNumberKey(event)">
                    <br>
                    <br>
                    <input style="width: 75%; padding: 7px 0px; text-align: center;" type="text" id="notefield" placeholder="Enter the note for reference" autocomplete="off">
                    <br>
                    <br>
                    <button class="block" onclick="addMoney()">Add Money + </button>
                </div>
                <div id="msgForUser"></div>
            </div>

            <div id="withdrawMoneyDiv" style="display: none">
                <h3>Withdraw Money</h3>
                <div id="getAccNumDivForWithdraw">
                    <h4>Enter the account number of the customer to which the amount has to be withdrawn</h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="accNumFieldForWithdraw" placeholder="Enter the account number to be withdrawn" onkeydown="limitAccNumForWithdraw(this);" onkeyup="limitAccNumForWithdraw(this);" onkeypress="return onlyNumberKey(event)" autocomplete="off">
                    <br>
                    <br>
                    <button id="validateAccNumBtn2" style="display: none" class="block" onclick="validateCustomerAccountNumberInWithdraw()">Next >>> </button>
                </div>
                <div id="moneyDivForWithdraw" style="display: none">
                    <h4>Enter the amount to be withdrawn</h4>
                    <input style="width: 100%; padding: 14px 0px; text-align: center;" type="text" id="moneyfieldForWithdraw" placeholder="Enter amount to be withdrawn" onkeydown="limitForWithdraw(this);" onkeyup="limitForWithdraw(this);" onkeypress="return onlyNumberKey(event)" autocomplete="off">
                    <br>
                    <br>
                    <input style="width: 75%; padding: 7px 0px; text-align: center;" type="text" id="notefieldForWithdraw" placeholder="Enter the note for reference" autocomplete="off">
                    <br>
                    <br>
                    <button class="block" onclick="withdrawMoney()">Withdraw Money - </button>
                </div>
                <div id="msgForUserForWithdraw"></div>
            </div>

            <div id="viewAllCustomersDiv" style="display: none">
                <h3>List of the all the customers in our Bank Branch</h3>
                <div id="allCustTable">
                    <!--Table from json-->
                </div>
            </div>
        </div>
        <% }%>
        
        <div id="userInfo">
            <br> <div id="welcomeArea"></div>
            <br> <div id="mainDisplay">
                <p id="userInfo1"> </p>
                <p id="userInfo2"> </p>
                <p id="userInfo3"> </p>
            </div> <br>
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
