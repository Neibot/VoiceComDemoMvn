/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.voicecomdemomvn.logic;

import com.voicecomdemomvn.swingforms.MainForm;
import java.util.ArrayList;

/**
 *
 * @author Neibot
 */
public class MainClass 
{   
    public volatile static int databaseScanCyclesCounter = 1; //глобальный счётчик проходов по БД 
    public static final int SCAN_PER_THREAD_QUANTITY = 2000; //количество проходов на каждый поток, при бесконечном сканировании не учитывается
    public static final int THREAD_QUANTITY = 5; //количество потоков
    public static final boolean ENDLESS_SCAN = false; //режим бесконечного сканирования
    
    public static void main(String args[])
    {   
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true); //выводим Swing-интерфейс для пользователя, через который он сможет загрузить данные из XLS в БД
                
        ArrayList <TableScanThread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_QUANTITY; i++) //инициализируем и запускаем потоки фонового сканирования БД
        {
            threads.add(i, new TableScanThread());
            threads.get(i).start();
        }               
    }
    
    //метод принимает результат работы парсера XLS и выгружает данные в БД, предварительно удалив уже имеющиеся
    public static void exportToSQL(ArrayList <String> results) 
    {
        DBConnect dbCon = new DBConnect();
        String query = "DELETE FROM public.\"Phones\"";
        dbCon.exec(query);        
        for (String phone_number : results)
        {         
            query = "INSERT INTO public.\"Phones\"" +
                    "(phone) VALUES " +
                    "('"+phone_number+"');";
            dbCon.exec(query);
        }        
    } 
}
