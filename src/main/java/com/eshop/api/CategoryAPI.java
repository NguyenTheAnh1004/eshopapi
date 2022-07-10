package com.eshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dto.CategoryDTO;
import com.eshop.service.impl.CategoryService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategoryAPI {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/Category")
	List<CategoryDTO> showCategory(){
		return categoryService.findAll();
	}
	
	@PostMapping(value = "/Category")
	CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO){
		return categoryService.save(categoryDTO);
	}
	
	@PutMapping(value = "/Category")
	CategoryDTO updateCategory(@RequestBody CategoryDTO categoryDTO){
		return categoryService.update(categoryDTO);
	}
	
	@DeleteMapping(value = "/Category")
	String deleteCategory(@RequestBody long id){
		return categoryService.delete(id);
	}
}
