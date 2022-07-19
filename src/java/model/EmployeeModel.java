/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vignesh-pt5186
 */
public class EmployeeModel {
    private String empLoginID;
    private String empPass;
    private int empID;
    private String empName;
    private int empRoleID;
    private String empRoleName;
    private int empBankID;    
    private String empBankName;
    private String empBankBranch;
    
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpLoginID() {
        return empLoginID;
    }

    public void setEmpLoginID(String empLoginID) {
        this.empLoginID = empLoginID;
    }

    public String getEmpPass() {
        return empPass;
    }

    public void setEmpPass(String empPass) {
        this.empPass = empPass;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getEmpRoleID() {
        return empRoleID;
    }

    public void setEmpRoleID(int empRoleID) {
        this.empRoleID = empRoleID;
    }

    public String getEmpRoleName() {
        return empRoleName;
    }

    public void setEmpRoleName(String empRoleName) {
        this.empRoleName = empRoleName;
    }

    public int getEmpBankID() {
        return empBankID;
    }

    public void setEmpBankID(int empBankID) {
        this.empBankID = empBankID;
    }
    
    public String getEmpBankName() {
        return empBankName;
    }

    public void setEmpBankName(String empBankName) {
        this.empBankName = empBankName;
    }

    public String getEmpBankBranch() {
        return empBankBranch;
    }

    public void setEmpBankBranch(String empBankBranch) {
        this.empBankBranch = empBankBranch;
    }
    
    public EmployeeModel(String empName, int empRoleID, int empBankID) {
        this.empRoleID = empRoleID;
        this.empName = empName;
        this.empBankID = empBankID;
    }
    
    public EmployeeModel(String empLoginID, String empName, String empPass,int empRoleID, int empBankID) {
        this.empLoginID = empLoginID;
        this.empPass = empPass;
        this.empName = empName;
        this.empRoleID = empRoleID;
        this.empBankID = empBankID;
    }
    
    public EmployeeModel() {
        this.empLoginID = getEmpLoginID();
        this.empPass = getEmpPass();
        this.empID = getEmpID();
        this.empName = getEmpName();
        this.empRoleID = getEmpRoleID();
        this.empRoleName = getEmpRoleName();
        this.empBankID = getEmpBankID();
        this.empBankName = getEmpBankName();
        this.empBankBranch = getEmpBankBranch();
    }
    
}
