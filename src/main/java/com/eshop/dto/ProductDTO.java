package com.eshop.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.eshop.entity.CategoryEntity;

public class ProductDTO extends AbstractDTO {

	private String name;
	private BigDecimal price;
	private String shortDes;
	private String shortDetails;
	private String image;
	private Integer quantity;
	private Integer discount;
	private String code;
	private Integer view;
	private String categoryName;
	private List<String> sizes = new ArrayList<String>();

	public ProductDTO() {
		super();
	}
	
	

	public ProductDTO(String name, BigDecimal price, String shortDes, String shortDetails, String image,
			Integer quantity, Integer discount, String code, Integer view, String categoryName, List<String> sizes) {
		super();
		this.name = name;
		this.price = price;
		this.shortDes = shortDes;
		this.shortDetails = shortDetails;
		this.image = image;
		this.quantity = quantity;
		this.discount = discount;
		this.code = code;
		this.view = view;
		this.categoryName = categoryName;
		this.sizes = sizes;
	}



	public ProductDTO(String name, BigDecimal price, String shortDes, String shortDetails, String image,
			Integer quantity, Integer discount, String code, Integer view, String categoryName) {
		super();
		this.name = name;
		this.price = price;
		this.shortDes = shortDes;
		this.shortDetails = shortDetails;
		this.image = image;
		this.quantity = quantity;
		this.discount = discount;
		this.code = code;
		this.view = view;
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getShortDes() {
		return shortDes;
	}

	public void setShortDes(String shortDes) {
		this.shortDes = shortDes;
	}

	public String getShortDetails() {
		return shortDetails;
	}

	public void setShortDetails(String shortDetails) {
		this.shortDetails = shortDetails;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}
	
}
