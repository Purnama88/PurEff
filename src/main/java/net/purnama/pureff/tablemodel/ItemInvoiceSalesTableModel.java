/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;

/**
 *
 * @author purnama
 */
public class ItemInvoiceSalesTableModel extends AbstractTableModel {
    
    private List<ItemInvoiceSalesEntity> iteminvoicesaleslist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    };
    
    public ItemInvoiceSalesTableModel(List<ItemInvoiceSalesEntity> iteminvoicesaleslist){
        super();
        this.iteminvoicesaleslist = iteminvoicesaleslist;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicesaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceSalesEntity iis = iteminvoicesaleslist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getItem_code(),
            iis.getItem_name(),
            iis.getFormattedquantity(), 
            iis.getUom_name(),
            iis.getFormattedprice(),
            iis.getFormatteddiscount(),
            "(" + iis.getFormatteddiscount_percentage() + "%)",
            iis.getFormattedtotal()};
        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public List<ItemInvoiceSalesEntity> getItemInvoiceSalesList(){
        return iteminvoicesaleslist;
    }
    
    public void setItemInvoiceSalesList(ArrayList<ItemInvoiceSalesEntity> iteminvoicesaleslist){
        this.iteminvoicesaleslist = iteminvoicesaleslist;
        fireTableDataChanged();
    }
}

