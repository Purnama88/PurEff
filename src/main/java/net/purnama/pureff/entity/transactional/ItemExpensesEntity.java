/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.entity.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.purnama.pureff.util.GlobalFields;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="itemexpenses")
public class ItemExpensesEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="quantity", columnDefinition="Decimal(16, 4)")
    private double quantity;
    
    @Column(name="price", columnDefinition="Decimal(16, 4)")
    private double price;
    
    @Column(name="discount", columnDefinition="Decimal(16, 4)")
    private double discount;
    
    @Column(name="description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "expenses_id")
    private ExpensesEntity expenses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpensesEntity getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesEntity expenses) {
        this.expenses = expenses;
    }
    
    @JsonIgnore
    public String getFormattedquantity(){
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }
    
    @JsonIgnore
    public String getFormattedprice(){
        return GlobalFields.NUMBERFORMAT.format(getPrice());
    }
    
    @JsonIgnore
    public String getFormatteddiscount(){
        return GlobalFields.NUMBERFORMAT.format(getDiscount());
    }
    
    @JsonIgnore
    public double getSubtotal() {
        return getQuantity() * getPrice();
    }
    
    @JsonIgnore
    public double getDiscount_percentage() {
        return getDiscount() / getSubtotal() * 100;
    }
    
    @JsonIgnore
    public String getFormatteddiscount_percentage(){
        return GlobalFields.NUMBERFORMAT.format(getDiscount_percentage());
    }
    
    @JsonIgnore
    public double getTotal() {
        return getSubtotal() - getDiscount();
    }
    
    @JsonIgnore
    public String getFormattedtotal(){
        return GlobalFields.NUMBERFORMAT.format(getTotal());
    }
}
