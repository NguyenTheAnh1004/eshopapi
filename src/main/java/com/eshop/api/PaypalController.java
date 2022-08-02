package com.eshop.api;

import java.io.IOException;

import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.eshop.dto.OrderDTO;

import com.eshop.service.impl.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PaypalController {
	@Autowired
	PaypalService service;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping(value = "/paypal")
	public String payment(@RequestBody OrderDTO order) {
		try {
			order.setPaymentInfo(order.getPaymentInfo()+" ,code: "+order.getCode());
			Payment payment = service.createPayment(order.getTotal().doubleValue(), "USD", "paypal", "sale", order.getPaymentInfo(),
					"http://localhost:8088/" + CANCEL_URL, "http://localhost:8088/" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
//					return link.getHref();
					service.Convertcurrency();
					return link.getHref();
//					return new ModelAndView("redirect:" + link.getHref());
					
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return "https://feeshopbasic.vercel.app/";   
//		return new ModelAndView("redirect:/");
	}

	@GetMapping(value = CANCEL_URL)
	public ModelAndView cancelPay() {
		return new ModelAndView("redirect:" + "https://feeshopbasic.vercel.app/");
	}

	@GetMapping(value = SUCCESS_URL)
	public ModelAndView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return new ModelAndView("redirect:" + "https://feeshopbasic.vercel.app/");
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return new ModelAndView("redirect:/");
	}

}
