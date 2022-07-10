package com.eshop.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eshop.dto.ProductDTO;
import com.eshop.service.impl.ProductService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductAPI {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/Product")
	public List<ProductDTO> showProduct() {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
//		System.out.print(fileDownloadUri);
		return productService.findAll();
	}

//	@PostMapping(value = "/Product")
//	public ProductDTO createProduct(@RequestBody ProductDTO product) {
//		return productService.save(product);
////		return product;
//	}

	@PutMapping(value = "/Product")
	public ProductDTO updateProduct(@RequestParam("id") Long id, @RequestParam("name") String name,
			@RequestParam("price") BigDecimal price, @RequestParam("shortDes") String shortDes,
			@RequestParam("shortDetails") String shortDetails, @RequestParam("file") MultipartFile file,
			@RequestParam("quantity") Integer quantity, @RequestParam("discount") Integer discount,
			@RequestParam("view") Integer view, @RequestParam("categoryName") String categoryName,
			@RequestParam("size") List<String> size) throws IOException {

		return productService.update(id, name, price, shortDes, shortDetails, file, quantity, discount, view,
				categoryName, size);
	}

	@DeleteMapping(value = "/Product")
	public String deleteProduct(@RequestBody long id) throws IOException {
		return productService.delete(id);
	}

	@GetMapping(value = "/Product/{code}")
	public ProductDTO findProductByCode(@PathVariable("code") String code) {
		return productService.findOneByCode(code);
	}

	@PostMapping(value = "/Product")
	public ProductDTO createProduct(@RequestParam("name") String name, @RequestParam("price") BigDecimal price,
			@RequestParam("shortDes") String shortDes, @RequestParam("shortDetails") String shortDetails,
			@RequestParam("file") MultipartFile file, @RequestParam("quantity") Integer quantity,
			@RequestParam("discount") Integer discount, @RequestParam("view") Integer view,
			@RequestParam("categoryName") String categoryName, @RequestParam("size") List<String> size)
			throws IOException {

		return productService.Save(name, price, shortDes, shortDetails, file, quantity, discount, view, categoryName, size);
	}

	@GetMapping(value = "/Images/Product/{photo}")
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
		if (!photo.equals("") || photo != null) {
			try {
				Path filename = Paths.get("uploads", photo);
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping(value = "/ProductStatus")
	public ProductDTO changeStatus(@RequestBody Long id) {

		return productService.changeStatus(id);
	}

}
