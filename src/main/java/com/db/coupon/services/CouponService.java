package com.db.coupon.services;

import com.db.coupon.dto.CouponDTO;
import com.db.coupon.models.Coupon;
import com.db.coupon.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    CouponRepository couponRepository;

    public List<CouponDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDTO> result = new ArrayList<>();
        for (var c : coupons) {
            result.add(new CouponDTO(c.getCode(), c.getExpiryDate(), c.getNumUses(), c.getDiscount()));
        }
        return result;
    }

    public Optional<Coupon> getCouponById(Integer id){
        return couponRepository.findById(id);
    }
}
