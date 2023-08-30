package com.db.coupon.controllers;

import com.db.coupon.dto.CouponDTO;
import com.db.coupon.models.Coupon;
import com.db.coupon.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CouponController {
    @Autowired
    CouponService couponService;

    @GetMapping("/coupons")
    List<CouponDTO> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/coupon/{id}")
    public ResponseEntity<String> getCouponById(@PathVariable Integer id){
        Date current = new Date();
        Optional<Coupon> obj = couponService.getCouponById(id);

       if(obj.isEmpty() || obj.get().getExpiryDate().compareTo(current) < 0) {
           String errorMessage = "Not found!";
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
       }

        ResponseCookie springCookie = ResponseCookie.from("code", obj.get().getCode())
                .path("/")
                .build();
        return ResponseEntity .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString()) .build();
    }



}
