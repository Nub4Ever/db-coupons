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
    public ResponseEntity<String> getCouponById(
            @PathVariable Integer id,
            @CookieValue(name = "code", defaultValue = "") String cookieCode
    ){
        Date current = new Date();
        Optional<Coupon> obj = couponService.getCouponById(id);

       if(obj.isEmpty() || obj.get().getExpiryDate().compareTo(current) < 0) {
           String errorMessage = "Not found!";
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
       }

       // cuponul a fost gasit si nu este expirat
        // e prima data cand utilizatorul cere cuponul (nu are un cookie cu el)
        // sau are un cookie care este diferit de codeul cuponului
        if (cookieCode.compareTo("") == 0 || obj.get().getCode().compareTo(cookieCode) != 0) {
            // TODO: cresc numarul de utilizari ale cuponului
            Coupon coupon = obj.get();
            if (coupon.getNumUsesLeft() - 1 < 0) {
                String errorMessage = "No usages left!";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            coupon.setNumUses(coupon.getNumUses() + 1);
            coupon.setNumUsesLeft(coupon.getNumUsesLeft() - 1);
            couponService.saveCoupon(coupon);
        } else {
            // Nu cresc numarul de utilizari
        }

        ResponseCookie springCookie = ResponseCookie.from("code", obj.get().getCode())
                .path("/")
                .build();
        return ResponseEntity .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString()) .build();
    }



}
