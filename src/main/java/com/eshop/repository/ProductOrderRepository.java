package com.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.entity.ProductOrderEntity;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Long>{

}
