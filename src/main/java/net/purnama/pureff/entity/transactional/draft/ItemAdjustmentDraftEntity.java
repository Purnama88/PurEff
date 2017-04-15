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

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="itemadjustmentdraft")
public class ItemAdjustmentDraftEntity implements Serializable {
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="tstock", columnDefinition="Decimal(16, 4)")
    private double tstock;
    
    @Column(name="quantity", columnDefinition="Decimal(16, 4)")
    private double quantity;
    
    @Column(name="remark")
    private String remark;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    
    @ManyToOne
    @JoinColumn(name = "adjustment_id")
    private AdjustmentDraftEntity adjustmentdraft;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTstock() {
        return tstock;
    }

    public void setTstock(double tstock) {
        this.tstock = tstock;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public AdjustmentDraftEntity getAdjustmentdraft() {
        return adjustmentdraft;
    }

    public void setAdjustmentdraft(AdjustmentDraftEntity adjustmentdraft) {
        this.adjustmentdraft = adjustmentdraft;
    }
}
