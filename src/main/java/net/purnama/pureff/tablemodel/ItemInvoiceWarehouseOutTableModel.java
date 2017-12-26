/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;

/**
 *
 * @author purnama
 */
public class ItemInvoiceWarehouseOutTableModel extends AbstractTableModel {
    
    private List<ItemInvoiceWarehouseOutEntity> iteminvoicewarehouseoutlist;
    
    String[] columnNames = new String[]{
        "", 
        "",
        "",
        "",
        ""
    };
    
    public ItemInvoiceWarehouseOutTableModel(
            List<ItemInvoiceWarehouseOutEntity> iteminvoicewarehouseoutlist){
        super();
        this.iteminvoicewarehouseoutlist = iteminvoicewarehouseoutlist;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicewarehouseoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceWarehouseOutEntity iis = iteminvoicewarehouseoutlist.get(rowIndex);
        
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
    
    public List<ItemInvoiceWarehouseOutEntity> getItemInvoiceWarehouseOutList(){
        return iteminvoicewarehouseoutlist;
    }
    
    public void setItemInvoiceWarehouseOutList(ArrayList<ItemInvoiceWarehouseOutEntity> iteminvoicewarehouseoutlist){
        this.iteminvoicewarehouseoutlist = iteminvoicewarehouseoutlist;
        fireTableDataChanged();
    }
}
