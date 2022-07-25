package com.eshop.service;

import java.util.List;

import com.eshop.dto.OrderDTO;

public interface IOrderService {
	OrderDTO payment(OrderDTO order);
	String delete(Long id);
	List<OrderDTO> findAll();
	OrderDTO findOneById(Long id);
	OrderDTO update(OrderDTO order);
	OrderDTO changeStatus(Long id);
}
