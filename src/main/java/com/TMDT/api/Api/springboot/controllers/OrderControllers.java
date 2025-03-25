package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/orders")
public class OrderControllers {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.getAll()));
    }

//    @PostMapping("/insert")
//    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderDTO orderDTO) {
//        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.add(orderDTO.getCartDetailIds(), orderDTO.getOrder(), orderDTO.getAddressId(), orderDTO.getPoint())));
//    }

    @GetMapping("/revenueByYear")
    public ResponseEntity<ResponseObject> getRevenueByYear(@RequestParam int year) {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.getRevenueByYear(year)));
    }

    @GetMapping("/getAllYear")
    public ResponseEntity<ResponseObject> getAllYear() {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.getAllYear()));
    }

    @GetMapping("/revenueByCategory")
    public ResponseEntity<ResponseObject> getRevenueByCategory(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.getRevenueByCategory(year, month)));
    }

    @GetMapping("/getByCustomer")
    public ResponseEntity<ResponseObject> getByCustomer(@RequestParam int customerId) {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.getByCustomer(customerId)));
    }

    @GetMapping("/cancelOrder")
    public ResponseEntity<ResponseObject> cancelOrder(@RequestParam int orderId) {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.cancelOrder(orderId)));
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ResponseObject> updateStatus(@RequestParam int orderId, @RequestParam int status) {
        return ResponseEntity.ok(new ResponseObject("ok", "success", orderService.updateStatus(orderId, status)));
    }
}
