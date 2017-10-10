/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity.transactional.draft;

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
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="expensesdraft")
public class ExpensesDraftEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "numbering_id")
    private NumberingEntity numbering;
    
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar date;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar duedate;
    
    @Column(name="subtotal", columnDefinition="Decimal(16, 4)")
    private double subtotal;
    
    @Column(name="discount", columnDefinition="Decimal(16, 4)")
    private double discount;
    
    @Column(name="tax", columnDefinition="Decimal(16, 4)")
    private double tax;
    
    @Column(name="freight", columnDefinition="Decimal(16, 4)")
    private double freight;
    
    @Column(name="rounding", columnDefinition="Decimal(16, 4)")
    private double rounding;
    
    @Column(name="rate", columnDefinition="Decimal(16, 4)")
    private double rate;
    
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;
    
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;
    
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

    public NumberingEntity getNumbering() {
        return numbering;
    }

    public void setNumbering(NumberingEntity numbering) {
        this.numbering = numbering;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public Calendar getDuedate() {
        return duedate;
    }

    public void setDuedate(Calendar duedate) {
        this.duedate = duedate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getRounding() {
        return rounding;
    }

    public void setRounding(double rounding) {
        this.rounding = rounding;
    }
    
    @JsonIgnore
    public double getDiscount_percentage(){
        return GlobalFunctions.round((discount/subtotal)*100);
    }
}
