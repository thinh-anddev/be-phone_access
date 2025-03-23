package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.repositories.CartRepository;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.repositories.PhoneCategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<CartDetail> getCartByCustomerId(int customerId) {
        return clearProperties(cartRepository.findByCustomer_Id(customerId));
    }

    public CartDetail get(int id) {
        return clearProperty(cartRepository.findById(id).orElse(null));
    }

    public CartDetail add(CartDetailDTO cartDetailDTO) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setProduct(productRepository.findById(cartDetailDTO.getProductId()).orElse(null));
        cartDetail.setPhoneCategory(phoneCategoryRepository.findById(cartDetailDTO.getPhoneCategoryId()).orElse(null));
        cartDetail.setQuantity(cartDetailDTO.getQuantity());
        cartDetail.setCustomer(customerRepository.findById(cartDetailDTO.getCustomerId()).orElse(null));
        cartDetail.setStatus(1);
        return cartRepository.save(cartDetail);
    }

    public void delete(int id) {
        cartRepository.deleteById(id);
    }

    public void deleteAll(List<CartDetail> cartDetails) {
        cartRepository.deleteAll(cartDetails);
    }

    public void deleteAllByCustomerId(int customerId) {
        cartRepository.deleteCartDetailByCustomer_Id(customerId);
    }

    public CartDetail update(int id, int quantity) {
        CartDetail cartDetail = cartRepository.findById(id).orElse(null);
        if (cartDetail == null) {
            return null;
        }
        cartDetail.setQuantity(quantity);
        return cartRepository.save(cartDetail);
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

    public int calculateTotalAmount(List<CartDetail> cartDetails) {
        List<CartDetail> cartDetails1 = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            cartDetails1.add(cartRepository.findById(cartDetail.getId()).orElse(null));
        }
        int totalAmount = 0;
        for (CartDetail cartDetail : cartDetails1) {
            totalAmount += cartDetail.getProduct().getPrice() * cartDetail.getQuantity();
        }
        return totalAmount;
    }

    public CartDetail getCartByCustomerIdAndProductIdAndPhoneCategoryId(int customerId, int productId, int phoneCategoryId) {
        CartDetail cartDetail;
        cartDetail = cartRepository.findByCustomer_IdAndProduct_IdAndPhoneCategory_Id(customerId, productId, phoneCategoryId);
        return cartDetail;
    }

    public List<CartDetail> getListCart(List<Integer> cartDetailIds) {
        return cartRepository.findAllById(cartDetailIds);
    }
}
