package com.eshop.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cloudinary.utils.ObjectUtils;
import com.eshop.conf.CloudinaryConfig;
import com.eshop.converter.ProductConverter;
import com.eshop.dto.ProductDTO;
import com.eshop.dto.UserDTO;
import com.eshop.entity.CategoryEntity;
import com.eshop.entity.ProductEntity;
import com.eshop.entity.RoleEntity;
import com.eshop.entity.SizeEntity;
import com.eshop.entity.UserEntity;
import com.eshop.repository.CategoryRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.repository.SizeRepository;
import com.eshop.service.IProductService;
import com.github.slugify.Slugify;

@Service
public class ProductService implements IProductService {

	@Value("${cloud.url}")
	private String cloudinaryURL;

	@Autowired
	ModelMapper modelMapper;

	private Slugify slg = new Slugify();

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SizeRepository sizeRepository;

	@Autowired
	private ProductConverter converter;

	@Autowired
	private CloudinaryConfig cloundinary;

	private Random rand = new Random();

	private final Path root = Paths.get("uploads");
//	private final Path root = Paths.get("src/main/resources/images");

	@Override
	public List<ProductDTO> findAll() {
		List<ProductEntity> listProductEntity = new ArrayList<ProductEntity>();
		listProductEntity = productRepository.findAll();
		List<ProductDTO> results = new ArrayList<ProductDTO>();
		for (ProductEntity items : listProductEntity) {
			modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
					.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
			ProductDTO productDTO = modelMapper.map(items, ProductDTO.class);
			items.getSizes().forEach(item -> {
				productDTO.getSizes().add(item.getName());
			});
			urlImage(productDTO);

			results.add(productDTO);
		}
		return results;
	}

	@Override
	public String delete(long id) throws IOException {
		// delete image in soure
//		ProductEntity productEntity = productRepository.findOneById(id);
//		try {
//			Files.delete(this.root.resolve(productEntity.getImage()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// delete image

		Optional<ProductEntity> users = productRepository.findById(id);
		if (users.isPresent()) {
			ProductEntity entity = productRepository.findOneById(id);
			// delete image on clound
			cloundinary.delete("heroyolo/" + FilenameUtils.removeExtension(entity.getImage().replaceAll(" ", "%20")),
					ObjectUtils.asMap("invalidate", true));
			// end delete imgae on cloud
			entity.getSizes().clear();
			productRepository.deleteById(id);
			return "deleted Product " + id + " successfully";
		}
		return "delete fail";
	}

