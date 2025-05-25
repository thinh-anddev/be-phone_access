package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    List<CartDetail> findByCustomer_Id(int customerId);

    void deleteCartDetailByCustomer_Id(int customerId);

    CartDetail findByCustomer_IdAndProduct_IdAndPhoneCategory_Id(int customerId, int productId, int phoneCategoryId);

    CartDetail findByCustomer_IdAndProduct_Id(int customerId, int productId);

    @Query("SELECT cd FROM CartDetail cd JOIN FETCH cd.product p LEFT JOIN FETCH p.images WHERE cd.customer.id = :customerId")
    List<CartDetail> findByCartIdWithProductImages(@Param("customerId") Long customerId);

}
