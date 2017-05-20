/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.voicecomdemomvn.logic;

import static com.voicecomdemomvn.logic.MainClass.ENDLESS_SCAN;
import static com.voicecomdemomvn.logic.MainClass.SCAN_PER_THREAD_QUANTITY;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static com.voicecomdemomvn.logic.MainClass.databaseScanCyclesCounter;

/**
 *
 * @author Neibot
 */
public class TableScanThread extends Thread {
        
    @Override
    public void run() 
    {
        DBConnect dbCon = new DBConnect(); //каждому потоку - своё соединение с БД
        if (ENDLESS_SCAN)         
            while (true) //вариант для бесконечного сканирования базы
                tableScan (dbCon);
        else
        {
            for (int i = 0; i < SCAN_PER_THREAD_QUANTITY; i++) //Каждый поток проходит по базе заданное количество раз
                tableScan (dbCon);
            dbCon.close(); //Соединение с БД желательно закрыть, актуально для конечного количества проходов
        }
    }
    
    //Проверка номера телефона на мобильность
    private boolean isMobileCheck(String number)
    {
        return number.matches("([7,8]{1}[\\d]{10})");
    }  
    
    //Метод выполняет один проход по базе и выставляет поле is_mobile в зависимости от номера телефона
    private void tableScan (DBConnect dbCon)
    {        
        String query = "SELECT id, phone, is_mobile FROM public.\"Phones\"";
        dbCon.select(query);
        ResultSet rs = dbCon.getRs();                
        ArrayList <ArrayList <String>> results = new ArrayList<>();                
        try {
            while (rs.next()) //сперва в один запрос (т.к. таблица небольшая) собираем все нужные данные из БД в коллекцию
            {
                ArrayList <String> result_line = new ArrayList<>();
                for (int columnIndex = 1; columnIndex <= rs.getMetaData().getColumnCount(); columnIndex++) 
                    result_line.add(rs.getString(columnIndex));
                results.add(result_line); 
            }
            String id = null;
            String phoneNumber = null;
            String is_mobile = null;
            for (ArrayList result : results) //затем проходим по коллекции, при обнаружении неправильного значения в поле is_mobile - исправляем в БД запросом
            {
                if (result.get(0) != null)
                    id = result.get(0).toString();
                if (result.get(1) != null)
                    phoneNumber = result.get(1).toString();
                if (result.get(2) != null)
                    is_mobile = result.get(2).toString().trim().toLowerCase();
                if (isMobileCheck(phoneNumber))
                {
                    if (is_mobile == null || !"true".equals(is_mobile))
                        query = "UPDATE public.\"Phones\" SET is_mobile=true WHERE id='"+id+"'";
                }
                else
                    if (is_mobile == null || !"false".equals(is_mobile))
                        query = "UPDATE public.\"Phones\" SET is_mobile=false WHERE id='"+id+"'";
                dbCon.exec(query);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка фонового сканирования при работе с БД в потоке "+this.getName()+"!", "Ошибка", JOptionPane.ERROR_MESSAGE, null);
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally 
        {
            System.out.println(databaseScanCyclesCounter+" проход по базе выполнен потоком "+this.getName());
            databaseScanCyclesCounter++;
        }
    }
}
