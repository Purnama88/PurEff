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

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="itemdeliverydraft")
public class ItemDeliveryDraftEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="description")
    private String description;
    
    @Column(name="quantity")
    private String quantity;
    
    @Column(name="remark")
    private String remark;
    
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryDraftEntity deliverydraft;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeliveryDraftEntity getDeliverydraft() {
        return deliverydraft;
    }

    public void setDeliverydraft(DeliveryDraftEntity deliverydraft) {
        this.deliverydraft = deliverydraft;
    }
    
}
