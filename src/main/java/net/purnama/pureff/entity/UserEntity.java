/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="user")
public class UserEntity implements Serializable {
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="code", unique=true)
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="username", unique=true)
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="dateforward")
    private boolean dateforward;
    
    @Column(name="datebackward")
    private boolean datebackward;
    
    @Column(name="raise_buyprice")
    private boolean raise_buyprice;
    
    @Column(name="raise_sellprice")
    private boolean raise_sellprice;
    
    @Column(name="lower_buyprice")
    private boolean lower_buyprice;
    
    @Column(name="lower_sellprice")
    private boolean lower_sellprice;
    
    @Column(name="discount", columnDefinition="Decimal(9, 4)")
    private double discount;
    
    @Column(name="note", columnDefinition="text")
    private String note;
    
    @Column(name="status")
    private boolean status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar lastmodified;
    
//    @JsonIgnoreProperties("lastmodifiedby")
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lastmodifiedby")
    private UserEntity lastmodifiedby;
    
    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userwarehouse", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "warehouse_id",
                nullable = false)})
    private Set<WarehouseEntity> warehouses;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDateforward() {
        return dateforward;
    }

    public void setDateforward(boolean dateforward) {
        this.dateforward = dateforward;
    }

    public boolean isDatebackward() {
        return datebackward;
    }

    public void setDatebackward(boolean datebackward) {
        this.datebackward = datebackward;
    }

    public boolean isRaise_buyprice() {
        return raise_buyprice;
    }

    public void setRaise_buyprice(boolean raise_buyprice) {
        this.raise_buyprice = raise_buyprice;
    }

    public boolean isRaise_sellprice() {
        return raise_sellprice;
    }

    public void setRaise_sellprice(boolean raise_sellprice) {
        this.raise_sellprice = raise_sellprice;
    }

    public boolean isLower_buyprice() {
        return lower_buyprice;
    }

    public void setLower_buyprice(boolean lower_buyprice) {
        this.lower_buyprice = lower_buyprice;
    }

    public boolean isLower_sellprice() {
        return lower_sellprice;
    }

    public void setLower_sellprice(boolean lower_sellprice) {
        this.lower_sellprice = lower_sellprice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public Set<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }
}
