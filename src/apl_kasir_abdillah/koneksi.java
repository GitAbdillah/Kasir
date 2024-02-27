/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apl_kasir_abdillah;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ACER
 */
public class koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi(){
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_abdillah";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Berhasil");
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        return koneksi;
    }
    public static void main(String args[]){
        getKoneksi();
    }
    
}
