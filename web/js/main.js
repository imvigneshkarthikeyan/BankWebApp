/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//Staff Login Check
function staffLogIn() {
    var userId = document.getElementById("idForStaff").value;
    var userPass = document.getElementById("passForStaff").value;
    let msgDiv = document.getElementById("msgForUserForStaff");
    if (userId === "" && userPass === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your user ID and password</p>";
    } else if (userId === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your user ID</p>";
    } else if (userPass === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your password</p>";
    } else if (userPass.length < 3) {
        msgDiv.innerHTML = "<p style=color:red>Password should be minimum 3 characters</p>";
    } else {
        msgDiv.innerHTML = "<p></p>";
        validationForStaffLogin(userId, userPass);
    }
}

function validationForStaffLogin(userId, userPass) {
    var request = new XMLHttpRequest();
    request.open("POST", "StaffLogin", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "userId": userId,
        "userPass": userPass
    };
    request.send(JSON.stringify(userInfo));
    request.onload = function () {
        if (this.status === 400 || this.status === 409 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("msgForUserForStaff").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            window.location.href = 'staffHome.jsp';
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

// LoginCheck
function logIn() {
    var userId = document.getElementById("id").value;
    var userPass = document.getElementById("pass").value;
    let msgDiv = document.getElementById("msgForUser");
    if (userId === "" && userPass === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your user ID and password</p>";
    } else if (userId === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your user ID</p>";
    } else if (userPass === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter your password</p>";
    } else if (userPass.length < 3) {
        msgDiv.innerHTML = "<p style=color:red>Password should be minimum 3 characters</p>";
    } else {
        msgDiv.innerHTML = "<p></p>";
        validationForLogin(userId, userPass);
    }
}

function validationForLogin(userId, userPass) {
    var request = new XMLHttpRequest();
    request.open("POST", "CustomerLogin", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "userId": userId,
        "userPass": userPass
    };
//    request.send("userId="+userId+"&userPass="+userPass);
    console.log("Executing...");
    request.send(JSON.stringify(userInfo));

    request.onload = function () {
        if (this.status === 400 || this.status === 409 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("msgForUser").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
//            document.getElementById("id").value = userId;
//            document.getElementById("pass").value = userPass;
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            window.location.href = 'home.jsp';
        } else if (this.status === 500) {
            console.log("Executing error...");
            redirectToErrorPage();
        }
    };
}

function logout() {
    var request = new XMLHttpRequest();
    request.open("GET", "logout", true);
    request.send();
    window.location.href = '/';
}

function openStaffLogin() {
    document.getElementById("loginDivision").style.display = "none";
    document.getElementById("loginDivisionForStaff").style.display = "";
}

function openCustomerLogin() {
    document.getElementById("loginDivision").style.display = "";
    document.getElementById("loginDivisionForStaff").style.display = "none";
}

//SignUpCheck
function checkUserExist() {
    var userName = document.getElementById("nameField").value;
    var userId = document.getElementById("loginIdField").value;
    let errorDiv = document.getElementById("errorMsg");
    if (userName === "" && userId === "") {
        errorDiv.innerHTML = "<p style=color:red>Please enter your user name and user ID to signup</p>";
    } else if (userName === "") {
        errorDiv.innerHTML = "<p style=color:red>Please enter your user name to signup</p>";
    } else if (userId === "") {
        errorDiv.innerHTML = "<p style=color:red>Please enter your user ID to signup</p>";
    } else {
        errorDiv.innerHTML = "<p></p>";
        validationForUserExist(userName, userId);
    }
}

function validationForUserExist(userName, userId) {
    var request = new XMLHttpRequest();
    request.open("POST", "validateUserId", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "userName": userName,
        "userId": userId
    };
//    request.send("userId=" + userId);
    request.send(JSON.stringify(userInfo));
    request.onload = function () {
        if (this.status === 409 || this.status === 400 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("errorMsg").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            document.getElementById("loginIdField").value = userId;
        } else if (this.status === 200) {
            var req = new XMLHttpRequest();
            req.open("GET", "fetchbank", true);
            req.setRequestHeader("Content-type", "application/json");
            req.send();
            req.onload = function () {
                let obj = JSON.parse(this.responseText);
                let out;
                for (let i = 0; i < obj.length; i++) {
                    out += "<option value=" + obj[i] + ">" + obj[i] + "</option>";
                }
                document.getElementById("bankSelectField").innerHTML = out;
            };
            document.getElementById("userNameID").style.display = "none";
            document.getElementById("bankDetails").style.display = "";
            document.getElementById("branchDetails").style.display = "none";
            document.getElementById("otherDetails").style.display = "none";
            document.getElementById("errorMsg").style.display = "none";
            document.getElementById("nextBtn").style.display = "none";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

function getBranchForBank() {
    var bankField = document.getElementById("bankSelectField");
    var selectedBank = bankField.options[bankField.selectedIndex].value;
    var request = new XMLHttpRequest();
    request.open("POST", "fetchbranch", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "bankName": selectedBank
    };
    request.send(JSON.stringify(userInfo));
    request.onload = function () {
        if (this.status === 409 || this.status === 400 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("errorMsgForBank").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
        } else if (this.status === 200) {
            let obj = JSON.parse(this.responseText);
            let out;
            for (let i = 0; i < obj.length; i++) {
                out += "<option value=" + obj[i] + ">" + obj[i] + "</option>";
            }
            document.getElementById("branchSelectField").innerHTML = out;
            document.getElementById("userNameID").style.display = "none";
            document.getElementById("bankDetails").style.display = "none";
            document.getElementById("branchDetails").style.display = "";
            document.getElementById("otherDetails").style.display = "none";
            document.getElementById("errorMsg").style.display = "none";
            document.getElementById("nextBtn").style.display = "none";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

function openOtherDetailsForSignUp() {
    var bankField = document.getElementById("bankSelectField");
    var selectedBank = bankField.options[bankField.selectedIndex].value;
    var branchField = document.getElementById("branchSelectField");
    var selectedBranch = branchField.options[branchField.selectedIndex].value;
    var request = new XMLHttpRequest();
    request.open("POST", "validateBranch", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "bankName": selectedBank,
        "branchName": selectedBranch
    };
    request.send(JSON.stringify(userInfo));
    request.onload = function () {
        if (this.status === 409 || this.status === 400 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("errorMsgForBranch").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
        } else if (this.status === 200) {
            document.getElementById("userNameID").style.display = "none";
            document.getElementById("bankDetails").style.display = "none";
            document.getElementById("branchDetails").style.display = "none";
            document.getElementById("otherDetails").style.display = "";
            document.getElementById("errorMsg").style.display = "none";
            document.getElementById("nextBtn").style.display = "none";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

function validateCustomerAccountNumberInWithdraw() {
    var accNum = document.getElementById("accNumFieldForWithdraw").value;
    if (accNum === "") {
        document.getElementById("msgForUserForWithdraw").innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else {
        document.getElementById("msgForUserForWithdraw").innerHTML = "";
        var request = new XMLHttpRequest();
        request.open("POST", "ValidateAccount", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "accNum": accNum
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUserForWithdraw").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                document.getElementById("getAccNumDivForAdd").style.display = "none";
                document.getElementById("moneyDivForAdd").style.display = "";
                document.getElementById("getAccNumDivForWithdraw").style.display = "none";
                document.getElementById("moneyDivForWithdraw").style.display = "";
                document.getElementById("accNumToBeSent").innerHTML = "Enter the amount to be added to: " + accNum;
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };

    }
}

function validateCustomerAccountNumber() {
    var accNum = document.getElementById("accNumField").value;
    if (accNum === "") {
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else {
        document.getElementById("msgForUser").innerHTML = "";
        var request = new XMLHttpRequest();
        request.open("POST", "ValidateAccount", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "accNum": accNum
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUser").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                document.getElementById("getAccNumDivForAdd").style.display = "none";
                document.getElementById("moneyDivForAdd").style.display = "";
                document.getElementById("accNumToBeSent").innerHTML = "Enter the amount to be added to: " + accNum;
            }
        };
    }
}


function addMoney() {
    var accNum = document.getElementById("accNumField").value;
    var amount = document.getElementById("moneyfield").value;
    var note = document.getElementById("notefield").value;
    let msgDiv = document.getElementById("msgForUser");
    if (accNum === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else if (amount === "" || amount === "0") {
        msgDiv.innerHTML = "<p style=color:red>Min enter 1 Rupee</p>";
    } else if (isNaN(amount)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else if (note.length > 20) {
        msgDiv.innerHTML = "<p style=color:red>Note can be maximum 20 Characters</p>";
    } else {
        msgDiv.innerHTML = "";
        var amount = {
            "accNum": accNum,
            "amount": amount,
            "note": note
        };
        var request = new XMLHttpRequest();
        request.open("PUT", "AddAmount", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(amount));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 409 || this.status === 400 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUser").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 201) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("getAccNumDivForAdd").style.display = "none";
                document.getElementById("moneyDivForAdd").style.display = "none";
                document.getElementById("msgForUser").innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }
}

function withdrawMoney() {
    var accNum = document.getElementById("accNumFieldForWithdraw").value;
    var amount = document.getElementById("moneyfieldForWithdraw").value;
    var note = document.getElementById("notefieldForWithdraw").value;
    let msgDiv = document.getElementById("msgForUserForWithdraw");
    if (accNum === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else if (amount === "" || amount === "0") {
        msgDiv.innerHTML = "<p style=color:red>Min enter 1 Rupee</p>";
    } else if (isNaN(amount)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else if (note.length > 20) {
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>Note can be maximum 20 Characters</p>";
    } else {
        msgDiv.innerHTML = "";
        var amount = {
            "accNum": accNum,
            "amount": amount,
            "note": note
        };
        var request = new XMLHttpRequest();
        request.open("PUT", "withdraw", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(amount));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 409 || this.status === 400 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                msgDiv.innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 201) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("getAccNumDivForWithdraw").style.display = "none";
                document.getElementById("moneyDivForWithdraw").style.display = "none";
                msgDiv.innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }
}

function validateAccountNumber() {
    var accNum = document.getElementById("accNumField").value;
    let msgDiv = document.getElementById("msgForUser");
    if (accNum === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid account number</p>";
    } else {
        msgDiv.innerHTML = "";
        var request = new XMLHttpRequest();
        request.open("POST", "transfervalidate", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "accNum": accNum
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 400 || this.status === 409 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                msgDiv.innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                document.getElementById("getAccNumDivForAdd").style.display = "none";
                document.getElementById("getAccNumDivForWithdraw").style.display = "none";
                document.getElementById("transferFundDiv").style.display = "";
                document.getElementById("accNumToBeSent").innerHTML = "Enter the amount to be transferred to: " + accNum;
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };

    }
}

function validateAccountNumberForTransfer() {
    var accNum = document.getElementById("accNumField").value;
    let msgDiv = document.getElementById("msgForUser");
    if (accNum === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid account number</p>";
    } else {
        msgDiv.innerHTML = "";
        var request = new XMLHttpRequest();
        request.open("POST", "TransferValidate", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "accNum": accNum
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                msgDiv.innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                document.getElementById("getAccNumDiv").style.display = "none";
                document.getElementById("transferFundDiv").style.display = "";
                document.getElementById("accNumToBeSent").innerHTML = "Enter the amount to be transferred to: " + accNum;
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };

    }
}

function validateStaffID() {
    var userID = document.getElementById("staffLoginIDfield").value;
    if (userID === "") {
        document.getElementById("msgForUserInAddStaff").innerHTML = "<p style=color:red>Please enter the login ID for staff</p>";
    } else {
        document.getElementById("msgForUserInAddStaff").innerHTML = "";
        var request = new XMLHttpRequest();
        request.open("POST", "ValidateStaff", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "userID": userID
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUserInAddStaff").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                document.getElementById("getUserNameToAddDiv").style.display = "none";
                document.getElementById("staffDetailsToAddDiv").style.display = "";
                document.getElementById("staffIDFromTextHeading").innerHTML = "Enter the other details to create the staff with login id: " + userID;
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };

    }
}

function addStaff() {
    var userID = document.getElementById("staffLoginIDfield").value;
    var userName = document.getElementById("nameOfStaffToBeAdded").value;
    var pass = document.getElementById("passwordOfStaffToBeAdded").value;
    if (userID === "" || userName === "" || pass === "") {
        document.getElementById("msgForUserInAddStaff").innerHTML = "<p style=color:red>Enter all the details</p>";
    } else {
        document.getElementById("msgForUserInAddStaff").innerHTML = "";
        var staffData = {
            "userID": userID,
            "userName": userName,
            "pass": pass
        };
        var request = new XMLHttpRequest();
        request.open("POST", "AddStaff", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(staffData));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 409 || this.status === 400 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUserInAddStaff").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 201) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("staffDetailsToAddDiv").style.display = "none";
                document.getElementById("msgForUserInAddStaff").innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }
}

function deleteStaff() {
    var userID = document.getElementById("staffToDeleteIDfield").value;
    if (userID === "") {
        document.getElementById("msgForUserInDeleteStaff").innerHTML = "<p style=color:red>Enter the user ID to be removed</p>";
    } else {
        document.getElementById("msgForUserInDeleteStaff").innerHTML = "";
        var staffData = {
            "userID": userID
        };
        var request = new XMLHttpRequest();
        request.open("DELETE", "DeleteStaff", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(staffData));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 403 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUserInDeleteStaff").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("getUserNameToDeleteDiv").style.display = "none";
                document.getElementById("empDetails").style.display = "none";
                document.getElementById("deleteStaffBtn").style.display = "none";
                document.getElementById("msgForUserInDeleteStaff").innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";

            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }

}

function verifyStaff() {
    var userID = document.getElementById("staffToDeleteIDfield").value;
    if (userID === "") {
        document.getElementById("msgForUserInDeleteStaff").innerHTML = "<p style=color:red>Enter the user ID to be removed</p>";
    } else {
        document.getElementById("msgForUserInDeleteStaff").innerHTML = "";
        var staffData = {
            "userID": userID
        };
        var request = new XMLHttpRequest();
        request.open("POST", "StaffValidate", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(staffData));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 400 || this.status === 409 || this.status === 403 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("msgForUserInDeleteStaff").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 200) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("getUserNameToDeleteDiv").style.display = "none";
                document.getElementById("empDetails").style.display = "";
                document.getElementById("deleteStaffBtn").style.display = "";
                document.getElementById("empDetails").innerHTML = "<br><p> Login ID: " + userID + "</p><br><p> Name: " + responseObj.userName + "</p><br>";

            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }

}

function transferFund() {
    var accNum = document.getElementById("accNumField").value;
    var amount = document.getElementById("moneyfield").value;
    var note = document.getElementById("notefield").value;
    let msgDiv = document.getElementById("msgForUser");
    if (accNum === "") {
        msgDiv.innerHTML = "<p style=color:red>Please enter the account number</p>";
    } else if (isNaN(accNum)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid account number</p>";
    } else if (amount === "" || amount === "0") {
        msgDiv.innerHTML = "<p style=color:red>Min enter 1 Rupee</p>";
    } else if (isNaN(amount)) {
        msgDiv.innerHTML = "<p style=color:red>Please enter valid amount</p>";
    } else if (note.length > 20) {
        msgDiv.innerHTML = "<p style=color:red>Note can be maximum 20 Characters</p>";
    } else {
        msgDiv.innerHTML = "";
        var transfer = {
            "accNum": accNum,
            "amount": amount,
            "note": note
        };
        var request = new XMLHttpRequest();
        request.open("PUT", "TransferFund", true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(JSON.stringify(transfer));
        request.onload = function () {
            if (this.status === 401) {
                location.href = "index.jsp";
            } else if (this.status === 409 || this.status === 400 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                msgDiv.innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 201) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("getAccNumDiv").style.display = "none";
                document.getElementById("transferFundDiv").style.display = "none";
                msgDiv.innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }
}

function makeEdit() {
    //geteditinfodata
    var request = new XMLHttpRequest();
    request.open("GET", "GetEditData", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("passField").value = responseObj.cust_pass;
            document.getElementById("phoneField").value = responseObj.phone_num;
            document.getElementById("addressField").value = responseObj.address;
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
    document.getElementById("custInfo").style.display = "none";
    document.getElementById("editDetails").style.display = "";
}

function editInfo() {
    var userPass = document.getElementById("passField").value;
    var userPhone = document.getElementById("phoneField").value;
    var userAddress = document.getElementById("addressField").value;
    if (userPass === "" || userPhone === "" || userAddress === "") {
        document.getElementById("displayMsg").innerHTML = "<p style=color:red>Please fill all the details to be updated</p>";
    } else {
        var request = new XMLHttpRequest();
        request.open("PUT", "EditInfo", true);
        request.setRequestHeader("Content-type", "application/json");
        var userInfo = {
            "userPass": userPass,
            "userPhone": userPhone,
            "userAddress": userAddress
        };
        request.send(JSON.stringify(userInfo));
        request.onload = function () {
            if (this.status === 401) {
                location.href = 'index.jsp';
            } else if (this.status === 409 || this.status === 400 || this.status === 503) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("displayMsg").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            } else if (this.status === 201) {
                responseObj = JSON.parse(this.responseText);
                document.getElementById("editDetails").style.display = "none";
                document.getElementById("displayMsg").innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
            } else if (this.status === 500) {
                redirectToErrorPage();
            }
        };
    }

}

function signUp() {
    var userName = document.getElementById("nameField").value;
    var userId = document.getElementById("loginIdField").value;
    var userBankName = document.getElementById("bankSelectField").value;
    var userBranchName = document.getElementById("branchSelectField").value;
    var userPass = document.getElementById("passField").value;
    var userAadhar = document.getElementById("aadharField").value;
    var userPan = document.getElementById("panField").value;
    var userPhone = document.getElementById("phoneField").value;
    var userAddress = document.getElementById("addressField").value;
    if (userName === "" || userId === "" || userBankName === "" || userBranchName === "" || userPass === "" || userAadhar === "" || userPan === "" || userPhone === "" || userAddress === "") {
        document.getElementById("displayMsg").innerHTML = "<p style=color:red>Please enter all the details to signup</p>";
    } else {
        createNewUser(userName, userId, userBankName, userBranchName, userPass, userAadhar, userPan, userPhone, userAddress);
    }
}

function createNewUser(userName, userId, userBankName, userBranchName, userPass, userAadhar, userPan, userPhone, userAddress) {
    var request = new XMLHttpRequest();
    request.open("POST", "createnew", true);
    request.setRequestHeader("Content-type", "application/json");
    var userInfo = {
        "userName": userName,
        "userId": userId,
        "userBankName": userBankName,
        "userBranchName": userBranchName,
        "userPass": userPass,
        "userAadhar": userAadhar,
        "userPan": userPan,
        "userPhone": userPhone,
        "userAddress": userAddress
    };
//    request.send("userName="+userName+"&userId="+userId+"&userBankName="+userBankName+"&userBranchName="+userBranchName+"&userPass="+userPass+"&userAadhar="+userAadhar+"&userPan="+userPan+"&userPhone="+userPhone+"&userAddress="+userAddress);
    request.send(JSON.stringify(userInfo));
    request.onload = function () {
        if (this.status === 409 || this.status === 400 || this.status === 503) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("displayMsg").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
            document.getElementById("bankSelectField").value = userBankName;
            document.getElementById("branchSelectField").value = userBranchName;
            document.getElementById("passField").value = userPass;
            document.getElementById("aadharField").value = userAadhar;
            document.getElementById("panField").value = userPan;
            document.getElementById("phoneField").value = userPhone;
            document.getElementById("addressField").value = userAddress;
        } else if (this.status === 201) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("signupDivision").style.display = "none";
            document.getElementById("displayMsg").innerHTML = "<p style=color:#358856>" + responseObj.successMessage + "</p>";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

function onlyNumberKey(evt) {
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode;
    if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}

function isAlphaNumeric(e) { // Alphanumeric only
    var k;
    document.all ? k = e.keycode : k = e.which;
    return((k > 47 && k < 58) || (k > 64 && k < 91) || (k > 96 && k < 123) || k === 0);
}

function validateEmail() {
    var email = document.getElementById("loginIdField").value;
    var errorMsg = document.getElementById("errorMsg");
    errorMsg.innerHTML = "";
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!expr.test(email)) {
        errorMsg.innerHTML = "Invalid email address.";
        document.getElementById("nextBtn").style.display = "none";
    } else {
        document.getElementById("nextBtn").style.display = "";
    }
}

function validateStaffLoginID() {
    var email = document.getElementById("staffLoginIDfield").value;
    var errorMsg = document.getElementById("msgForUserInAddStaff");
    errorMsg.innerHTML = "";
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!expr.test(email)) {
        errorMsg.innerHTML = "Invalid email address.";
        document.getElementById("checkIfStaffIdExistBtn").style.display = "none";
    } else {
        document.getElementById("checkIfStaffIdExistBtn").style.display = "";
    }
}

function validateStaffLoginInDelete() {
    var email = document.getElementById("staffToDeleteIDfield").value;
    var errorMsg = document.getElementById("msgForUserInDeleteStaff");
    errorMsg.innerHTML = "";
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!expr.test(email)) {
        errorMsg.innerHTML = "Invalid email address.";
        document.getElementById("checkIfStaffExistBtn").style.display = "none";
    } else {
        document.getElementById("checkIfStaffExistBtn").style.display = "";
    }
}

function showPassword() {
    var x = document.getElementById("passField");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function validateLogin() {
    var loginID = document.getElementById("id").value;
    var msgForUser = document.getElementById("msgForUser");
    msgForUser.innerHTML = "";
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!expr.test(loginID)) {
        msgForUser.innerHTML = "Invalid Login ID.";
        document.getElementById("loginBtn").style.display = "none";
    } else {
        document.getElementById("loginBtn").style.display = "";
    }
}

function validateLoginForStaff() {
    var loginID = document.getElementById("idForStaff").value;
    var msgForUser = document.getElementById("msgForUserForStaff");
    msgForUser.innerHTML = "";
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!expr.test(loginID)) {
        msgForUser.innerHTML = "Invalid Login ID.";
        document.getElementById("loginBtnForStaff").style.display = "none";
    } else {
        document.getElementById("loginBtnForStaff").style.display = "";
    }
}

function limit(element)
{
    var max_chars = 7;

    if (element.value.length > max_chars) {
        element.value = element.value.substr(0, max_chars);
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>Maximum only 10,00,000 can be transfered</p>";
    } else {
        document.getElementById("msgForUser").innerHTML = "";
    }
}

function limitForWithdraw(element) {
    var max_chars = 7;

    if (element.value.length > max_chars) {
        element.value = element.value.substr(0, max_chars);
        document.getElementById("msgForUserForWithdraw").innerHTML = "<p style=color:red>Maximum only 10,00,000 can be transfered</p>";
    } else {
        document.getElementById("msgForUserForWithdraw").innerHTML = "";
    }
}

function limitAccNum(element)
{
    if (element.value.length > 11 || element.value.length < 11) {
        element.value = element.value.substr(0, 11);
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>The account number is 11 digits</p>";
    } else {
        document.getElementById("msgForUser").innerHTML = "";
        document.getElementById("validateAccNumBtn1").style.display = "";
    }
}

function limitAccNumForWithdraw(element)
{
    if (element.value.length > 11 || element.value.length < 11) {
        element.value = element.value.substr(0, 11);
        document.getElementById("msgForUserForWithdraw").innerHTML = "<p style=color:red>The account number is 11 digits</p>";
    } else {
        document.getElementById("msgForUserForWithdraw").innerHTML = "";
        document.getElementById("validateAccNumBtn2").style.display = "";
    }
}

function limitAccNumForUser(element)
{
    if (element.value.length > 11 || element.value.length < 11) {
        element.value = element.value.substr(0, 11);
        document.getElementById("msgForUser").innerHTML = "<p style=color:red>The account number is 11 digits</p>";
    } else {
        document.getElementById("msgForUser").innerHTML = "";
        document.getElementById("validateAccNumBtn").style.display = "";
    }
}



function openCustomerHome() {
//    customerhome
    var request = new XMLHttpRequest();
    request.open("GET", "CustomerDetails", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("welcomeArea").innerHTML = "<h3> Welcome " + responseObj.userName + "!</h3>";
            document.getElementById("userInfo1").innerHTML = "<p> Aadhar Number: " + responseObj.aadhar + "</p>";
            document.getElementById("userInfo2").innerHTML = "<p> Pan Number: " + responseObj.pan + "</p>";
            document.getElementById("userInfo3").innerHTML = "<p> Bank Name: " + responseObj.bankName + "</p>";
            document.getElementById("userInfo4").innerHTML = "<p> Bank Branch: " + responseObj.bankBranch + "</p>";
            document.getElementById("userInfo5").innerHTML = "<p> Account Number: " + responseObj.accountNum + "</p>";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
    document.getElementById("userInfo").style.display = "";
    document.getElementById("trasferMoney").style.display = "none";
    document.getElementById("showBalanceDiv").style.display = "none";
    document.getElementById("transactionHistoryDiv").style.display = "none";
    document.getElementById("myInfoDiv").style.display = "none";
}

function openCustomerTransferFund() {
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("trasferMoney").style.display = "";
    document.getElementById("getAccNumDiv").style.display = "";
    document.getElementById("validateAccNumBtn").style.display = "none";

    document.getElementById("msgForUser").innerHTML = "";
    document.getElementById("accNumField").value = "";
    document.getElementById("moneyfield").value = "";
    document.getElementById("notefield").value = "";

    document.getElementById("transferFundDiv").style.display = "none";
    document.getElementById("showBalanceDiv").style.display = "none";
    document.getElementById("transactionHistoryDiv").style.display = "none";
    document.getElementById("myInfoDiv").style.display = "none";
}

function openCustomerShowBalance() {
    //showbalance
    location.href = 'showbalance.jsp';
//    var request = new XMLHttpRequest();
//    request.open("GET", "showbalance", true);
//    request.setRequestHeader("Content-type", "application/json");
//    request.send();
//    request.onload = function () {
//        if (this.status === 401) {
//            location.href = 'index';
//        } else if (this.status === 200) {
//            responseObj = JSON.parse(this.responseText);
//            document.getElementById("balanceAvailableField").innerHTML = "<p> ₹ " + responseObj.balance + "</p>";
//        } else if (this.status === 500) {
//            redirectToErrorPage();
//        }
//    };
//    document.getElementById("userInfo").style.display = "none";
//    document.getElementById("trasferMoney").style.display = "none";
//    document.getElementById("showBalanceDiv").style.display = "";
//    document.getElementById("transactionHistoryDiv").style.display = "none";
//    document.getElementById("myInfoDiv").style.display = "none";
}

function openCustomerTransactions() {

    document.getElementById("userInfo").style.display = "none";
    document.getElementById("trasferMoney").style.display = "none";
    document.getElementById("showBalanceDiv").style.display = "none";
    document.getElementById("transactionHistoryDiv").style.display = "";
    document.getElementById("myInfoDiv").style.display = "none";
    //customerhistory
    var request = new XMLHttpRequest();
    request.open("GET", "CustomerHistory", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 400) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("custTransactionHistoryDiv").innerHTML = "<p style=color:#FFA400>" + responseObj.errorMessage + "</p>";
        } else if (this.status === 200) {
            let obj = JSON.parse(this.responseText);
            if (obj.length === 0) {
                document.getElementById("custTransactionHistoryDiv").innerHTML = "<p> No Transactions till now</p>";
            } else {
                out = "<table> \
                <tr> \
                        <th>Date | Time</th> \
                        <th>From Account Number	</th> \
                        <th>To Account Number</th> \
                        <th>Amount Transferred</th> \
                        <th>Balance</th> \
                        <th>Transaction Note</th> \
                    </tr>";
                for (let i = 0; i < obj.length; i++) {
                    let amount = obj[i]["amount_transfered"];
                    if (Math.sign(amount) === -1) {
                        out += "<tr><td>" + obj[i]["date_time"] + "</td> \
                                <td>" + obj[i]["from_acc_no"] + "</td> \
                                <td>" + obj[i]["to_acc_no"] + "</td> \
                                <td style='color: red'>" + obj[i]["amount_transfered"] + "</td> \
                                <td>" + obj[i]["balance"] + "</td> \
                                <td>" + obj[i]["trans_note"] + "</td> \
                            </tr>";
                    } else {
                        out += "<tr><td>" + obj[i]["date_time"] + "</td> \
                                <td>" + obj[i]["from_acc_no"] + "</td> \
                                <td>" + obj[i]["to_acc_no"] + "</td> \
                                <td style='color: green'>" + obj[i]["amount_transfered"] + "</td> \
                                <td>" + obj[i]["balance"] + "</td> \
                                <td>" + obj[i]["trans_note"] + "</td> \
                            </tr>";
                    }
                }
                out += "</table>";
                document.getElementById("custTransactionHistoryDiv").innerHTML = out;
            }
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
}

function openCustomerInfoTab() {
    //getcustomerinfo
    var request = new XMLHttpRequest();
    request.open("GET", "GetCustomerInfo", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("userPhoneNumField").innerHTML = "<p> Phone: " + responseObj.phone + "</p>";
            document.getElementById("userAddressField").innerHTML = "<p> Address: " + responseObj.address + "</p>";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("trasferMoney").style.display = "none";
    document.getElementById("showBalanceDiv").style.display = "none";
    document.getElementById("transactionHistoryDiv").style.display = "none";
    document.getElementById("myInfoDiv").style.display = "";
    document.getElementById("custInfo").style.display = "";
    document.getElementById("displayMsg").innerHTML = "";
    document.getElementById("editDetails").style.display = "none";
}

function openStaffHome() {
    //getstaffdata
    var request = new XMLHttpRequest();
    request.open("GET", "StaffDetails", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("welcomeArea").innerHTML = "<h3> Welcome " + responseObj.name + "!</h3>";
            document.getElementById("userInfo1").innerHTML = "<p> Role Name: " + responseObj.roleName + "</p>";
            document.getElementById("userInfo2").innerHTML = "<p> Bank Name: " + responseObj.bankName + "</p>";
            document.getElementById("userInfo3").innerHTML = "<p> Branch Name: " + responseObj.branchName + "</p>";
        } else if (this.status === 500) {
            redirectToErrorPage();
        }

    };

    document.getElementById("userInfo").style.display = "";
    document.getElementById("addMoneyDiv").style.display = "none";
    document.getElementById("withdrawMoneyDiv").style.display = "none";
    document.getElementById("viewAllCustomersDiv").style.display = "none";
}

function openAddMoney() {
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("addMoneyDiv").style.display = "";
    document.getElementById("getAccNumDivForAdd").style.display = "";
    document.getElementById("validateAccNumBtn1").style.display = "none";
    document.getElementById("msgForUser").innerHTML = "";
    document.getElementById("accNumField").value = "";
    document.getElementById("moneyfield").value = "";
    document.getElementById("notefield").value = "";
    document.getElementById("moneyDivForAdd").style.display = "none";
    document.getElementById("withdrawMoneyDiv").style.display = "none";
    document.getElementById("viewAllCustomersDiv").style.display = "none";
}

function openWithdrawMoney() {
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("addMoneyDiv").style.display = "none";
    document.getElementById("withdrawMoneyDiv").style.display = "";
    document.getElementById("getAccNumDivForWithdraw").style.display = "";
    document.getElementById("validateAccNumBtn2").style.display = "none";
    document.getElementById("msgForUserForWithdraw").innerHTML = "";
    document.getElementById("accNumFieldForWithdraw").value = "";
    document.getElementById("moneyfieldForWithdraw").value = "";
    document.getElementById("notefieldForWithdraw").value = "";
    document.getElementById("moneyDivForWithdraw").style.display = "none";
    document.getElementById("viewAllCustomersDiv").style.display = "none";
}

function openViewAllCustomers() {
    //viewallcustomers
    var request = new XMLHttpRequest();
    request.open("GET", "viewallcustomers", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            let obj = JSON.parse(this.responseText);
            if (obj.length === 0) {
                document.getElementById("allCustTable").innerHTML = "<p> No customers till now</p>";
            } else {
                out = "<table> \
                <tr> \
                        <th>Account Number</th> \
                        <th>Customer Name</th> \
                        <th>Customer Login ID</th> \
                        <th>PAN Number</th> \
                        <th>Aadhar Number</th> \
                        <th>Phone Number</th> \
                        <th>Address</th> \
                        <th>Balance</th> \
                    </tr>";
                for (let i = 0; i < obj.length; i++) {
                    out += "<tr><td>" + obj[i]["account_num"] + "</td> \
                            <td>" + obj[i]["cust_name"] + "</td> \
                            <td>" + obj[i]["cust_login_id"] + "</td> \
                            <td>" + obj[i]["pan_num"] + "</td> \
                            <td>" + obj[i]["aadhar"] + "</td> \
                            <td>" + obj[i]["phone_num"] + "</td> \
                            <td>" + obj[i]["address"] + "</td> \
                            <td>" + obj[i]["amount"] + "</td> \
                        </tr>";
                }
                out += "</table>";
                document.getElementById("allCustTable").innerHTML = out;
            }
        } else if (this.status === 500) {
            redirectToErrorPage();
        }
    };
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("addMoneyDiv").style.display = "none";
    document.getElementById("withdrawMoneyDiv").style.display = "none";
    document.getElementById("viewAllCustomersDiv").style.display = "";
}

function openManagerHome() {
    var request = new XMLHttpRequest();
    request.open("GET", "StaffDetails", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            responseObj = JSON.parse(this.responseText);
            document.getElementById("welcomeArea").innerHTML = "<h3> Welcome " + responseObj.name + "!</h3>";
            document.getElementById("userInfo1").innerHTML = "<p> Role Name: " + responseObj.roleName + "</p>";
            document.getElementById("userInfo2").innerHTML = "<p> Bank Name: " + responseObj.bankName + "</p>";
            document.getElementById("userInfo3").innerHTML = "<p> Branch Name: " + responseObj.branchName + "</p>";
        }
    };
    document.getElementById("userInfo").style.display = "";
    document.getElementById("viewAllStaffsDiv").style.display = "none";
    document.getElementById("bankWideTransactionsDiv").style.display = "none";
    document.getElementById("addNewStaffDiv").style.display = "none";
    document.getElementById("deleteStaffDiv").style.display = "none";

}

function openManagerAddStaffs() {
    //addNewStaffDiv
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("viewAllStaffsDiv").style.display = "none";
    document.getElementById("bankWideTransactionsDiv").style.display = "none";
    document.getElementById("addNewStaffDiv").style.display = "";
    document.getElementById("getUserNameToAddDiv").style.display = "";
    document.getElementById("staffLoginIDfield").value = "";
    document.getElementById("staffDetailsToAddDiv").style.display = "none";
    document.getElementById("deleteStaffDiv").style.display = "none";
    document.getElementById("msgForUserInAddStaff").innerHTML = "";
    document.getElementById("nameOfStaffToBeAdded").value = "";
    document.getElementById("passwordOfStaffToBeAdded").value = "";

}

function openManagerDeleteStaffs() {
    //deleteStaffDiv
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("viewAllStaffsDiv").style.display = "none";
    document.getElementById("bankWideTransactionsDiv").style.display = "none";
    document.getElementById("addNewStaffDiv").style.display = "none";
    document.getElementById("deleteStaffDiv").style.display = "";
    document.getElementById("getUserNameToDeleteDiv").style.display = "";
    document.getElementById("empDetails").style.display = "none";
    document.getElementById("deleteStaffBtn").style.display = "none";
    document.getElementById("addNewStaffDiv").style.display = "none";
    document.getElementById("msgForUserInDeleteStaff").innerHTML = "";
    document.getElementById("staffToDeleteIDfield").value = "";


}

function openViewAllStaffs() {
    //listallstaff
    var request = new XMLHttpRequest();
    request.open("GET", "ListAllStaff", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            let obj = JSON.parse(this.responseText);
            if (obj.length === 0) {
                document.getElementById("listAllStaffsDiv").innerHTML = "<p> No staffs till now</p>";
            } else {
                out = "<table> \
                <tr> \
                        <th>Employee ID</th> \
                        <th>Employee Name</th> \
                        <th>Employee Login ID</th> \
                        <th>Employee Role</th> \
                    </tr>";
                for (let i = 0; i < obj.length; i++) {
                    out += "<tr><td>" + obj[i]["emp_id"] + "</td> \
                            <td>" + obj[i]["emp_name"] + "</td> \
                            <td>" + obj[i]["emp_login_id"] + "</td> \
                            <td>" + obj[i]["role_name"] + "</td> \
                        </tr>";
                }
                out += "</table>";
                document.getElementById("listAllStaffsDiv").innerHTML = out;
            }
        } else if (this.status === 500) {
            redirectToErrorPage();
        }

    };
    document.getElementById("userInfo").style.display = "none";
    document.getElementById("viewAllStaffsDiv").style.display = "";
    document.getElementById("bankWideTransactionsDiv").style.display = "none";
    document.getElementById("addNewStaffDiv").style.display = "none";
    document.getElementById("deleteStaffDiv").style.display = "none";
}

function openBankWideTransactions() {
    //listallbanktransactions
    var request = new XMLHttpRequest();
    request.open("GET", "ListAllBankTransactions", true);
    request.setRequestHeader("Content-type", "application/json");
    request.send();
    request.onload = function () {
        if (this.status === 401) {
            location.href = 'index.jsp';
        } else if (this.status === 200) {
            let obj = JSON.parse(this.responseText);
            if (obj.length === 0) {
                document.getElementById("listAllTransactions").innerHTML = "<p> No Transactions till now</p>";
            } else {
                out = "<table> \
                <tr> \
                        <th>Date | Time</th> \
                        <th>From Account Number	</th> \
                        <th>To Account Number</th> \
                        <th>Amount Transferred</th> \
                        <th>Transaction Note</th> \
                    </tr>";
                for (let i = 0; i < obj.length; i++) {
                    out += "<tr><td>" + obj[i]["date_time"] + "</td> \
                            <td>" + obj[i]["from_acc_no"] + "</td> \
                            <td>" + obj[i]["to_acc_no"] + "</td> \
                            <td>" + obj[i]["amount_transfered"] + "</td> \
                            <td>" + obj[i]["trans_note"] + "</td> \
                        </tr>";
                }
                out += "</table>";
                document.getElementById("listAllTransactions").innerHTML = out;
            }
        } else if (this.status === 500) {
            redirectToErrorPage();
        }

    };

    document.getElementById("userInfo").style.display = "none";
    document.getElementById("viewAllStaffsDiv").style.display = "none";
    document.getElementById("bankWideTransactionsDiv").style.display = "";
    document.getElementById("addNewStaffDiv").style.display = "none";
    document.getElementById("deleteStaffDiv").style.display = "none";


}

function redirectToErrorPage() {
    window.location.href = 'error.jsp';
}

window.onload = function () {
    document.getElementById("HomeBtn").click();
};


