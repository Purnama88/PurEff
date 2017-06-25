/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="menu")
public class MenuEntity implements Serializable{
    
    public static final boolean [] MENU_TRANSACTION = {
        true,
        false,
        true,
        true,
        true,
        
        true,
        true,
        true,
        false,
        false,
        
        false,
        false,
        false,
        false,
        true,
        
        true,
        false,
        false,
        true,
        true,
        
        false,
        false,
        false,
        false,
        false
    };
    
    public static final String [] MENU_NAMES = {    
        "Item Adjustment",
        "Currency",
        "Delivery",
        "Expenses",
        "Invoice Warehouse In",
        
        "Invoice Warehouse Out",
        "Invoice Purchase",
        "Invoice Sales",
        "Item",
        "Item Group",
        
        "Menu",
        "Numbering Name",
        "Partner",
        "Partner Type",
        "Incoming Payment",
        
        "Outgoing Payment",
        "Payment Type",
        "Report",
        "Return Purchase",
        "Return Sales",
        
        "Role",
        "Synchronize",
        "Uom",
        "User",
        "Warehouse",
    };
    
    @Id
    private int id;
    
    @Column(name="transactional")
    private boolean transactional;

    @Column(name="name")
    private String name;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
