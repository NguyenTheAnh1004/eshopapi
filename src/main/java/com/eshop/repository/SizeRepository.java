package com.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.entity.SizeEntity;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

	SizeEntity findOneByName(String items);

}
