package com.eshop.dto;

public class CategoryDTO extends AbstractDTO {
	
	private String name;
	private Integer sortOrder;
	
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
