/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.voicecomdemomvn.swingforms;

import com.voicecomdemomvn.logic.XLSParser;
import com.voicecomdemomvn.logic.MainClass;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Neibot
 */
public class MainForm extends javax.swing.JFrame 
{    
    public MainForm() //инициализация и настройки отображения формы
    {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);        
        this.setTitle("Excel Handler");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectInputFileButton = new javax.swing.JButton();
        inputFilePathField = new javax.swing.JTextField();
        UploadDataToDatabaseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SelectInputFileButton.setText("Выбрать входной файл");
        SelectInputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectInputFileButtonActionPerformed(evt);
            }
        });

        UploadDataToDatabaseButton.setText("Выгрузить данные в БД");
        UploadDataToDatabaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadDataToDatabaseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UploadDataToDatabaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputFilePathField, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SelectInputFileButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputFilePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectInputFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UploadDataToDatabaseButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Кнопка выбора файла
    private void SelectInputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectInputFileButtonActionPerformed
        JFileChooser fileopen = new JFileChooser();
        fileopen.setFileFilter(new FileNameExtensionFilter("XLS файл", "xls"));
        int ret = fileopen.showDialog(null, "Выбор файла");                
        if (ret == JFileChooser.APPROVE_OPTION)
            inputFilePathField.setText(String.valueOf(fileopen.getSelectedFile())); 
    }//GEN-LAST:event_SelectInputFileButtonActionPerformed

    //Кнопка выгрузки данных в БД
    private void UploadDataToDatabaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadDataToDatabaseButtonActionPerformed
        XLSParser excelParser = new XLSParser(inputFilePathField.getText());
        MainClass.exportToSQL(excelParser.getResults());
    }//GEN-LAST:event_UploadDataToDatabaseButtonActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SelectInputFileButton;
    private javax.swing.JButton UploadDataToDatabaseButton;
    private javax.swing.JTextField inputFilePathField;
    // End of variables declaration//GEN-END:variables
}
