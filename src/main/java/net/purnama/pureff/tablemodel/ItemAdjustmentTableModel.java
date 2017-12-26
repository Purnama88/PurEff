/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;

/**
 *
 * @author purnama
 */
public class ItemAdjustmentTableModel extends AbstractTableModel{
    
    private List<ItemAdjustmentEntity> itemadjustmentlist;
    
    String[] columnNames = new String[]{
        "", 
        "",
        "",
        "",
        "",
        "",
        ""
    };
    
    public ItemAdjustmentTableModel(List<ItemAdjustmentEntity> itemadjustmentlist){
        super();
        
        this.itemadjustmentlist = itemadjustmentlist;
    }
    
    @Override
    public int getRowCount() {
        return itemadjustmentlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemAdjustmentEntity id = itemadjustmentlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            id.getItem_code(),
            id.getItem_name(),
            id.getFormattedtstock(),
            id.getFormatteddiff(),
            id.getFormattedquantity(),
            id.getRemark()
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
    
    public void setItemAdjustmentList(ArrayList<ItemAdjustmentEntity> itemadjustmentlist){
        this.itemadjustmentlist = itemadjustmentlist;
        fireTableDataChanged();
    }
    
    public List<ItemAdjustmentEntity> getItemAdjustmentList(){
        return itemadjustmentlist;
    }
    
    public ItemAdjustmentEntity getItemAdjustment(int index){
        return itemadjustmentlist.get(index);
    }
}

