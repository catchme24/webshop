package com.example.repository;

import com.example.entity.DeliveryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DeliveryListRepository extends JpaRepository<DeliveryList, Integer> {

}
