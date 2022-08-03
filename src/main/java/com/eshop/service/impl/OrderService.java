package com.eshop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.cloudinary.utils.ObjectUtils;
import com.eshop.dto.OrderDTO;
import com.eshop.dto.ProductDTO;
import com.eshop.dto.ProductOrderDTO;
import com.eshop.entity.OrderEntity;
import com.eshop.entity.ProductEntity;
import com.eshop.entity.ProductOrderEntity;
import com.eshop.repository.OrderRepository;
import com.eshop.repository.ProductOrderRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.service.IOrderService;

@Service
public class OrderService implements IOrderService {

	@Value("${cloud.url}")
	private String cloudinaryURL;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductOrderRepository productOrderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	private Random rand = new Random();

	@Override
	public OrderDTO payment(OrderDTO orderDTO) {
		orderDTO.setStatus(false);
//		int int_random = rand.nextInt(10000); 
//		orderDTO.setCode(String.valueOf(int_random));
		orderDTO.setPaymentInfo(orderDTO.getPaymentInfo()+" ,code: "+orderDTO.getCode());
		OrderEntity orderEntity = new OrderEntity();
		modelMapper.typeMap(OrderDTO.class, OrderEntity.class)
				.addMappings(mapper -> mapper.skip(OrderEntity::setProductorder));
		orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
		BigDecimal total = new BigDecimal(0);
		List<ProductOrderEntity> productOrders = new ArrayList<ProductOrderEntity>();
		int temp = 0;
		for (ProductOrderDTO items : orderDTO.getProductorder()) {
			ProductOrderEntity productOrder = new ProductOrderEntity();
			productOrder.setProduct(productRepository.findOneById(items.getProductId()));
			items.setImage(productOrder.getProduct().getImage());
			productOrder.setQuantity(items.getQuantity());
			productOrder.setOrder(orderEntity);
			productOrder.setSize(items.getSize());
			productOrder.setTotal(
					productOrder.getProduct().getPrice()
					.subtract((productOrder.getProduct().getPrice())
					.divide((BigDecimal.valueOf(100)))
					.multiply(BigDecimal.valueOf(productOrder.getProduct().getDiscount())))
					.multiply(BigDecimal.valueOf(productOrder.getQuantity())));
			total = total.add(productOrder.getTotal());
			productOrders.add(productOrder);
			temp++;
		}
		orderEntity.setTotal(total);
		orderEntity = orderRepository.save(orderEntity);
		OrderDTO newOrderDTO = new OrderDTO();
		List<String> listImage = new ArrayList<String>();
		List<String> listName = new ArrayList<String>();
		for (ProductOrderEntity items : productOrders) {
			items.setOrder(orderEntity);
			productOrderRepository.save(items);
			orderEntity.getProductorder().add(items);
			listImage.add(items.getProduct().getImage());
			listName.add(items.getProduct().getName());
		}
		newOrderDTO = modelMapper.map(orderEntity, OrderDTO.class);
//		urlImage(newOrderDTO);
		int indexImage = 0;
		for(ProductOrderDTO image : newOrderDTO.getProductorder()) {
			image.setImage((cloudinaryURL + listImage.get(indexImage)).replaceAll(" ", "%20"));
			image.setImage(listImage.get(indexImage));
			indexImage++;
		}
		return newOrderDTO;
	}

	@Override
	public String delete(Long id) {
		Optional<OrderEntity> order = orderRepository.findById(id);
		if (order.isPresent()) {
			OrderEntity entity = orderRepository.findOneById(id);
			// end delete imgae on cloud
			entity.getProductorder().clear();
			orderRepository.deleteById(id);
			return "deleted Product " + id + " successfully";
		}
		return "delete fail";
	}

	@Override
	public List<OrderDTO> findAll() {
		List<OrderEntity> listOrderEntity = orderRepository.findAll();
		List<OrderDTO> listOrderDTO = new ArrayList<OrderDTO>();
		for(OrderEntity entity : listOrderEntity) {
			OrderDTO dto = new OrderDTO();
//			ProductEntity entity = productRepository.findOneById
			dto = modelMapper.map(entity , OrderDTO.class);
			int index = 0;
			List<String> listImage = new ArrayList<String>();
			List<String> listName = new ArrayList<String>();
			for(ProductOrderEntity pOEntity : entity.getProductorder()) {
				listImage.add(pOEntity.getProduct().getImage());
				listName.add(pOEntity.getProduct().getName());
			}
			int indexImage = 0;
			for(ProductOrderDTO PDto : dto.getProductorder()) {
				PDto.setImage((cloudinaryURL + listImage.get(indexImage)).replaceAll(" ", "%20"));
				PDto.setName(listName.get(indexImage));
				indexImage++;
			}
			listOrderDTO.add(dto);
		}
		return listOrderDTO;
	}

	@Override
	public OrderDTO findOneById(Long id) {
		OrderEntity entity = new OrderEntity();
		entity = orderRepository.findOneById(id);
		OrderDTO dto = modelMapper.map(entity, OrderDTO.class);
		List<String> listImage = new ArrayList<String>();
		List<String> listName = new ArrayList<String>();
		for(ProductOrderEntity pOEntity : entity.getProductorder()) {
			listImage.add(pOEntity.getProduct().getImage());
			listName.add(pOEntity.getProduct().getName());
		}
		int indexImage = 0;
		for(ProductOrderDTO image : dto.getProductorder()) {
			image.setImage((cloudinaryURL + listImage.get(indexImage)).replaceAll(" ", "%20"));
			image.setName(listName.get(indexImage));
			indexImage++;
		}
		return dto;
	}

	@Override
	public OrderDTO update(OrderDTO order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO changeStatus(Long id) {
		OrderEntity entity = orderRepository.findOneById(id);
		
		// change quantity
		if(!entity.isStatus()) {
			for(ProductOrderEntity productOrder : entity.getProductorder()) {
				ProductEntity product = productRepository.findOneById(productOrder.getProduct().getId());
				product.setQuantity(product.getQuantity() - productOrder.getQuantity());
				productRepository.save(product);
			}
		} else {
			for(ProductOrderEntity productOrder : entity.getProductorder()) {
				ProductEntity product = productRepository.findOneById(productOrder.getProduct().getId());
				product.setQuantity(product.getQuantity() + productOrder.getQuantity());
				productRepository.save(product);
			}
		}
		//
		
		// change status
		entity.setStatus(!entity.isStatus());
		//
		
		entity = orderRepository.save(entity);
		OrderDTO dto = modelMapper.map(entity, OrderDTO.class);
		List<String> listImage = new ArrayList<String>();
		List<String> listName = new ArrayList<String>();
		for(ProductOrderEntity pOEntity : entity.getProductorder()) {
			listImage.add(pOEntity.getProduct().getImage());
			listName.add(pOEntity.getProduct().getName());
		}
		int indexImage = 0;
		for(ProductOrderDTO image : dto.getProductorder()) {
			image.setImage((cloudinaryURL + listImage.get(indexImage)).replaceAll(" ", "%20"));
			image.setName(listName.get(indexImage));
			indexImage++;
		}
		return dto;
	}

//	public void urlImage(OrderDTO dto, ProductOrderEntity entity) {
//		for(ProductOrderEntity productOrder : entity.getProduct() {
//			String fileDownloadUri = cloudinaryURL + productOrder.getImage();
//			String linkCloundinary = fileDownloadUri.replaceAll(" ", "%20");
//			productOrder.setImage(linkCloundinary);
//		}

//	}

}
