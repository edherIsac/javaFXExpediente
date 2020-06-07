/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expedient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author isac
 */
public class conexionMySQL {

    private Connection con;
    private String bdd = "";
    private String user = "";
    private String pass = "";

    public conexionMySQL(String _user, String _bdd, String _pass) {
        user = _user;
        pass = _pass;
        bdd = _bdd;

        iniConexion();
    }

    private void iniConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdd, user, pass);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public ResultSet query(String _sql) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(_sql);
            
            return st.executeQuery(_sql);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return null;
    }
    
    public String exeScriptInsert(String _sql) {
        try {
            int idNew = con.prepareStatement(_sql, Statement.RETURN_GENERATED_KEYS).executeUpdate();
            
            return String.valueOf(idNew);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        
        return "0";
    }
    
    public void exeScript(String _sql) {
        try {
            con.prepareStatement(_sql).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

}
