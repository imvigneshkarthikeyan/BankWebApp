/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.EmployeeModel;

/**
 *
 * @author vignesh-pt5186
 */
public class StaffDAO {
    public static boolean validateUser(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "SELECT bank_staff_details.emp_login_id, bank_staff_details.emp_pass FROM bank_schema.bank_staff_details where emp_login_id=?";
//            String query = "select * from emp_login_pass where emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    public static int validateUser(String userId, String userPass) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "SELECT bank_staff_details.emp_pass FROM bank_schema.bank_staff_details where emp_login_id=?";
//            String query = "select emp_pass from emp_login_pass where emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            } else if (!resultSet.getString("emp_pass").equals(userPass)) {
                return -1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static int getStaffRoleID(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "SELECT bank_staff_details.role_id FROM bank_schema.bank_staff_details where emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("role_id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static int getStaffBankID(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "SELECT bank_staff_details.bank_id FROM bank_schema.bank_staff_details where emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("bank_id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return 0;
    }
    
    public static EmployeeModel getStaffNameRoleAndBankID(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "SELECT bank_staff_details.emp_name, bank_staff_details.role_id, bank_staff_details.bank_id FROM bank_schema.bank_staff_details where bank_staff_details.emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            EmployeeModel employeeModel = new EmployeeModel(resultSet.getString("emp_name"), resultSet.getInt("role_id"), resultSet.getInt("bank_id"));
            return employeeModel;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
    
    public static EmployeeModel getStaffDetails(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            EmployeeModel employeeModel = new EmployeeModel();
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "  SELECT bank_staff_details.emp_id, bank_staff_details.emp_name, bank_staff_details.emp_login_id,"
                    + " bank_staff_details.role_id, bank_staff_details.bank_id, bank_details.bank_name, bank_details.branch_name"
                    + " FROM bank_schema.bank_staff_details"
                    + " LEFT JOIN bank_schema.bank_details ON bank_staff_details.bank_id = bank_details.bank_id "
                    + " where bank_staff_details.emp_login_id=?";
//            String query = "select * from emp_bank_details where emp_login_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            employeeModel.setEmpID(resultSet.getInt("emp_id"));
            employeeModel.setEmpName(resultSet.getString("emp_name"));
            employeeModel.setEmpRoleID(resultSet.getInt("role_id"));
            if (employeeModel.getEmpRoleID()==1) {
                employeeModel.setEmpRoleName("Manager");
            } else {
                employeeModel.setEmpRoleName("Staff");
            }
            employeeModel.setEmpBankID(resultSet.getInt("bank_id"));
            employeeModel.setEmpBankName(resultSet.getString("bank_name"));
            employeeModel.setEmpBankBranch(resultSet.getString("branch_name"));
            return employeeModel;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }
    
    public static boolean createNewStaff(EmployeeModel employeeModel) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "insert into bank_schema.bank_staff_details (emp_name, emp_login_id, emp_pass, role_id, bank_id) values (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeModel.getEmpName());
            statement.setString(2, employeeModel.getEmpLoginID());
            statement.setString(3, employeeModel.getEmpPass());
            statement.setInt(4, employeeModel.getEmpRoleID());
            statement.setInt(5, employeeModel.getEmpBankID());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    public static boolean deleteStaff(String empLoginID) throws SQLException {
        Connection connection = null;
        try {
            utilities.Util.getClassForName();
            connection = utilities.Util.getConnection();
            String query = "delete from bank_schema.bank_staff_details where emp_login_id=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, empLoginID);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }
    
}
