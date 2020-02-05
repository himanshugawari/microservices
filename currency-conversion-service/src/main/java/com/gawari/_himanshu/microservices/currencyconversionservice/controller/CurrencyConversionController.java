package com.gawari._himanshu.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gawari._himanshu.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.gawari._himanshu.microservices.currencyconversionservice.feignproxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	// Hardcoded
	@GetMapping(path = "/currency-converter-hardcoded/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyHardcoded(@PathVariable String from, @PathVariable String to,
			@PathVariable String quantity) {
		return new CurrencyConversion(1L, from, to, BigDecimal.ONE, BigDecimal.valueOf(Integer.parseInt(quantity)),
				BigDecimal.valueOf(Integer.parseInt(quantity)), 0);
	}

	@GetMapping(path = "/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable String quantity) {

		// Feign - Problem 1 (Invoking other services)
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		CurrencyConversion response = responseEntity.getBody();
		BigDecimal quantity1 = BigDecimal.valueOf(Integer.parseInt(quantity));
		BigDecimal conversionMultiple = response.getConversionMultiple();
		BigDecimal totalCalculation = quantity1.multiply(conversionMultiple);
		return new CurrencyConversion(response.getId(), from, to, conversionMultiple, quantity1, totalCalculation,
				response.getPort());
	}

	// Using Feign
	@GetMapping(path = "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable String quantity) {

		CurrencyConversion response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

		BigDecimal quantity1 = BigDecimal.valueOf(Integer.parseInt(quantity));
		BigDecimal conversionMultiple = response.getConversionMultiple();
		BigDecimal totalCalculation = quantity1.multiply(conversionMultiple);

		return new CurrencyConversion(response.getId(), from, to, conversionMultiple, quantity1, totalCalculation,
				response.getPort());
	}

}
