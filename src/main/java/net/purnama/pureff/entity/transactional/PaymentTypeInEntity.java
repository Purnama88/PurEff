/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity.transactional;

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
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.util.GlobalFields;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="paymenttypein")
public class PaymentTypeInEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="type")
    private int type;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar date;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar duedate;
    
    @Column(name="bank")
    private String bank;
    
    @Column(name="number")
    private String number;
    
    @Column(name="expirydate")
    private String expirydate;
    
    @Column(name="amount", columnDefinition="Decimal(16, 4)")
    private double amount;
    
    @Column(name="valid")
    private boolean valid;
    
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentInEntity paymentin;
    
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getDuedate() {
        return duedate;
    }

    public void setDuedate(Calendar duedate) {
        this.duedate = duedate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public PaymentInEntity getPaymentin() {
        return paymentin;
    }

    public void setPaymentin(PaymentInEntity paymentin) {
        this.paymentin = paymentin;
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
    public String getFormatteddate(){
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
    }
    
    @JsonIgnore
    public String getFormattedduedate(){
        return GlobalFields.DATEFORMAT.format(getDuedate().getTime());
    }
    
    @JsonIgnore
    public String getFormattedamount(){
        return GlobalFields.NUMBERFORMAT.format(getAmount());
    }
    
    @JsonIgnore
    public String getFormattedtype(){
        return PAYMENT_TYPE[getType()];
    }
    
    public static final String [] PAYMENT_TYPE = {
        "CASH",
        "TRANSFER",
        "CREDIT CARD",
        "CHEQUE",
    };
}
