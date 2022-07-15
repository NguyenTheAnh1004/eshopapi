package com.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.dto.CategoryDTO;
import com.eshop.entity.CategoryEntity;
import com.eshop.repository.CategoryRepository;
import com.eshop.service.ICategoryService;
import com.github.slugify.Slugify;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CategoryRepository categoryRepository;
	
	private Slugify slg = new Slugify();

	public List<CategoryDTO> findAll() {
		List<CategoryEntity> categoryEntity = new ArrayList<CategoryEntity>();
		categoryEntity = categoryRepository.findAll();
		List<CategoryDTO> results = new ArrayList<CategoryDTO>();
		for (CategoryEntity items : categoryEntity) {
			CategoryDTO dto = new CategoryDTO();
			dto = modelMapper.map(items, CategoryDTO.class);
			results.add(dto);
		}
		return results;
	}

	@Override
	public CategoryDTO save(CategoryDTO dto) {
		dto.setCode(slg.slugify(dto.getName()));
		CategoryEntity entity = new CategoryEntity();
		entity = modelMapper.map(dto, CategoryEntity.class);
		entity = categoryRepository.save(entity);
		return modelMapper.map(entity, CategoryDTO.class);
	}

	@Override
	public CategoryDTO update(CategoryDTO dto) {
		dto.setCode(slg.slugify(dto.getName()));
		CategoryEntity oldEntity = categoryRepository.findOneById(dto.getId());
		dto.setCreatedBy(oldEntity.getCreatedBy());
		dto.setCreatedDate(oldEntity.getCreatedDate());
		oldEntity = modelMapper.map(dto, CategoryEntity.class);
		oldEntity = categoryRepository.save(oldEntity);
		return modelMapper.map(oldEntity, CategoryDTO.class);
	}

	@Override
	public String delete(long id) {
		CategoryEntity entity = categoryRepository.findOneById(id);
		String results = "remove " + entity.getName();
		categoryRepository.deleteById(id);
		return results;
	}
}
