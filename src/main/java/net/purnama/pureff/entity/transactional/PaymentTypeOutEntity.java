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
import net.purnama.pureff.entity.UserEntity;

/**
 *
 * @author Purnama
 */
@Entity
@Table(name="paymenttypeout")
public class PaymentTypeOutEntity implements Serializable{
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="type")
    private int type;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar date;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar duedate;
    
    @Column(name="bank")
    private String bank;
    
    @Column(name="number")
    private String number;
    
    @Column(name="expirydate")
    private String expirydate;
    
    @Column(name="amount", columnDefinition="Decimal(16, 4)")
    private double amount;
    
    @Column(name="valid")
    private boolean valid;
    
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentOutEntity paymentout;
    
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
}
