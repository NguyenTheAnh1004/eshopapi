package com.eshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findOneByName(String role);
	
	Optional<RoleEntity> findByName(String name);

}
