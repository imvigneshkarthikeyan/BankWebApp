/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author vignesh-pt5186
 */
public class Util {
    public static String getJDBCUrl() {
        return "jdbc:postgresql://localhost:5432/bankapp";
    }
    public static String getJDBCUserName() {
        return "viki";
    }
    public static String getJDBCPassword() {
        return "123";
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getJDBCUrl(), getJDBCUserName(), getJDBCPassword());
    }
    
    public static Class<?> getClassForName() throws ClassNotFoundException {
        return Class.forName("org.postgresql.Driver");
    }
    
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    public static boolean digitValidator(String s, int num) {
        if (s.length()!=num) {
            return false;
        }
        return true;
    }
    
    public static boolean isDigit(String str) {
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static void setStatusCodeMessage(int statusCode, String typeOfMsg, String message, HttpServletResponse response) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        response.setStatus(statusCode);
        json.put(typeOfMsg, message);
//        response.getWriter().println(json);
        response.getWriter().write(json.toString());
    }

}
