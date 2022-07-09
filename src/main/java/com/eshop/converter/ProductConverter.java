package com.eshop.converter;

import org.springframework.stereotype.Component;

import com.eshop.dto.ProductDTO;
import com.eshop.entity.ProductEntity;

@Component
public class ProductConverter {
	public ProductEntity toEntity(ProductDTO dto) {
		ProductEntity entity = new ProductEntity();
		entity.setId(dto.getId());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedDate(dto.getUpdatedDate());
		entity.setStatus(dto.isStatus());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setShortDetails(dto.getShortDetails());
		entity.setShortDes(dto.getShortDes());
		entity.setImage(dto.getImage());
//		entity.setCategory(dto.getCategoryName());
		return entity;
	}
	
	public ProductDTO toDTO(ProductEntity entity) {
		ProductDTO dto = new ProductDTO();
		dto.setId(entity.getId());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setUpdatedDate(entity.getUpdatedDate());
		dto.setStatus(entity.isStatus());
		dto.setName(entity.getName());
		dto.setPrice(entity.getPrice());
		dto.setShortDetails(entity.getShortDetails());
		dto.setShortDes(entity.getShortDes());
		dto.setImage(entity.getImage());
		return dto;
	}
}
