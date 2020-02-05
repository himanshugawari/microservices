package com.gawari._himanshu.microservices.netflixzuulapigatewayserver.logging;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("-----------------------------------------------------------------------");
		logger.info("request -> {}  request uri -> {}", request.getServerName(), request.getRequestURI());
		logger.info("-----------------------------------------------------------------------");
		return null;
	}

	@Override
	public String filterType() {
		// return "pre / post / error";
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
