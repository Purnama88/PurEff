/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.PaymentInEntity;

/**
 *
 * @author purnama
 */
public class PaymentInTableModel2 extends AbstractTableModel{
    
    private List<PaymentInEntity> paymentinlist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "",
        "", 
        "",
        ""
    };
    
    public PaymentInTableModel2(List<PaymentInEntity> paymentinlist){
        super();
        
        this.paymentinlist = paymentinlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentInEntity is = paymentinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(), 
            is.getFormattedduedate(),
            is.getPartner_name(), 
            is.getFormattedamount(),
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
    
    public void setPaymentInList(ArrayList<PaymentInEntity> paymentinlist){
        this.paymentinlist = paymentinlist;
        fireTableDataChanged();
    }
    
    public PaymentInEntity getPaymentIn(int index){
        return paymentinlist.get(index);
    }
}
