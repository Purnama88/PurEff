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

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="item")
public class ItemEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="code", unique=true)
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="cost", columnDefinition="Decimal(16, 4)")
    private double cost;
    
    @ManyToOne
    @JoinColumn(name = "itemgroup_id")
    private ItemGroupEntity itemgroup;

    @ManyToOne
    @JoinColumn(name = "selluom_id")
    private UomEntity selluom;
    
    @ManyToOne
    @JoinColumn(name = "buyuom_id")
    private UomEntity buyuom;
    
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ItemGroupEntity getItemgroup() {
        return itemgroup;
    }

    public void setItemgroup(ItemGroupEntity itemgroup) {
        this.itemgroup = itemgroup;
    }

    public UomEntity getSelluom() {
        return selluom;
    }

    public void setSelluom(UomEntity selluom) {
        this.selluom = selluom;
    }

    public UomEntity getBuyuom() {
        return buyuom;
    }

    public void setBuyuom(UomEntity buyuom) {
        this.buyuom = buyuom;
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
}
