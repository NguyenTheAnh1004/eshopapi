package com.eshop.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.eshop.entity.BaseEntity;

public class OrderDTO extends AbstractDTO {
	private String code;
	private BigDecimal total;
	private Long customerId;
	private String customerPhone;
	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String payment;
	private String paymentInfo;
	private String message;
	private List<ProductOrderDTO> productorder = new ArrayList<ProductOrderDTO>();

	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDTO(String code, BigDecimal total, Long customerId, String customerPhone, String customerName,
			String customerAddress, String customerEmail, String payment, String paymentInfo, String message,
			List<ProductOrderDTO> productorder) {
		super();
		this.code = code;
		this.total = total;
		this.customerId = customerId;
		this.customerPhone = customerPhone;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.payment = payment;
		this.paymentInfo = paymentInfo;
		this.message = message;
		this.productorder = productorder;
	}

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public List<ProductOrderDTO> getProductorder() {
		return productorder;
	}

	public void setProductorder(List<ProductOrderDTO> productorder) {
		this.productorder = productorder;
	}

}
