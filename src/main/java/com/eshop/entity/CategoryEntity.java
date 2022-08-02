   package com.eshop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "sort_order")
	private Integer sortOrder;
	
	@Column(name = "code", unique = true)
	private String code;

	@OneToMany(mappedBy = "category")
	private List<ProductEntity> products = new ArrayList<ProductEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
