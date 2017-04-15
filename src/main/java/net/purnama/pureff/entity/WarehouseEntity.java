/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Purnama
 */

@Entity
@Table(name="warehouse")
public class WarehouseEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="code", unique=true)
    private String code;
    
    @Column(name="address", columnDefinition="text")
    private String address;
    
    @Column(name="defaultwarehouse")
    private boolean defaultwarehouse;
    
    @Column(name="url")
    private String url;
    
    @Column(name="port")
    private String port;
    
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
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "warehouses")
    private Set<UserEntity> users;
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public Calendar getLastmodified() {
        return lastmodified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean isDefaultwarehouse() {
        return defaultwarehouse;
    }

    public void setDefaultwarehouse(boolean defaultwarehouse) {
        this.defaultwarehouse = defaultwarehouse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
