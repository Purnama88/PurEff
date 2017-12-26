/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;

/**
 *
 * @author purnama
 */
public class PaymentOutTableModel2 extends AbstractTableModel{
    
    private List<PaymentOutEntity> paymentoutlist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "",
        "", 
        "",
        ""
    };
    
    public PaymentOutTableModel2(List<PaymentOutEntity> paymentoutlist){
        super();
        
        this.paymentoutlist = paymentoutlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentOutEntity is = paymentoutlist.get(rowIndex);
        
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
    
    public void setPaymentOutList(ArrayList<PaymentOutEntity> paymentoutlist){
        this.paymentoutlist = paymentoutlist;
        fireTableDataChanged();
    }
    
    public PaymentOutEntity getPaymentOut(int index){
        return paymentoutlist.get(index);
    }
}
