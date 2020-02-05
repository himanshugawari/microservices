package com.gawari._himanshu.microservices.currencyconversionservice.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gawari._himanshu.microservices.currencyconversionservice.bean.CurrencyConversion;

//after using ribbon we don't need to put url in feign client
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")

//To connect to naming server
//@FeignClient(name = "currency-exchange-service")

//to get uri from zuul api gateway
@FeignClient(name = "netfliz-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	// to connect to naming server
	// @GetMapping("/currency-exchange/from/{from}/to/{to}")
	
	// to get uri from zuul api gateway
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
