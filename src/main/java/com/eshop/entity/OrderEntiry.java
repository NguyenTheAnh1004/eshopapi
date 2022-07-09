package com.eshop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntiry extends BaseEntity{
	
	@Column(name = "code")
	private String code;

	@Column(name = "total", precision = 13, scale = 2, nullable = false)
	private BigDecimal total;

	@Column(name = "customer_phone")
	private String customerPhone;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_address")
	private String customerAddress;

	@Column(name = "customer_email")
	private String customerEmail;  
	
	@Column(name = "payment")
	private String payment;  
	
	@Lob
	@Column(name = "payment_info")
	private String paymentInfo;  
	
	@Column(name = "message")
	private String message;  
	
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProductOrderEntity> getProductorder() {
		return productorder;
	}

	public void setProductorder(List<ProductOrderEntity> productorder) {
		this.productorder = productorder;
	}

	@OneToMany(mappedBy="order")
    private List<ProductOrderEntity> productorder = new ArrayList<ProductOrderEntity>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<ProductOrderEntity> getProductOrder() {
		return productorder;
	}

	public void setProductOrder(List<ProductOrderEntity> productorder) {
		this.productorder= productorder;
	}
	
}
