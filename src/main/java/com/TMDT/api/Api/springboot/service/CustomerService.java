package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.CustomerDTO;
import com.TMDT.api.Api.springboot.dto.UpdateCustomerDTO;
import com.TMDT.api.Api.springboot.dto.UpdateCustomerPasswordDTO;
import com.TMDT.api.Api.springboot.mapper.CustomerMapper;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

    public List<Customer> getAllCustomer() {
        return clearProperties(customerRepository.findAll());
    }

    public Customer get(int id) {
        return clearProperty(Objects.requireNonNull(customerRepository.findById(id).orElse(null)));
    }

    public CustomerDTO getByEmail(String email) {
        return customerMapper.toDto(customerRepository.findByEmail(email));
    }

    //    public CustomerDTO getByEmail(String email) {
//        return customerRepository.findByEmail(email);
//    }
    public CustomerDTO login(String email, String password) {
        Customer foundUser = customerRepository.findByEmail(email);
        CustomerDTO userDTO = customerMapper.toDto(foundUser);
        return userDTO != null && passwordEncoder.matches(password, userDTO.getPassword()) ? userDTO : null;
    }

    public CustomerDTO register(CustomerDTO customerDTO) {
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    public Customer forgotPassword(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return null;
        }
        String newPassword = generatePassword();
        customer.setPassword(passwordEncoder.encode(newPassword));
        return customerRepository.save(customer);
    }

    public boolean isExistEmail(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    public Customer updateInfo(UpdateCustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).orElse(null);
        if (customer == null) {
            return null;
        }
        customer.setUsername(customerDTO.getUserName());
        customer.setPhone(customerDTO.getPhone());
        return clearProperty(customerRepository.save(customer));
    }

    public Customer updatePassword(int id, String password, String newPassword) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            return null;
        }
        customer.setPassword(passwordEncoder.encode(newPassword));
        return customerRepository.save(customer);
    }

    public Customer clearProperty(Customer customer) {
        customer.setOrders(null);
        if (customer.getOrders() != null) {
            customer.getCartDetails().forEach(cartDetail -> {
                cartDetail.setCustomer(null);
                cartDetail.setPhoneCategory(null);
                cartDetail.getProduct().setCategory(null);
                if (cartDetail.getProduct().getProductPhoneCategories() != null) {
                    cartDetail.getProduct().getProductPhoneCategories().clear();
                }
            });
        }
        return customer;
    }
    public boolean deleteById(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Customer> clearProperties(List<Customer> customers) {
        for (Customer customer : customers) {
            clearProperty(customer);
        }
        return customers;
    }
}
