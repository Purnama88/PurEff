/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;

/**
 *
 * @author purnama
 */
public class ReturnSalesTableModel2 extends AbstractTableModel{
    
    private List<ReturnSalesEntity> returnsaleslist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "", 
        "",
        "",
        ""
    };
    
    public ReturnSalesTableModel2(List<ReturnSalesEntity> returnsaleslist){
        super();
        
        this.returnsaleslist = returnsaleslist;
    }
    
    @Override
    public int getRowCount() {
        return returnsaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReturnSalesEntity is = returnsaleslist.get(rowIndex);
        
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
    
    public void setReturnSalesList(ArrayList<ReturnSalesEntity> returnsaleslist){
        this.returnsaleslist = returnsaleslist;
        fireTableDataChanged();
    }
    
    public ReturnSalesEntity getReturnSales(int index){
        return returnsaleslist.get(index);
    }
}
