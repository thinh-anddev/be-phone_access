package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.ReqOrderDTO;
import com.TMDT.api.Api.springboot.models.*;
import com.TMDT.api.Api.springboot.repositories.CartRepository;
import com.TMDT.api.Api.springboot.repositories.OrderDetailRepository;
import com.TMDT.api.Api.springboot.repositories.OrderRepository;
import com.TMDT.api.Api.springboot.service.*;
import com.TMDT.api.Api.springboot.utils.PaymentConfig;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentControllers {
    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody List<CartDetail> cartDetails, @RequestParam int point, @RequestParam int transportFee) throws UnsupportedEncodingException {
        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((cartService.calculateTotalAmount(cartDetails) - point * 1000 + transportFee) * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "1");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", "172.16.2.173");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl + "?point=" + point + "&shippingFee=" + transportFee);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);
        ResponseObject responseObject = new ResponseObject("ok", "Success", paymentUrl);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/payment_success")
    public ResponseEntity<ResponseObject> paymentSuccess(@RequestBody ReqOrderDTO orderDTO) throws MessagingException {
        Customer customer = customerService.get(orderDTO.getCustomerId());
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Customer not found", ""));
        }

        List<CartDetail> cartDetails = cartService.getListCart(orderDTO.getCartDetailIds());

        int total = cartService.calculateTotalAmount(cartDetails);
        customer.setPoint((customer.getPoint() - orderDTO.getPoint()) + total / 1000);
        customerService.update(customer);

        Order order = new Order();
        order.setAddress(orderDTO.getAddress());
        order.setCustomer(customer);
        order.setCreateDate(LocalDateTime.now());
        order.setDeliveryId(orderDTO.getDeliveryId());
        order.setDiscount(orderDTO.getPoint() * 1000);
        order.setPaymentDate(LocalDateTime.now());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setNote(orderDTO.getNote());
        order.setTotal(total);
        order.setStatus(1);
        order.setShippingFee(orderDTO.getShippingFee());
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            productService.updateSold(cartDetail.getProduct().getId(), cartDetail.getQuantity());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setStatus(1);
            orderDetail.setPrice((int) (cartDetail.getProduct().getPrice() * cartDetail.getQuantity()));
            orderDetail.setPhoneCategory(cartDetail.getPhoneCategory());
            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);
        cartRepository.deleteAllById(orderDTO.getCartDetailIds());
        order.setOrderDetails(orderDetails);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", customer.getUsername());
        templateModel.put("email", customer.getEmail());
        templateModel.put("phone", customer.getPhone());
        templateModel.put("orderId", order.getId());
        templateModel.put("address", order.getAddress());
        templateModel.put("total", order.getTotal());
        templateModel.put("discount", order.getDiscount());
        templateModel.put("shippingFee", order.getShippingFee());
        templateModel.put("paymentDate", order.getPaymentDate().format(formatter));
        templateModel.put("status", order.getPaymentStatus() == 2 ? "Unpaid" : "Paid");
        templateModel.put("deliveryId", order.getDeliveryId());
        templateModel.put("orderDetails", orderDetails);

        emailService.sendHtmlEmailPaymentSuccess(customer.getEmail(), "Booking Success", templateModel);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", order));
    }


}
