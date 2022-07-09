package com.eshop.service;

import java.util.List;

import com.eshop.dto.CategoryDTO;

public interface ICategoryService {
	List<CategoryDTO> findAll();
	CategoryDTO save(CategoryDTO category);
	CategoryDTO update(CategoryDTO category);
	String delete(long id);
}
