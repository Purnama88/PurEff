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
@Table(name="role")
public class RoleEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="name", unique=true)
    private String name;

    @Column(name="note", columnDefinition="text")
    private String note;
    
    @Column(name="status")
    private boolean status;
    
    @Column(name="defaultrole")
    private boolean defaultrole;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar lastmodified;
    
    @Column(name="user_read")
    private boolean user_read;
    @Column(name="user_write")
    private boolean user_write;
    
    @Column(name="role_read")
    private boolean role_read;
    @Column(name="role_write")
    private boolean role_write;
    
    @Column(name="numberingname_read")
    private boolean numberingname_read;
    @Column(name="numberingname_write")
    private boolean numberingname_write;
    
    @Column(name="numbering_read")
    private boolean numbering_read;
    @Column(name="numbering_write")
    private boolean numbering_write;
    
    @Column(name="warehouse_write")
    private boolean warehouse_write;
    @Column(name="warehouse_read")
    private boolean warehouse_read;
    
    @Column(name="partnertype_read")
    private boolean partnertype_read;
    @Column(name="partnertype_write")
    private boolean partnertype_write;
    
    @Column(name="partner_read")
    private boolean partner_read;
    @Column(name="partner_write")
    private boolean partner_write;
    
    @Column(name="uom_read")
    private boolean uom_read;
    @Column(name="uom_write")
    private boolean uom_write;
    
    @Column(name="itemgroup_read")
    private boolean itemgroup_read;
    @Column(name="itemgroup_write")
    private boolean itemgroup_write;
    
    @Column(name="item_read")
    private boolean item_read;
    @Column(name="item_write")
    private boolean item_write;
    
    @Column(name="currency_read")
    private boolean currency_read;
    @Column(name="currency_write")
    private boolean currency_write;
    
    @Column(name="invoicesales_read")
    private boolean invoicesales_read;
    @Column(name="invoicesales_write")
    private boolean invoicesales_write;
    
    @Column(name="invoicepurchase_read")
    private boolean invoicepurchase_read;
    @Column(name="invoicepurchase_write")
    private boolean invoicepurchase_write;
    
    @Column(name="expenses_read")
    private boolean expenses_read;
    @Column(name="expenses_write")
    private boolean expenses_write;
    
    @Column(name="returnsales_read")
    private boolean returnsales_read;
    @Column(name="returnsales_write")
    private boolean returnsales_write;
    
    @Column(name="returnpurchase_read")
    private boolean returnpurchase_read;
    @Column(name="returnpurchase_write")
    private boolean returnpurchase_write;
    
    @Column(name="adjustment_read")
    private boolean adjustment_read;
    @Column(name="adjustment_write")
    private boolean adjustment_write;
    
    @Column(name="invoicewarehousein_read")
    private boolean invoicewarehousein_read;
    @Column(name="invoicewarehousein_write")
    private boolean invoicewarehousein_write;
    
    @Column(name="invoicewarehouseout_read")
    private boolean invoicewarehouseout_read;
    @Column(name="invoicewarehouseout_write")
    private boolean invoicewarehouseout_write;
    
    @Column(name="delivery_read")
    private boolean delivery_read;
    @Column(name="delivery_write")
    private boolean delivery_write;
    
    @Column(name="paymentin_read")
    private boolean paymentin_read;
    @Column(name="paymentin_write")
    private boolean paymentin_write;
    
    @Column(name="paymentout_read")
    private boolean paymentout_read;
    @Column(name="paymentout_write")
    private boolean paymentout_write;
    
    @Column(name="paymenttype_read")
    private boolean paymenttype_read;
    @Column(name="paymenttype_write")
    private boolean paymenttype_write;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lastmodifiedby")
    private UserEntity lastmodifiedby;

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }
    
    public Calendar getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }

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

    public boolean isDefaultrole() {
        return defaultrole;
    }

    public void setDefaultrole(boolean defaultrole) {
        this.defaultrole = defaultrole;
    }

    public boolean isUser_read() {
        return user_read;
    }

    public void setUser_read(boolean user_read) {
        this.user_read = user_read;
    }

    public boolean isUser_write() {
        return user_write;
    }

    public void setUser_write(boolean user_write) {
        this.user_write = user_write;
    }

    public boolean isRole_read() {
        return role_read;
    }

    public void setRole_read(boolean role_read) {
        this.role_read = role_read;
    }

    public boolean isRole_write() {
        return role_write;
    }

    public void setRole_write(boolean role_write) {
        this.role_write = role_write;
    }

    public boolean isNumberingname_read() {
        return numberingname_read;
    }

    public void setNumberingname_read(boolean numberingname_read) {
        this.numberingname_read = numberingname_read;
    }

    public boolean isNumberingname_write() {
        return numberingname_write;
    }

    public void setNumberingname_write(boolean numberingname_write) {
        this.numberingname_write = numberingname_write;
    }

    public boolean isNumbering_read() {
        return numbering_read;
    }

    public void setNumbering_read(boolean numbering_read) {
        this.numbering_read = numbering_read;
    }

    public boolean isNumbering_write() {
        return numbering_write;
    }

    public void setNumbering_write(boolean numbering_write) {
        this.numbering_write = numbering_write;
    }

    public boolean isWarehouse_write() {
        return warehouse_write;
    }

    public void setWarehouse_write(boolean warehouse_write) {
        this.warehouse_write = warehouse_write;
    }

    public boolean isWarehouse_read() {
        return warehouse_read;
    }

    public void setWarehouse_read(boolean warehouse_read) {
        this.warehouse_read = warehouse_read;
    }

    public boolean isPartnertype_read() {
        return partnertype_read;
    }

    public void setPartnertype_read(boolean partnertype_read) {
        this.partnertype_read = partnertype_read;
    }

    public boolean isPartnertype_write() {
        return partnertype_write;
    }

    public void setPartnertype_write(boolean partnertype_write) {
        this.partnertype_write = partnertype_write;
    }

    public boolean isPartner_read() {
        return partner_read;
    }

    public void setPartner_read(boolean partner_read) {
        this.partner_read = partner_read;
    }

    public boolean isPartner_write() {
        return partner_write;
    }

    public void setPartner_write(boolean partner_write) {
        this.partner_write = partner_write;
    }

    public boolean isUom_read() {
        return uom_read;
    }

    public void setUom_read(boolean uom_read) {
        this.uom_read = uom_read;
    }

    public boolean isUom_write() {
        return uom_write;
    }

    public void setUom_write(boolean uom_write) {
        this.uom_write = uom_write;
    }

    public boolean isItemgroup_read() {
        return itemgroup_read;
    }

    public void setItemgroup_read(boolean itemgroup_read) {
        this.itemgroup_read = itemgroup_read;
    }

    public boolean isItemgroup_write() {
        return itemgroup_write;
    }

    public void setItemgroup_write(boolean itemgroup_write) {
        this.itemgroup_write = itemgroup_write;
    }

    public boolean isItem_read() {
        return item_read;
    }

    public void setItem_read(boolean item_read) {
        this.item_read = item_read;
    }

    public boolean isItem_write() {
        return item_write;
    }

    public void setItem_write(boolean item_write) {
        this.item_write = item_write;
    }

    public boolean isInvoicesales_read() {
        return invoicesales_read;
    }

    public void setInvoicesales_read(boolean invoicesales_read) {
        this.invoicesales_read = invoicesales_read;
    }

    public boolean isInvoicesales_write() {
        return invoicesales_write;
    }

    public void setInvoicesales_write(boolean invoicesales_write) {
        this.invoicesales_write = invoicesales_write;
    }

    public boolean isInvoicepurchase_read() {
        return invoicepurchase_read;
    }

    public void setInvoicepurchase_read(boolean invoicepurchase_read) {
        this.invoicepurchase_read = invoicepurchase_read;
    }

    public boolean isInvoicepurchase_write() {
        return invoicepurchase_write;
    }

    public void setInvoicepurchase_write(boolean invoicepurchase_write) {
        this.invoicepurchase_write = invoicepurchase_write;
    }

    public boolean isExpenses_read() {
        return expenses_read;
    }

    public void setExpenses_read(boolean expenses_read) {
        this.expenses_read = expenses_read;
    }

    public boolean isExpenses_write() {
        return expenses_write;
    }

    public void setExpenses_write(boolean expenses_write) {
        this.expenses_write = expenses_write;
    }

    public boolean isReturnsales_read() {
        return returnsales_read;
    }

    public void setReturnsales_read(boolean returnsales_read) {
        this.returnsales_read = returnsales_read;
    }

    public boolean isReturnsales_write() {
        return returnsales_write;
    }

    public void setReturnsales_write(boolean returnsales_write) {
        this.returnsales_write = returnsales_write;
    }

    public boolean isReturnpurchase_read() {
        return returnpurchase_read;
    }

    public void setReturnpurchase_read(boolean returnpurchase_read) {
        this.returnpurchase_read = returnpurchase_read;
    }

    public boolean isReturnpurchase_write() {
        return returnpurchase_write;
    }

    public void setReturnpurchase_write(boolean returnpurchase_write) {
        this.returnpurchase_write = returnpurchase_write;
    }

    public boolean isAdjustment_read() {
        return adjustment_read;
    }

    public void setAdjustment_read(boolean adjustment_read) {
        this.adjustment_read = adjustment_read;
    }

    public boolean isAdjustment_write() {
        return adjustment_write;
    }

    public void setAdjustment_write(boolean adjustment_write) {
        this.adjustment_write = adjustment_write;
    }

    public boolean isInvoicewarehousein_read() {
        return invoicewarehousein_read;
    }

    public void setInvoicewarehousein_read(boolean invoicewarehousein_read) {
        this.invoicewarehousein_read = invoicewarehousein_read;
    }

    public boolean isInvoicewarehousein_write() {
        return invoicewarehousein_write;
    }

    public void setInvoicewarehousein_write(boolean invoicewarehousein_write) {
        this.invoicewarehousein_write = invoicewarehousein_write;
    }

    public boolean isInvoicewarehouseout_read() {
        return invoicewarehouseout_read;
    }

    public void setInvoicewarehouseout_read(boolean invoicewarehouseout_read) {
        this.invoicewarehouseout_read = invoicewarehouseout_read;
    }

    public boolean isInvoicewarehouseout_write() {
        return invoicewarehouseout_write;
    }

    public void setInvoicewarehouseout_write(boolean invoicewarehouseout_write) {
        this.invoicewarehouseout_write = invoicewarehouseout_write;
    }

    public boolean isCurrency_read() {
        return currency_read;
    }

    public void setCurrency_read(boolean currency_read) {
        this.currency_read = currency_read;
    }

    public boolean isCurrency_write() {
        return currency_write;
    }

    public void setCurrency_write(boolean currency_write) {
        this.currency_write = currency_write;
    }

    public boolean isDelivery_read() {
        return delivery_read;
    }

    public void setDelivery_read(boolean delivery_read) {
        this.delivery_read = delivery_read;
    }

    public boolean isDelivery_write() {
        return delivery_write;
    }

    public void setDelivery_write(boolean delivery_write) {
        this.delivery_write = delivery_write;
    }

    public boolean isPaymentin_read() {
        return paymentin_read;
    }

    public void setPaymentin_read(boolean paymentin_read) {
        this.paymentin_read = paymentin_read;
    }

    public boolean isPaymentin_write() {
        return paymentin_write;
    }

    public void setPaymentin_write(boolean paymentin_write) {
        this.paymentin_write = paymentin_write;
    }

    public boolean isPaymentout_read() {
        return paymentout_read;
    }

    public void setPaymentout_read(boolean paymentout_read) {
        this.paymentout_read = paymentout_read;
    }

    public boolean isPaymentout_write() {
        return paymentout_write;
    }

    public void setPaymentout_write(boolean paymentout_write) {
        this.paymentout_write = paymentout_write;
    }

    public boolean isPaymenttype_read() {
        return paymenttype_read;
    }

    public void setPaymenttype_read(boolean paymenttype_read) {
        this.paymenttype_read = paymenttype_read;
    }

    public boolean isPaymenttype_write() {
        return paymenttype_write;
    }

    public void setPaymenttype_write(boolean paymenttype_write) {
        this.paymenttype_write = paymenttype_write;
    }
}
