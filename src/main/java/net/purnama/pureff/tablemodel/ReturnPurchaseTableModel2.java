/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;

/**
 *
 * @author purnama
 */
public class ReturnPurchaseTableModel2 extends AbstractTableModel{
    
    private List<ReturnPurchaseEntity> returnpurchaselist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "", 
        "",
        "",
        ""
    };
    
    public ReturnPurchaseTableModel2(List<ReturnPurchaseEntity> returnpurchaselist){
        super();
        
        this.returnpurchaselist = returnpurchaselist;
    }
    
    @Override
    public int getRowCount() {
        return returnpurchaselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReturnPurchaseEntity is = returnpurchaselist.get(rowIndex);
        
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
    
    public void setReturnPurchaseList(ArrayList<ReturnPurchaseEntity> returnpurchaselist){
        this.returnpurchaselist = returnpurchaselist;
        fireTableDataChanged();
    }
    
    public ReturnPurchaseEntity getReturnPurchase(int index){
        return returnpurchaselist.get(index);
    }
}
