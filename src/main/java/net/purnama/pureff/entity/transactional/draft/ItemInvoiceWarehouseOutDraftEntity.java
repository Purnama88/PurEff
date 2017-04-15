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
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="iteminvoicewarehouseoutdraft")
public class ItemInvoiceWarehouseOutDraftEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="quantity", columnDefinition="Decimal(16, 4)")
    private double quantity;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft;
    
    @ManyToOne
    @JoinColumn(name = "uom_id")
    private UomEntity uom;

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

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public InvoiceWarehouseOutDraftEntity getInvoicewarehouseoutdraft() {
        return invoicewarehouseoutdraft;
    }

    public void setInvoicewarehouseoutdraft(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        this.invoicewarehouseoutdraft = invoicewarehouseoutdraft;
    }

    public UomEntity getUom() {
        return uom;
    }

    public void setUom(UomEntity uom) {
        this.uom = uom;
    }
}
