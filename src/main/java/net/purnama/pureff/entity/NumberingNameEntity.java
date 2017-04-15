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
@Table(name="numberingname")
public class NumberingNameEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="name", unique=true)
    private String name;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar begin;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar end;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getBegin() {
        return begin;
    }

    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
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
