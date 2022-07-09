package com.eshop.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProductOrder")
public class ProductOrderEntity extends BaseEntity {

	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "total", precision = 13, scale = 2, nullable = false)
	private BigDecimal total;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private OrderEntiry order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderEntiry getOrder() {
		return order;
	}

	public void setOrder(OrderEntiry order) {
		this.order = order;
	}
}
