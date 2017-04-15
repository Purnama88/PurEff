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
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="expenses")
public class ExpensesEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="number")
    private String number;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar date;
    
    @Column(name="printed")
    private int printed;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
    
    @Column(name = "draftid")
    private String draftid;
    
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
    
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;
    
    @Column(name="rate")
    private double rate;
    
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;
    
    @Column(name="currency_code")
    private String currency_code;
    
    @Column(name="currency_name")
    private String currency_name;
    
    @Column(name="partner_name")
    private String partner_name;
    
    @Column(name="partner_code")
    private String partner_code;
    
    @Column(name="partner_address")
    private String partner_address;
    
    @Column(name="user_code")
    private String user_code;
    
    @Column(name="warehouse_code")
    private String warehouse_code;
    
    @Column(name="paid", columnDefinition="Decimal(16, 4)")
    private double paid;
    
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

    private double discount_percentage;
    
    private double total_before_tax;
    
    private double tax_percentage;
    
    private double total_after_tax;
    
    private double total_defaultcurrency;
    
    private double remaining;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getPrinted() {
        return printed;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public String getDraftid() {
        return draftid;
    }

    public void setDraftid(String draftid) {
        this.draftid = draftid;
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

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getPartner_address() {
        return partner_address;
    }

    public void setPartner_address(String partner_address) {
        this.partner_address = partner_address;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getWarehouse_code() {
        return warehouse_code;
    }

    public void setWarehouse_code(String warehouse_code) {
        this.warehouse_code = warehouse_code;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
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

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double getTotal_before_tax() {
        return total_before_tax;
    }

    public void setTotal_before_tax(double total_before_tax) {
        this.total_before_tax = total_before_tax;
    }

    public double getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(double tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public double getTotal_after_tax() {
        return total_after_tax;
    }

    public void setTotal_after_tax(double total_after_tax) {
        this.total_after_tax = total_after_tax;
    }

    public double getTotal_defaultcurrency() {
        return total_defaultcurrency;
    }

    public void setTotal_defaultcurrency(double total_defaultcurrency) {
        this.total_defaultcurrency = total_defaultcurrency;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }
    
}
