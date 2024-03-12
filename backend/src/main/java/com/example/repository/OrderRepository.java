package com.example.repository;

import com.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    List<Order> findOrders
//   ByIds(List<Integer> ids);
//    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE id = ?1")
//    Order fuckAllJavaLoosers(Integer id);
}
