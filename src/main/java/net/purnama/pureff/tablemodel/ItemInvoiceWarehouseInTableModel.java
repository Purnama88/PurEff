/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;

/**
 *
 * @author purnama
 */
public class ItemInvoiceWarehouseInTableModel extends AbstractTableModel {
    
    private List<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist;
    
    String[] columnNames = new String[]{
        "", 
        "",
        "",
        "",
        ""
    };
    
    public ItemInvoiceWarehouseInTableModel(List<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist){
        super();
        this.iteminvoicewarehouseinlist = iteminvoicewarehouseinlist;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicewarehouseinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceWarehouseInEntity iis = iteminvoicewarehouseinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getItem_code(),
            iis.getItem_name(),
            iis.getFormattedquantity(), 
            iis.getUom_name()
        };
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
    
    public List<ItemInvoiceWarehouseInEntity> getItemInvoiceWarehouseInList(){
        return iteminvoicewarehouseinlist;
    }
    
    public void setItemInvoiceWarehouseInList(ArrayList<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist){
        this.iteminvoicewarehouseinlist = iteminvoicewarehouseinlist;
        fireTableDataChanged();
    }
}
