/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.entity.transactional;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="itemreturnpurchase")
public class ItemReturnPurchaseEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="quantity", columnDefinition="Decimal(16, 4)")
    private double quantity;
    
    @Column(name="price", columnDefinition="Decimal(16, 4)")
    private double price;
    
    @Column(name="discount", columnDefinition="Decimal(16, 4)")
    private double discount;
    
    @Column(name="cost", columnDefinition="Decimal(16, 4)")
    private double cost;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    
    @ManyToOne
    @JoinColumn(name = "uom_id")
    private UomEntity uom;
    
    @ManyToOne
    @JoinColumn(name = "return_id")
    private ReturnPurchaseEntity returnpurchase;
    
    @Column(name="item_code")
    private String item_code;
    
    @Column(name="item_name")
    private String item_name;
    
    @Column(name="uom_name")
    private String uom_name;
    
    @Column(name="baseuom_name")
    private String baseuom_name;
    
    @Column(name="basequantity", columnDefinition="Decimal(16, 4)")
    private double basequantity;
    
    @Column(name="invoice_ref")
    private String invoice_ref;

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UomEntity getUom() {
        return uom;
    }

    public void setUom(UomEntity uom) {
        this.uom = uom;
    }

    public ReturnPurchaseEntity getReturnpurchase() {
        return returnpurchase;
    }

    public void setReturnpurchase(ReturnPurchaseEntity returnpurchase) {
        this.returnpurchase = returnpurchase;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getUom_name() {
        return uom_name;
    }

    public void setUom_name(String uom_name) {
        this.uom_name = uom_name;
    }

    public String getBaseuom_name() {
        return baseuom_name;
    }

    public void setBaseuom_name(String baseuom_name) {
        this.baseuom_name = baseuom_name;
    }

    public double getBasequantity() {
        return basequantity;
    }

    public void setBasequantity(double basequantity) {
        this.basequantity = basequantity;
    }

    public String getInvoice_ref() {
        return invoice_ref;
    }

    public void setInvoice_ref(String invoice_ref) {
        this.invoice_ref = invoice_ref;
    }
    
}
