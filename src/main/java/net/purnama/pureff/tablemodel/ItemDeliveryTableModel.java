/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;

/**
 *
 * @author purnama
 */
public class ItemDeliveryTableModel extends AbstractTableModel{
    
    private List<ItemDeliveryEntity> itemdeliverylist;
    
    String[] columnNames = new String[]{
        "", 
        "",
        "",
        ""};
    
    public ItemDeliveryTableModel(List<ItemDeliveryEntity> itemdeliverylist) {
        super();
        this.itemdeliverylist = itemdeliverylist;
    }
    
    @Override
    public int getRowCount() {
        return itemdeliverylist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemDeliveryEntity id = itemdeliverylist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            id.getQuantity(), 
            id.getDescription(),
            id.getRemarks()};
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
    
    public List<ItemDeliveryEntity> getItemDeliveryList(){
        return itemdeliverylist;
    }
    
    public void setItemDeliveryList(ArrayList<ItemDeliveryEntity> itemdeliverylist){
        this.itemdeliverylist = itemdeliverylist;
        fireTableDataChanged();
    }
}
