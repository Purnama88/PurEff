/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;

/**
 *
 * @author purnama
 */
public class ItemExpensesTableModel extends AbstractTableModel {
    
    private List<ItemExpensesEntity> itemexpenseslist;
    
    String[] columnNames = new String[]{
        "", 
        "",
        "",
        "",
        "",
        "",
        ""
    };
    
    public ItemExpensesTableModel(List<ItemExpensesEntity> itemexpenseslist){
        super();
        this.itemexpenseslist = itemexpenseslist;
    }
    
    @Override
    public int getRowCount() {
        return itemexpenseslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemExpensesEntity iis = itemexpenseslist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getDescription(),
            iis.getFormattedquantity(), 
            iis.getFormattedprice(),
            iis.getFormatteddiscount(),
            "(" + iis.getFormatteddiscount_percentage() + "%)",
            iis.getFormattedtotal()
        };
        return values[columnIndex];
    }

    public List<ItemExpensesEntity> getItemExpensesList(){
        return itemexpenseslist;
    }
    
    public void setItemExpensesList(ArrayList<ItemExpensesEntity> itemexpenseslist){
        this.itemexpenseslist = itemexpenseslist;
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}

