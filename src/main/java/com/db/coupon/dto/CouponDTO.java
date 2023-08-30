package com.db.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;
import java.util.Date;

@Data
@AllArgsConstructor
public class CouponDTO {
    String code;
    Date expiryDate;
    Integer numUses;
    Double discount;
}
