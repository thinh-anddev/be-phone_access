package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CustomerDTO;
import com.TMDT.api.Api.springboot.mapper.OrderMapper;
import com.TMDT.api.Api.springboot.models.Order;
import com.TMDT.api.Api.springboot.service.CustomerService;
import com.TMDT.api.Api.springboot.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/orders")
public class OrderControllers {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderMapper orderMapper;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CustomerDTO currentCustomer = customerService.getByEmail(email);
        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("failed", "Unauthorized", null));
        }
        if (currentCustomer.getId() != customerId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseObject("failed", "Forbidden", null));
        }

        // Lấy danh sách order của customer, map sang DTO
        var orders = orderService.getByCustomer(customerId);

        return ResponseEntity.ok(new ResponseObject("ok", "success", orders));
    }

    @GetMapping("/cancelOrder")
    public ResponseEntity<ResponseObject> cancelOrder(@RequestParam int orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CustomerDTO currentCustomer = customerService.getByEmail(email);
        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("failed", "Unauthorized", null));
        }

        Order canceled = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new ResponseObject("ok", "Order canceled successfully", canceled));
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ResponseObject> updateStatus(@RequestParam int orderId, @RequestParam int status) {
        // Thêm kiểm tra quyền admin nếu cần

        Order updated = orderService.updateStatus(orderId, status);
        return ResponseEntity.ok(new ResponseObject("ok", "Order status updated", updated));
    }
}
