/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="numbering",
        uniqueConstraints = {@UniqueConstraint(columnNames = 
                {"prefix", "numberingname", "menu", "warehouse"})})
public class NumberingEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="prefix")
    private String prefix;
    
    @Column(name="start")
    private int start;
    
    @Column(name="end")
    private int end;
    
    @Column(name="current")
    private int current;

    @ManyToOne
    @JoinColumn(name = "numberingname")
    private NumberingNameEntity numberingname;
    
    @ManyToOne
    @JoinColumn(name = "menu")
    private MenuEntity menu;
    
    @ManyToOne
    @JoinColumn(name = "warehouse")
    private WarehouseEntity warehouse;
    
    @Column(name="note", columnDefinition="text")
    private String note;
    
    @Column(name="status")
    private boolean status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar lastmodified;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lastmodifiedby")
    private UserEntity lastmodifiedby;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrent() {
        return current;
    }

    public String getFormattedCurrent(){
        return String.format(getFormat(), getCurrent());
    }
    
    public void setCurrent(int current) {
        this.current = current;
    }

    public NumberingNameEntity getNumberingname() {
        return numberingname;
    }

    public void setNumberingname(NumberingNameEntity numberingname) {
        this.numberingname = numberingname;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }
    
    public String getFormat(){
        return "%0" + getLength() + "d";
    }
    
    public int getLength(){
        try{
            return String.valueOf(getEnd()).length();
        }
        catch(Exception e){
            return 1;
        }
    }
    
    public String getCurrentId(){
        return getPrefix() + getFormattedCurrent();
    }
}
