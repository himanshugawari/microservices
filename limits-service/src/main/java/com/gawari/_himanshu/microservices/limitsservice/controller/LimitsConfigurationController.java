package com.gawari._himanshu.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gawari._himanshu.microservices.limitsservice.bean.LimitConfiguration;
import com.gawari._himanshu.microservices.limitsservice.configuration.Configuration;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration conf;

	@GetMapping(path = "/limits")
	public LimitConfiguration retrieveLimitsFromCongiguration() {
		return new LimitConfiguration(conf.getMaximum(), conf.getMinimum());
	}

}
