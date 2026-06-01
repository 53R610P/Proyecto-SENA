/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yeyo_
 */

public class MyTableModel extends AbstractTableModel {
    private Object[][] data;
    private String[] columnNames;

    public MyTableModel(Object[][] data, String[] columnNames){
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount(){
        return data.length;
    }

    @Override
    public int getColumnCount(){
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column){
        if (row >= 0 && row < data.length && column >=0 && column < data[0].length) {
            return data[row][column];
        }
        return null;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column){
        for(int row = 0; row < getRowCount(); row++) {
            Object o = getValueAt(row, column);
            if(o != null) {
                return o.getClass();
            }
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false; // Evitar edición de celdas
    }
}