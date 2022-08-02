package com.eshop.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dto.OrderDTO;
import com.eshop.service.impl.VNPayService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class VNPayController {
	@Autowired
	VNPayService payService;
	@PostMapping("/vnpay")
    public String pay(@RequestBody OrderDTO payModel, HttpServletRequest request) throws IOException{
        try {
            return payService.payWithVNPAY(payModel, request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
