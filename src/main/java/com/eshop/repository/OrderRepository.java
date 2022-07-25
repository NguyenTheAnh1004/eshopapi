package com.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	OrderEntity findOneById(Long id);

}
