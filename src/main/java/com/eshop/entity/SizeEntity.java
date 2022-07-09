package com.eshop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="size")
public class SizeEntity extends BaseEntity {
	
	@Column(name="name")
	String name;
	
	@ManyToMany(mappedBy = "sizes")
	private List<ProductEntity> products = new ArrayList<ProductEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
}
