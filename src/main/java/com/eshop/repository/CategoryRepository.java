package com.eshop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eshop.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
//	
//	@Query(value ="SELECT c FROM CategoryEntity c WHERE c.name = :categoryName")
//	List<CategoryEntity> findByName( @Param("categoryName") String categoryName);
	
	List<CategoryEntity> findByName(String categoryName);

	CategoryEntity findOneById(long id);

	CategoryEntity findOneByName(String categoryName);

	CategoryEntity findOneByCode(String catecode);

}
