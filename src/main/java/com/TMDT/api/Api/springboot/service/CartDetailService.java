package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.mapper.CartDetailMapper;
import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.repositories.CartDetailRepository;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.repositories.PhoneCategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartDetailMapper cartDetailMapper;

    public List<CartDetailDTO> getCartByCustomerId(int customerId) {
        return cartDetailMapper.toDtoList(cartDetailRepository.findByCustomer_Id(customerId));
    }


    public CartDetail get(int id) {
        return clearProperty(Objects.requireNonNull(cartDetailRepository.findById(id).orElse(null)));
    }

    public CartDetail add(CartDetailDTO cartDetailDTO) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setProduct(productRepository.findById(cartDetailDTO.getProductId()).orElse(null));
        cartDetail.setPhoneCategory(phoneCategoryRepository.findById(cartDetailDTO.getPhoneCategoryId()).orElse(null));
        cartDetail.setQuantity(cartDetailDTO.getQuantity());
        cartDetail.setCustomer(customerRepository.findById(cartDetailDTO.getCustomerId()).orElse(null));
        cartDetail.setStatus(1);
        return cartDetailRepository.save(cartDetail);
    }

    public void delete(int id) {
        cartDetailRepository.deleteById(id);
    }

    public void deleteAll(List<CartDetail> cartDetails) {
        cartDetailRepository.deleteAll(cartDetails);
    }

    public void deleteAllByCustomerId(int customerId) {
        cartDetailRepository.deleteCartDetailByCustomer_Id(customerId);
    }

    public CartDetail update(int id, int quantity) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
        if (cartDetail == null) {
            return null;
        }
        cartDetail.setQuantity(quantity);
        return cartDetailRepository.save(cartDetail);
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
            cartDetails1.add(cartDetailRepository.findById(cartDetail.getId()).orElse(null));
        }
        int totalAmount = 0;
        for (CartDetail cartDetail : cartDetails1) {
            if (cartDetail != null) {
                totalAmount += (int) (cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
            }

        }
        return totalAmount;
    }

    public CartDetailDTO getCartByCustomerIdAndProductIdAndPhoneCategoryId(int customerId, int productId, int phoneCategoryId) {

        return cartDetailMapper.toDto(cartDetailRepository.findByCustomer_IdAndProduct_IdAndPhoneCategory_Id(customerId, productId, phoneCategoryId));
    }

    public List<CartDetail> getListCart(List<Integer> cartDetailIds) {
        return cartDetailRepository.findAllById(cartDetailIds);
    }
}
