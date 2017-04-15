/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.entity.transactional.draft;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="paymentininvoicesalesdraft")
public class PaymentInInvoiceSalesDraftEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="amount", columnDefinition="Decimal(16, 4)")
    private double amount;
    
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceSalesEntity invoicesales;
    
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentInDraftEntity paymentindraft;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InvoiceSalesEntity getInvoicesales() {
        return invoicesales;
    }

    public void setInvoicesales(InvoiceSalesEntity invoicesales) {
        this.invoicesales = invoicesales;
    }

    public PaymentInDraftEntity getPaymentindraft() {
        return paymentindraft;
    }

    public void setPaymentindraft(PaymentInDraftEntity paymentindraft) {
        this.paymentindraft = paymentindraft;
    }
    
}
