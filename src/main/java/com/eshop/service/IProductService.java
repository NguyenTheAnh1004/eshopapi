package com.eshop.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eshop.dto.ProductDTO;

public interface IProductService {
	List<ProductDTO> findAll();

//	ProductDTO save(ProductDTO product);

//	ProductDTO update(ProductDTO product);

	String delete(long ids) throws IOException;

	List<ProductDTO> findByName(String name);

	ProductDTO findOneByName(String name);

	ProductDTO findOneByCode(String code);
	
	List<ProductDTO> findByCategory(String cate);

	ProductDTO Save(String name, BigDecimal price, String shortDes, String shortDetails,
			MultipartFile file, Integer quantity, Integer discount, Integer view, String categoryName, List<String> size) throws IOException;
	
	ProductDTO update(Long id, String name, BigDecimal price, String shortDes, String shortDetails,
			MultipartFile file, Integer quantity, Integer discount, Integer view, String categoryName, List<String> size) throws IOException;
	
	ProductDTO changeStatus(Long id);
}
