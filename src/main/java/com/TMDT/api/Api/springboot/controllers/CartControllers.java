package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.dto.CustomerDTO;
import com.TMDT.api.Api.springboot.mapper.CartDetailMapper;
import com.TMDT.api.Api.springboot.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/carts")
public class CartControllers {
    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    private CustomerDTO getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomerDTO) authentication.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getCartByCustomer() {
        CustomerDTO customer = getCurrentCustomer();
        List<CartDetailDTO> data = cartDetailService.getCartByCustomerId(customer.getId());
        return ResponseEntity.ok(new ResponseObject("ok", "Success", data));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseObject> getCartByCartId(@PathVariable int cartId) {
        CartDetailDTO cartDetail = cartDetailMapper.toDto(cartDetailService.get(cartId));
        CustomerDTO customer = getCurrentCustomer();
        if (cartDetail == null || cartDetail.getCustomerId() != customer.getId()) {
            return ResponseEntity.status(403).body(new ResponseObject("failed", "Unauthorized", null));
        }
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartDetail));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> add(@RequestBody CartDetailDTO cartDetail) {
        CustomerDTO customer = getCurrentCustomer();
        cartDetail.setCustomerId(customer.getId());
        CartDetailDTO existing = cartDetailService.getCartByCustomerIdAndProductIdAndPhoneCategoryId(
                customer.getId(), cartDetail.getProductId(), cartDetail.getPhoneCategoryId());

        if (existing == null) {
            return ResponseEntity.ok(new ResponseObject("ok", "Success", cartDetailService.add(cartDetail)));
        }
        existing.setQuantity(existing.getQuantity() + cartDetail.getQuantity());
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartDetailService.update(existing.getId(), existing.getQuantity())));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestParam int quantity) {
        CustomerDTO customer = getCurrentCustomer();
        CartDetailDTO cartDetail =cartDetailMapper.toDto( cartDetailService.get(id));
        if (cartDetail == null || cartDetail.getCustomerId() != customer.getId()) {
            return ResponseEntity.status(403).body(new ResponseObject("failed", "Unauthorized", null));
        }
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartDetailService.update(id, quantity)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable int id) {
        CustomerDTO customer = getCurrentCustomer();
        CartDetailDTO cartDetail = cartDetailMapper.toDto(cartDetailService.get(id));
        if (cartDetail == null || cartDetail.getCustomerId() != customer.getId()) {
            return ResponseEntity.status(403).body(new ResponseObject("failed", "Unauthorized", null));
        }
        cartDetailService.delete(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseObject> deleteAllByCustomerId() {
        CustomerDTO customer = getCurrentCustomer();
        cartDetailService.deleteAllByCustomerId(customer.getId());
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @PostMapping("/getTotalPrice")
    public ResponseEntity<ResponseObject> getTotalPrice(@RequestBody List<CartDetailDTO> cartDetailDtos) {
        if (cartDetailDtos == null) return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
        return ResponseEntity.ok(new ResponseObject("ok", "Success", Optional.of(cartDetailService.calculateTotalAmount(cartDetailDtos))));
    }
}
