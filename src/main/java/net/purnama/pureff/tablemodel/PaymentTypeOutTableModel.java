/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;

/**
 *
 * @author purnama
 */
public class PaymentTypeOutTableModel extends AbstractTableModel{
    private List<PaymentTypeOutEntity> paymenttypeoutlist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    };
    
    public PaymentTypeOutTableModel(List<PaymentTypeOutEntity> paymenttypeoutlist){
        super();
        
        this.paymenttypeoutlist = paymenttypeoutlist;
    }
    
    @Override
    public int getRowCount() {
        return paymenttypeoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        PaymentTypeOutEntity ig = paymenttypeoutlist.get(row);
        
        if(col == 8){
            ig.setStatus(!ig.isStatus());
            
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(getPaymentTypeOut(row).isValid()){
            return col == 8;
        }
        else{
            return false;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentTypeOutEntity ig = paymenttypeoutlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            ig.getFormattedtype(), 
            ig.getFormatteddate(), 
            ig.getFormattedduedate(),
            ig.getBank(),
            ig.getNumber(),
            ig.getExpirydate(),
            ig.getFormattedamount(),
            ig.isStatus(),
            ig.isValid()
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
    
    public void addRow(PaymentTypeOutEntity paymenttypeout) {
        int rowCount = getRowCount();

        paymenttypeoutlist.add(paymenttypeout);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<PaymentTypeOutEntity> getPaymentTypeOutList(){
        return paymenttypeoutlist;
    }
    
    public void setPaymentTypeOutList(ArrayList<PaymentTypeOutEntity> paymenttypeoutlist){
        this.paymenttypeoutlist = paymenttypeoutlist;
        fireTableDataChanged();
    }
    
    public PaymentTypeOutEntity getPaymentTypeOut(int index){
        return paymenttypeoutlist.get(index);
    }
    
    public double deleteRow(int index){
        double value = paymenttypeoutlist.get(index).getAmount();
        paymenttypeoutlist.remove(index);
        fireTableDataChanged();
        
        return value;
    }
}
