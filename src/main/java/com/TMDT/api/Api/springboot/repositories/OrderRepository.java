package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT SUM(o.total) FROM Order o WHERE YEAR(o.createDate) = :year AND MONTH(o.createDate) = :month")
    Integer getTotalRevenueForMonthAndYear(@Param("year") int year, @Param("month") int month);

    @Query("SELECT DISTINCT YEAR(o.createDate) FROM Order o ORDER BY YEAR(o.createDate) DESC")
    List<Integer> getAllYear();


    @Query("SELECT SUM(od.price * od.quantity) FROM OrderDetail od " +
            "JOIN od.order o " +
            "JOIN od.product p " +
            "JOIN p.category c " +
            "WHERE c.id = :categoryId " +
            "AND YEAR(o.createDate) = :year " +
            "AND MONTH(o.createDate) = :month")
    Integer getTotalRevenueForCategoryInMonthAndYear(@Param("categoryId") int categoryId,
                                                     @Param("year") int year,
                                                     @Param("month") int month);

    List<Order> findByCustomer_Id(int customerId);
}
