package com.eshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dto.OrderDTO;
import com.eshop.service.impl.OrderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PaymentOrderAPI {
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/Payment")
	public OrderDTO payment(@RequestBody OrderDTO order) {
		return orderService.payment(order);
//		return order;
	}
	
	@GetMapping(value = "/Order")
	public List<OrderDTO> listOrder(){
		return orderService.findAll();
	}
	
	@DeleteMapping(value = "/Order")
	public String  deleteOrder(@RequestBody Long id) {
		return orderService.delete(id);
	}
	
	@PostMapping(value = "/Order")
	public OrderDTO findOnebyId(@RequestBody Long id) {
		return orderService.findOneById(id);
	}
	
	@PutMapping(value = "/OrderStatus")
	public OrderDTO changeStatus(@RequestBody Long id) {
		return orderService.changeStatus(id);
	}
	
}
