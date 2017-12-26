/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;

/**
 *
 * @author purnama
 */
public class InvoiceSalesTableModel2 extends AbstractTableModel{
    
    private List<InvoiceSalesEntity> invoicesaleslist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "", 
        "",
        "",
        ""
    };
    
    public InvoiceSalesTableModel2(List<InvoiceSalesEntity> invoicesaleslist){
        super();
        
        this.invoicesaleslist = invoicesaleslist;
    }
    
    @Override
    public int getRowCount() {
        return invoicesaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceSalesEntity is = invoicesaleslist.get(rowIndex);
        
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
    
    public void setInvoiceSalesList(ArrayList<InvoiceSalesEntity> invoicesaleslist){
        this.invoicesaleslist = invoicesaleslist;
        fireTableDataChanged();
    }
    
    public InvoiceSalesEntity getInvoiceSales(int index){
        return invoicesaleslist.get(index);
    }
}
