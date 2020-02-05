package com.gawari._himanshu.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gawari._himanshu.microservices.limitsservice.bean.LimitConfiguration;
import com.gawari._himanshu.microservices.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration conf;

	@GetMapping(path = "/limits")
	public LimitConfiguration retrieveLimitsFromCongiguration() {
		return new LimitConfiguration(conf.getMaximum(), conf.getMinimum());
	}

	@GetMapping(path = "/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveCongiguration")
	public LimitConfiguration retrieveCongiguration() {
		throw new RuntimeException("NOT AVAILABLE");
	}

	public LimitConfiguration fallbackRetrieveCongiguration() {
		return new LimitConfiguration(999, 9);
	}

}
