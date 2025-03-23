package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/carts")
public class CartControllers {
    @Autowired
    private CartService cartService;

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getCartByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.getCartByCustomerId(customerId)));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseObject> getCartByCartId(@PathVariable int cartId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.get(cartId)));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> add(@RequestBody CartDetailDTO cartDetail) {
        CartDetail cartDetail1 = cartService.getCartByCustomerIdAndProductIdAndPhoneCategoryId(cartDetail.getCustomerId(), cartDetail.getProductId(), cartDetail.getPhoneCategoryId());
        if (cartDetail1 == null) {
            return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.add(cartDetail)));
        }
        cartDetail1.setQuantity(cartDetail1.getQuantity() + cartDetail.getQuantity());
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.update(cartDetail1.getId(), cartDetail1.getQuantity())));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestParam int quantity) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.update(id, quantity)));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable int id) {
        cartService.delete(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @DeleteMapping("/deleteAllByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> deleteAllByCustomerId(@PathVariable int customerId) {
        cartService.deleteAllByCustomerId(customerId);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @PostMapping("/getTotalPrice")
    public ResponseEntity<ResponseObject> getTotalPrice(@RequestBody List<CartDetail> cartDetails) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.calculateTotalAmount(cartDetails)));
    }


    public CartDetail clearProperty(CartDetail cartDetail) {
        cartDetail.setCustomer(null);
        cartDetail.getProduct().setCategory(null);
        if (cartDetail.getProduct().getProductPhoneCategories() != null) {
            cartDetail.getProduct().getProductPhoneCategories().clear();
        }
        if (cartDetail.getPhoneCategory() != null && cartDetail.getPhoneCategory().getProductPhoneCategories() != null) {
            cartDetail.getPhoneCategory().getProductPhoneCategories().clear();
        }
        return cartDetail;
    }

    public List<CartDetail> clearProperties(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            clearProperty(cartDetail);
        }
        return cartDetails;
    }

}
