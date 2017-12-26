/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;

/**
 *
 * @author purnama
 */
public class InvoicePurchaseTableModel2 extends AbstractTableModel{
    
    private List<InvoicePurchaseEntity> invoicepurchaselist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "", 
        "",
        "",
        ""
    };
    
    public InvoicePurchaseTableModel2(List<InvoicePurchaseEntity> invoicepurchaselist){
        super();
        
        this.invoicepurchaselist = invoicepurchaselist;
    }
    
    @Override
    public int getRowCount() {
        return invoicepurchaselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoicePurchaseEntity is = invoicepurchaselist.get(rowIndex);
        
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
    
    public void setInvoicePurchaseList(ArrayList<InvoicePurchaseEntity> invoicepurchaselist){
        this.invoicepurchaselist = invoicepurchaselist;
        fireTableDataChanged();
    }
    
    public InvoicePurchaseEntity getInvoicePurchase(int index){
        return invoicepurchaselist.get(index);
    }
}
