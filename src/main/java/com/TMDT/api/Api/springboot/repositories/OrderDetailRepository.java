package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
