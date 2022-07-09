package com.eshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dto.ProductDTO;
import com.eshop.service.impl.ProductService;

@RestController
public class SearchAPI {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/Search")
	public List<ProductDTO> searchProduct(@RequestParam String name) {
		return productService.findByName(name);
	}
}
