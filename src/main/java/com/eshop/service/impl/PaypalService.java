package com.eshop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
@Service
public class PaypalService {
	
	@Autowired
	private APIContext apiContext;
	
	
	public Payment createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
	
	public void Convertcurrency() {
		 try {
	            //Public API:
	            //https://www.metaweather.com/api/location/search/?query=<CITY>
	            //https://www.metaweather.com/api/location/44418/

	            URL url = new URL("https://api.exchangerate.host/latest?base=USD");

	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.connect();

	            //Check if connect is made
	            int responseCode = conn.getResponseCode();

	            // 200 OK
	            if (responseCode != 200) {
	                throw new RuntimeException("HttpResponseCode: " + responseCode);
	            } else {

	                StringBuilder informationString = new StringBuilder();
	                Scanner scanner = new Scanner(url.openStream());

	                while (scanner.hasNext()) {
	                    informationString.append(scanner.nextLine());
	                }
	                //Close the scanner
	                scanner.close();

	                System.out.println(informationString);


	                //JSON simple library Setup with Maven is used to convert strings to JSON
	                JSONParser parse = new JSONParser();
	                JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));
//	                dataObject.date;
//	                JSONArray array = new JSONArray();
//	                array.add(dataObject);
//	                System.out.println(array.get(0));
	                JSONObject rates = (JSONObject)dataObject.get("rates");
	                System.out.println(dataObject.get("rates"));
	                System.out.println(rates.get("USD"));

	                //Get the first JSON object in the JSON array
//	                System.out.println(dataObject.get(0));

//	                JSONObject countryData = (JSONObject) dataObject.get(0);

//	                System.out.println(countryData.get("woeid"));

	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
