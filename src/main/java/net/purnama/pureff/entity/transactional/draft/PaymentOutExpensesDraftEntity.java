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
import net.purnama.pureff.entity.transactional.ExpensesEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="paymentoutexpensesdraft")
public class PaymentOutExpensesDraftEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="amount", columnDefinition="Decimal(16, 4)")
    private double amount;
    
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentOutDraftEntity paymentoutdraft;
    
    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private ExpensesEntity expenses;

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

    public PaymentOutDraftEntity getPaymentoutdraft() {
        return paymentoutdraft;
    }

    public void setPaymentoutdraft(PaymentOutDraftEntity paymentoutdraft) {
        this.paymentoutdraft = paymentoutdraft;
    }

    public ExpensesEntity getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesEntity expenses) {
        this.expenses = expenses;
    }
    
}
