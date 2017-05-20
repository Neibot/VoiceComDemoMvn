/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.voicecomdemomvn.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Neibot
 */
public class DBConnect { //класс для подключения к БД, использует PostgreSQL JDBC Driver
    
    //т.к. пример демонстрационный - данные для подключения просто захардкожены
    private String con_string = "jdbc:postgresql://localhost:5432/VoiceComDemoBase";
    private String con_login = "postgres";
    private String con_pass = "123456";
    private Statement st;
    private ResultSet rs;
    
    public DBConnect ()
    {   
        try {            
            DriverManager.registerDriver(new org.postgresql.Driver());
            Connection con = DriverManager.getConnection(con_string,con_login,con_pass);
            if (con != null)                              
                st = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Подключение не удалось!\nПроверьте правильность учетных данных!", "Ошибка", JOptionPane.ERROR_MESSAGE, null);
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ResultSet getRs() { //получение ResultSet
        return rs;
    }
    
    void select (String query) // запросы Select
    {        
        try {        
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Неверный запрос SELECT к БД!\n"+query, "Ошибка", JOptionPane.ERROR_MESSAGE, null);            
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
       
    void exec (String query) // остальные запросы
    {
        try {        
            st.execute(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Неверный запрос к БД!\n"+query, "Ошибка", JOptionPane.ERROR_MESSAGE, null);
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    void close() // отключение соединения
    {
        try {
            st.getConnection().close();            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
