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
import net.purnama.pureff.util.GlobalFields;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="partner")
public class PartnerEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="code", unique=true)
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="contactname")
    private String contactname;
    
    @Column(name="address", columnDefinition="text")
    private String address;
    
    @Column(name="paymentdue")
    private int paymentdue;
    
    @Column(name="maximumdiscount", columnDefinition="Decimal(9, 4)")
    private double maximumdiscount;
    
    @Column(name="maximumbalance", columnDefinition="Decimal(16, 4)")
    private double maximumbalance;
    
    @Column(name="balance", columnDefinition="Decimal(16, 4)")
    private double balance;
    
    @Column(name="phonenumber")
    private String phonenumber;
    
    @Column(name="phonenumber2")
    private String phonenumber2;
    
    @Column(name="faxnumber")
    private String faxnumber;
    
    @Column(name="mobilenumber")
    private String mobilenumber;
    
    @Column(name="email")
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "partnertype")
    private PartnerTypeEntity partnertype;
    
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

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPaymentdue() {
        return paymentdue;
    }

    public void setPaymentdue(int paymentdue) {
        this.paymentdue = paymentdue;
    }

    public double getMaximumdiscount() {
        return maximumdiscount;
    }

    public void setMaximumdiscount(double maximumdiscount) {
        this.maximumdiscount = maximumdiscount;
    }

    public double getMaximumbalance() {
        return maximumbalance;
    }

    public void setMaximumbalance(double maximumbalance) {
        this.maximumbalance = maximumbalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getFaxnumber() {
        return faxnumber;
    }

    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PartnerTypeEntity getPartnertype() {
        return partnertype;
    }

    public void setPartnertype(PartnerTypeEntity partnertype) {
        this.partnertype = partnertype;
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
    
    @JsonIgnore
    public String getFormattedbalance(){
        
        return GlobalFields.NUMBERFORMAT.format(getBalance());
    }
    
    @JsonIgnore
    public String getFormattedmaximumbalance(){
        if(getMaximumbalance() >= 0){
            return GlobalFields.NUMBERFORMAT.format(getMaximumbalance());
        }
        else{
            return "UNLIMITED";
        }
    }
}
