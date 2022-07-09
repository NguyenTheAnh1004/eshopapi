package com.eshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eshop.dto.ProductDTO;
import com.eshop.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByName(String name);
	
//	@Query(value ="SELECT p FROM ProductEntity p WHERE c.name = :categoryName")
//	List<ProductEntity> searchByName(String name);

	ProductEntity findOneById(long id);

	ProductEntity findOneByName(String name);

	ProductEntity findOneByCode(String code);

	List<ProductEntity> findByNameContaining(String name);

	@Query("SELECT p FROM ProductEntity p WHERE p.name LIKE %:name%")
	List<ProductEntity> findByNameLike(@Param("name") String name);

}
