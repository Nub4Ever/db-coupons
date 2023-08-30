package com.db.coupon.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true)
    String code;
    Date expiryDate;
    Integer numUsesLeft;
    Integer numUses;
    Double discount;
}
