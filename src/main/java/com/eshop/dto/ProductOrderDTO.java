package com.eshop.dto;

import java.math.BigDecimal;

import com.eshop.entity.OrderEntity;
import com.eshop.entity.ProductEntity;

public class ProductOrderDTO extends AbstractDTO {
	private Integer quantity;
	private String size;
	private BigDecimal total;
	private Long orderId;
	private Long productId;
	private String image;
	private String name;
	public ProductOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
