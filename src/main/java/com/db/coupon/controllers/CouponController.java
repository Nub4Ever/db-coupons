package com.db.coupon.controllers;

import com.db.coupon.dto.CouponDTO;
import com.db.coupon.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CouponController {
    @Autowired
    CouponService couponService;

    @GetMapping("/coupons")
    List<CouponDTO> getAllCoupons() {
        return couponService.getAllCoupons();
    }
}
