package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.*;
import com.TMDT.api.Api.springboot.mapper.CustomerMapper;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.models.Order;
import com.TMDT.api.Api.springboot.models.VerificationCode;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.service.CustomerService;
import com.TMDT.api.Api.springboot.service.EmailService;
import com.TMDT.api.Api.springboot.service.JwtService;
import com.TMDT.api.Api.springboot.service.VerificationCodeService;
import jakarta.mail.MessagingException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerControllers {

    //Tạo ra biến userRepository, giống như singleton
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    private CustomerDTO getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomerDTO) authentication.getPrincipal();
    }

    // get /api/v1/users/getAll
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllCustomers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Kiểm tra người dùng có tồn tại không
        CustomerDTO currentCustomer = customerService.getByEmail(email);

        // Lấy danh sách khách hàng và chuyển thành DTO
        List<Customer> customers = customerService.getAllCustomer(); // giả sử trả về List<Customer>
        List<CustomerDTO> customerDTOs = customerMapper.toListDto(customers); // cần có hàm ánh xạ

        return ResponseEntity.ok(new ResponseObject("ok", "Success", customerDTOs));
    }

    // /api/v1/products/1
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CustomerDTO currentCustomer = customerService.getByEmail(email);

        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("failed", "Unauthorized", ""));
        }

        if (currentCustomer.getId() != id) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject("failed", "Forbidden", ""));
        }

        Optional<Customer> foundUser = customerRepository.findById(id);
        return foundUser.map(customer -> ResponseEntity.ok(new ResponseObject("ok", "Success", customer))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot find user by id = " + id, "")));
    }

    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestBody LoginReqDTO loginReqDTO) {
        CustomerDTO customerDTO = customerService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());

        if (customerDTO.getStatus() == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("inactive", "account inactive", "")
            );
        }

        String token = jwtService.generateToken(customerDTO.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("customer", customerDTO);
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Login successful", response)
        );
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> register(@RequestBody CustomerDTO customer, @RequestParam String verificationCode) {
        if (!verificationCodeService.isExist(customer.getEmail(), verificationCode)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Invalid verification code", "")
            );
        }
        if (customerService.isExistEmail(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Email already exists", "")
            );
        }
        CustomerDTO newCustomer = customerService.register(customer);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Register successful", newCustomer)
        );
    }

    @PutMapping("/update")
    ResponseEntity<ResponseObject> update(@RequestBody UpdateCustomerDTO customerDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CustomerDTO currentCustomer = customerService.getByEmail(email);
        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("failed", "Unauthorized", ""));
        }
        if (customerDTO.getId() != currentCustomer.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseObject("failed", "Forbidden", ""));
        }

        Customer updatedCustomer = customerService.updateInfo(customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", "Update failed", ""));
        }

        return ResponseEntity.ok(new ResponseObject("ok", "Update successful", updatedCustomer));
    }

    @GetMapping("/sendVerificationEmail")
    ResponseEntity<ResponseObject> sendVerificationEmail(@RequestParam String email, @RequestParam String name) {
        try {
            String code = customerService.generateVerificationCode();
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setEmail(email);
            verificationCode.setCode(code);
            verificationCode.setExpirationTime(LocalDateTime.now().plusMinutes(5));
            verificationCodeService.save(verificationCode);

            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("name", name);
            templateModel.put("verificationCode", code);

            emailService.sendHtmlEmailVerificationCode(email, "Your Verification Code", templateModel);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Send Verification Code successful", "")
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Send Verification Code fail", "")
            );
        }
    }

    @GetMapping("/sendNewPasswordEmail")
    ResponseEntity<ResponseObject> sendForgotPasswordEmail(@RequestParam String email) {
        try {
            String newPassword = customerService.generatePassword();
            CustomerDTO customer = customerService.getByEmail(email);
            customer.setPassword(newPassword);
            customerService.update(customer);

            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("name", customer.getUsername());
            templateModel.put("newPassword", newPassword);

            emailService.sendHtmlEmailNewPassword(email, "New Password!", templateModel);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Send new password successful", templateModel.get("newPassword"))
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Send new password fail", "")
            );
        }
    }

    @PostMapping("/forgotPassword")
    ResponseEntity<ResponseObject> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        CustomerDTO customer = customerService.getByEmail(forgotPasswordDTO.getEmail());
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Email not found", "")
            );
        }

        if (!verificationCodeService.isExist(customer.getEmail(), forgotPasswordDTO.getVerifyCode())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Invalid verification code", "")
            );
        } else {
            customer.setPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
            customerService.update(customer);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Forgot password successful", "")
            );
        }

    }

    @PutMapping("/updatePassword")
    ResponseEntity<ResponseObject> updatePassword(@RequestBody UpdateCustomerPasswordDTO customerDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDTO currentCustomer = (CustomerDTO) authentication.getPrincipal();
        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed", "Customer not found", ""));
        }
        Customer foundCustomer = customerService.updatePassword(currentCustomer.getId(), customerDTO.getPassword(), customerDTO.getNewPassword());
        return foundCustomer != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update password successful", "")
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Update password fail", "")
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CustomerDTO currentCustomer = customerService.getByEmail(email);

        boolean isDeleted = customerService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseObject("ok", "Delete user successful", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "User not found", null));
        }
    }

    @PutMapping("/updateRole")
    public ResponseEntity<ResponseObject> updateRole(@RequestBody UpdateRoleDTO updateRoleDTO) {
        CustomerDTO currentCustomer = getCurrentCustomer();
        if (currentCustomer.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("ok", "Order status updated", ""));
        }
        CustomerDTO customerDTO = customerService.updateRole(updateRoleDTO.getId(), updateRoleDTO.getRole());
        return ResponseEntity.ok(new ResponseObject("ok", "Role updated", customerDTO));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<ResponseObject> updateRole(@RequestBody UpdateStatusDTO updateStatusDTO) {
        CustomerDTO currentCustomer = getCurrentCustomer();
        if (currentCustomer.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("ok", "Order status updated", ""));
        }
        CustomerDTO customerDTO = customerService.updateStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus());
        return ResponseEntity.ok(new ResponseObject("ok", "Role updated", customerDTO));
    }

}
