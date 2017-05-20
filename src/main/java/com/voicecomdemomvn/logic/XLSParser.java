/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.voicecomdemomvn.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Neibot
 */
public class XLSParser //парсер XLS, принимает путь к файлу, выдаёт его содержимое в виде ArrayList
{                      //использует Apache POI
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private Iterator<Row> rowIterator;
    private Iterator<Cell> cellIterator;
    private ArrayList <String> results;
    
    public XLSParser (String filePath)
    {
        if (!"".equals(filePath))
        {
            if (filePath.toLowerCase().endsWith(".xls"))
            {    
                try 
                {
                    results = new ArrayList <> (); //т.к. во входном файле только один столбец - коллекция одномерная
                    FileInputStream file = new FileInputStream(new File(filePath));
                    workbook = new HSSFWorkbook(file);
                    sheet = workbook.getSheetAt(0);                    
                    rowIterator = sheet.iterator();                   
                    while (rowIterator.hasNext()) 
                    {
                        Row row = rowIterator.next();
                        cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) 
                        {                            
                            Cell cell = cellIterator.next();
                            cell.setCellType(CellType.STRING);                            
                            results.add(cell.getStringCellValue());                            
                        }
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Входной файл не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE, null);
                    Logger.getLogger(XLSParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Не могу прочитать входной файл!", "Ошибка!", JOptionPane.ERROR_MESSAGE, null);
                    Logger.getLogger(XLSParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Входной файл должен быть *.xls", "Ошибка!", JOptionPane.ERROR_MESSAGE, null);
        }
        else
            JOptionPane.showMessageDialog(null, "Входной файл не выбран!", "Ошибка!", JOptionPane.ERROR_MESSAGE, null);
    }

    public ArrayList<String> getResults() 
    {
        return results;
    }    
}
