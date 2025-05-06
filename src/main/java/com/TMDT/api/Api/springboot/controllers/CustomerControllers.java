package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.ForgotPasswordDTO;
import com.TMDT.api.Api.springboot.dto.LoginReqDTO;
import com.TMDT.api.Api.springboot.dto.UpdateCustomerDTO;
import com.TMDT.api.Api.springboot.dto.UpdateCustomerPasswordDTO;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.models.VerificationCode;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.service.CustomerService;
import com.TMDT.api.Api.springboot.service.EmailService;
import com.TMDT.api.Api.springboot.service.VerificationCodeService;
import jakarta.mail.MessagingException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    PasswordEncoder passwordEncoder;

    // get /api/v1/users/getAll
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getUsers() {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", customerService.getAllCustomer()));
    }

    // /api/v1/products/1
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable int id) {
        Optional<Customer> foundUser = customerRepository.findById(id);
        return foundUser.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundUser)
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Cannot find user by id = " + id, "")
                );
    }

    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestBody LoginReqDTO loginReqDTO) {
        Customer foundCustomer = customerService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return foundCustomer != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Login successful", foundCustomer)
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Invalid username or password", "")
                );
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> register(@RequestBody Customer customer, @RequestParam String verificationCode) {
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
        Customer newCustomer = customerService.register(customer);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Register successful", newCustomer)
        );
    }

    @PutMapping("/update")
    ResponseEntity<ResponseObject> update(@RequestBody UpdateCustomerDTO customerDTO) {
        Customer customer = customerService.updateInfo(customerDTO);
        return customer == null ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("failed", "Update failed", ""))
                :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update successful", customer));
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
            Customer customer = customerService.getByEmail(email);
            customer.setPassword(newPassword);
            customerService.update(customer);

            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("name", customer.getUsername());
            templateModel.put("newPassword", newPassword);

            emailService.sendHtmlEmailNewPassword(email, "New Password!", templateModel);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Send new password successful", "")
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Send new password fail", "")
            );
        }
    }

    @PostMapping("forgotPassword")
    ResponseEntity<ResponseObject> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        Customer customer = customerService.getByEmail(forgotPasswordDTO.getEmail());
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
        Customer foundCustomer = customerService.updatePassword(customerDTO);
        return foundCustomer != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update password successful", "")
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Update password fail", "")
                );
    }


}
