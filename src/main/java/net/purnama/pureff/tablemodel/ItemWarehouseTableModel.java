/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;

/**
 *
 * @author purnama
 */
public class ItemWarehouseTableModel extends AbstractTableModel{
    private List<ItemWarehouseEntity> itemwarehouselist;
    
    String[] columnNames = new String[]{
        "",
        "",
        "",
        "",
        ""
    };
    
    public ItemWarehouseTableModel(List<ItemWarehouseEntity> itemwarehouselist){
        super();
        
        this.itemwarehouselist = itemwarehouselist;
    }
    
    @Override
    public int getRowCount() {
        return itemwarehouselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemWarehouseEntity iw = itemwarehouselist.get(rowIndex);
        
        Object[] values = new Object[]{
            iw.getItem().getCode(),
            iw.getItem().getName(),
            iw.getItem().getItemgroup().getName(),
            String.valueOf(iw.getStock()),
            iw.getItem().isStatus()
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
    
    public List<ItemWarehouseEntity> getItemWarehouseList(){
        return itemwarehouselist;
    }
    
    public void setItemWarehouseList(ArrayList<ItemWarehouseEntity> itemwarehouselist){
        this.itemwarehouselist = itemwarehouselist;
        fireTableDataChanged();
    }
    
    public ItemWarehouseEntity getItemWarehouse(int index){
        return itemwarehouselist.get(index);
    }
    
    public ItemEntity getItem(int index){
        return itemwarehouselist.get(index).getItem();
    }
}
