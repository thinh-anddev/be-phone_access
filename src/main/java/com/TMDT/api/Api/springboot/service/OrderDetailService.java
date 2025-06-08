package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.OrderDetailDTO;
import com.TMDT.api.Api.springboot.mapper.OrderDetailMapper;
import com.TMDT.api.Api.springboot.models.OrderDetail;
import com.TMDT.api.Api.springboot.repositories.CartDetailRepository;
import com.TMDT.api.Api.springboot.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public List<OrderDetailDTO> addAll(List<OrderDetailDTO> orderDetailDTOs) {
        List<OrderDetail> orderDetails = orderDetailMapper.toListEntity(orderDetailDTOs);
        return orderDetailMapper.toListDto(orderDetailRepository.saveAll(orderDetails));
    }
}