	@Override
	public List<ProductDTO> findByName(String name) {
		List<ProductEntity> entity = new ArrayList<ProductEntity>();
		entity = productRepository.findByNameLike(name);
		List<ProductDTO> results = new ArrayList<ProductDTO>();
		for (ProductEntity items : entity) {
			modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
					.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
			ProductDTO dto = modelMapper.map(items, ProductDTO.class);
			items.getSizes().forEach(item -> {
				dto.getSizes().add(item.getName());
			});
			urlImage(dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public ProductDTO findOneByName(String name) {
		ProductEntity entity = new ProductEntity();
		modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
				.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
		ProductDTO dto = modelMapper.map(entity, ProductDTO.class);
		entity.getSizes().forEach(item -> {
			dto.getSizes().add(item.getName());
		});
		return dto;
	}

	@Override
	public ProductDTO findOneByCode(String code) {
		ProductEntity entity = productRepository.findOneByCode(code);
		modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
				.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
		ProductDTO dto = modelMapper.map(entity, ProductDTO.class);
		entity.getSizes().forEach(item -> {
			dto.getSizes().add(item.getName());
		});
		urlImage(dto);
		return dto;
	}

	@Override
	public ProductDTO Save(String name, BigDecimal price, String shortDes, String shortDetails, MultipartFile file,
			Integer quantity, Integer discount, Integer view, String categoryName, List<String> sizes)
			throws IOException {

		String code = slg.slugify(name);
		// upload image in resoure
//		Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
//				StandardCopyOption.REPLACE_EXISTING);
//		System.out.print(FilenameUtils.removeExtension(file.getOriginalFilename()));

		// upload on cloundinary
		cloundinary.upload(file.getBytes(),
				ObjectUtils.asMap("public_id", FilenameUtils.removeExtension(file.getOriginalFilename()),
						"unique_filename", "false", "folder", "heroyolo/"));

		// end upload

		ProductDTO product = new ProductDTO(name, price, shortDes, shortDetails, file.getOriginalFilename(), quantity,
				discount, code, view, categoryName, sizes);

		ProductEntity productEntity = new ProductEntity();
		modelMapper.typeMap(ProductDTO.class, ProductEntity.class)
				.addMappings(mapper -> mapper.skip(ProductEntity::setSizes));
		productEntity = modelMapper.map(product, ProductEntity.class);

		/* size */
		for (String items : sizes) {
			SizeEntity size = sizeRepository.findOneByName(items);
			productEntity.getSizes().add(size);
		}

		// category
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity = categoryRepository.findOneByName(product.getCategoryName());
		productEntity.setCategory(categoryEntity);

		productEntity = productRepository.save(productEntity);
		modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
				.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
		ProductDTO dto = modelMapper.map(productEntity, ProductDTO.class);
		productEntity.getSizes().forEach(items -> {
			dto.getSizes().add(items.getName());
		});
		urlImage(dto);

		return dto;

	}

	@Override
	public ProductDTO update(Long id, String name, BigDecimal price, String shortDes, String shortDetails,
			MultipartFile file, Integer quantity, Integer discount, Integer view, String categoryName,
			List<String> sizes) throws IOException {

		String code = slg.slugify(name);
		ProductDTO product = new ProductDTO(name, price, shortDes, shortDetails, file.getOriginalFilename(), quantity,
				discount, code, view, categoryName, sizes);

		product.setId(id);
		ProductEntity oldProduct = productRepository.findOneById(product.getId());
		product.setCreatedDate(oldProduct.getCreatedDate());
		product.setCreatedBy(oldProduct.getCreatedBy());

		// update image
//		try {
//			Files.delete(this.root.resolve(oldProduct.getImage()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if (file.getOriginalFilename() != "") {
//			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
//					StandardCopyOption.REPLACE_EXISTING);
//		} else {
//			product.setImage(oldProduct.getImage());
//		}
		cloundinary.delete("heroyolo/" + FilenameUtils.removeExtension(oldProduct.getImage().replaceAll(" ", "%20")),
				ObjectUtils.asMap("invalidate", true));

		cloundinary.upload(file.getBytes(),
				ObjectUtils.asMap("public_id", FilenameUtils.removeExtension(file.getOriginalFilename()),
						"unique_filename", "false", "folder", "heroyolo/"));

		// end update image
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.typeMap(ProductDTO.class, ProductEntity.class)
				.addMappings(mapper -> mapper.skip(ProductEntity::setSizes));
		oldProduct = modelMapper.map(product, ProductEntity.class);

		/* size */
		oldProduct.getSizes().clear();
		for (String items : sizes) {
			SizeEntity size = sizeRepository.findOneByName(items);
			oldProduct.getSizes().add(size);
		}

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity = categoryRepository.findOneByName(product.getCategoryName());
		oldProduct.setCategory(categoryEntity);
		oldProduct = productRepository.save(oldProduct);
		modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
				.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
		ProductDTO dto = modelMapper.map(oldProduct, ProductDTO.class);
		oldProduct.getSizes().forEach(items -> {
			dto.getSizes().add(items.getName());
		});
		urlImage(dto);

		return dto;
	}

	public void urlImage(ProductDTO dto) {
		// link resource
//		String fileDownloadUri = (ServletUriComponentsBuilder.fromCurrentContextPath().path("/Images/Product/")
//				.path(dto.getImage()).toUriString());
//		dto.setImage(fileDownloadUri);

		String fileDownloadUri = cloudinaryURL + dto.getImage();
		String linkCloundinary = fileDownloadUri.replaceAll(" ", "%20");
		dto.setImage(linkCloundinary);

	}

	@Override
	public ProductDTO changeStatus(Long id) {
		ProductEntity oldEntity = productRepository.findOneById(id);
		oldEntity.setStatus(!oldEntity.isStatus());
		oldEntity = productRepository.save(oldEntity);
		modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
				.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
		ProductDTO dto = modelMapper.map(oldEntity, ProductDTO.class);
		oldEntity.getSizes().forEach(items -> {
			dto.getSizes().add(items.getName());
		});
		urlImage(dto);

		return dto;

	}

	@Override
	public List<ProductDTO> findByCategory(String catecode) {
		CategoryEntity category = categoryRepository.findOneByCode(catecode);
		List<ProductEntity> entity = productRepository.findByCategory(category);
		List<ProductDTO> results = new ArrayList<ProductDTO>();
		for (ProductEntity items : entity) {
			modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
					.addMappings(mapper -> mapper.skip(ProductDTO::setSizes));
			ProductDTO dto = modelMapper.map(items, ProductDTO.class);
			items.getSizes().forEach(item -> {
				dto.getSizes().add(item.getName());
			});
			urlImage(dto);
			results.add(dto);
		}
		return results;
	}

}
