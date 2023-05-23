/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rhoma
 */
public class Config {
    private static Connection mysqlConfig;
    public static Connection ConfigDB()throws SQLException{
        if(mysqlConfig==null){
            try {
                String url = "jdbc:mysql://localhost/aplikasi_mengkriuk";
                String user = "root";
                String pass = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                mysqlConfig=DriverManager.getConnection(url,user,pass);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal");
            }
        }
        return mysqlConfig;
    }
}
