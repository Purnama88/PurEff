/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ExpensesEntity;

/**
 *
 * @author purnama
 */
public class ExpensesTableModel2 extends AbstractTableModel{
    
    private List<ExpensesEntity> expenseslist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "", 
        "",
        "",
        ""
    };
    
    public ExpensesTableModel2(List<ExpensesEntity> expenseslist){
        super();
        
        this.expenseslist = expenseslist;
    }
    
    @Override
    public int getRowCount() {
        return expenseslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExpensesEntity is = expenseslist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(),  
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedtotal_after_tax(),
            is.getFormattedstatus()
            };
        return values[columnIndex];
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    
    @Override
    public Class getColumnClass(int c) {  
        return getValueAt(0, c).getClass();
    }
    
    public void setExpensesList(ArrayList<ExpensesEntity> expenseslist){
        this.expenseslist = expenseslist;
        fireTableDataChanged();
    }
    
    public ExpensesEntity getExpenses(int index){
        return expenseslist.get(index);
    }
}
