package com.gawari._himanshu.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gawari._himanshu.microservices.currencyexchangeservice.bean.ExchangeValue;
import com.gawari._himanshu.microservices.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);

		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		logger.info("{}", exchangeValue);
		return exchangeValue;
	}

	@GetMapping("/currency-exchange/to/{to}")
	public ExchangeValue retrieveExchangeValueTo(@PathVariable String to) {

		ExchangeValue exchangeValue = repository.findByTo(to);

		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		return exchangeValue;
	}

	@GetMapping("/currency-exchange/from/{from}")
	public ExchangeValue retrieveExchangeValueFrom(@PathVariable String from) {

		ExchangeValue exchangeValue = repository.findByFrom(from);

		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		return exchangeValue;
	}

	// Hardcoded exchange value
	@GetMapping(path = "/currency-exchange-harcoded/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValueHardcoded(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65)); // exchangeValue.setPort(8000);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}

}
