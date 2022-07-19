<%-- 
    Document   : signup
    Created on : 17 Jun, 2022, 3:05:46 PM
    Author     : vignesh-pt5186
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
        <link rel="stylesheet" href="style/style.css">
    </head>
    <body>
    <center>
        <h1 id="heading">Welcome to Bank Application!</h1>
        <div id="signupDivision">
            <h4>Fill your details to create a new account</h4>
            <div id="userNameID">
                <br> Name: <input type="text" id="nameField" autocomplete="off"/> <br>
                <br> Login ID: <input type="text" id="loginIdField" onkeyup="validateEmail();" autocomplete="off"/> <br>
            </div>
            <div id="errorMsg"></div>
            <div id="nextBtn" style="display: none">
                <br>
                <button class="btn" onclick="checkUserExist()">Next</button>
            </div>
            <div id="bankDetails" style="display: none">
                <br>
                Select Bank:<select id="bankSelectField"> </select>
                <br>
                <div id="errorMsgForBank"></div>
                <br>
                <button class="btn" onclick="getBranchForBank()">Next</button>
            </div>
            <div id="branchDetails" style="display: none">
                <br>
                Select Branch: <select id="branchSelectField"> </select>
                <br>
                <div id="errorMsgForBranch"></div>
                <br>
                <button class="btn" onclick="openOtherDetailsForSignUp()">Next</button>
            </div>
            <div id="otherDetails" style="display: none">
                <br> Password: <input type="password" id="passField" autocomplete="off"/> <br>
                <br> Aadhar Number: <input type="text" id="aadharField" onkeypress="return onlyNumberKey(event)" autocomplete="off"/> <br>
                <br> Pan: <input type="text" id="panField" onkeypress="return isAlphaNumeric(event,this.value)" autocomplete="off"/> <br>
                <br> Phone: <input type="text" id="phoneField" onkeypress="return onlyNumberKey(event)" autocomplete="off"/> <br>
                <br> Address: <input type="text" id="addressField" autocomplete="off"/> <br>
                <br>
                <button id="submitBtn" class="btn" onclick="signUp()">Submit</button>
            </div>
        </div>
            <div id="displayMsg"></div>
            <br>
            <button id="goBackLogin" class="btn2" onclick="location.href = 'index.jsp';">Go to Login</button>
    </center>
    <script type="text/javascript" src="js/main.js"></script>
    </body>
</html>
