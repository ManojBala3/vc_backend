package com;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Configuration
public class CrosConfig extends  CorsFilter  {

	public CrosConfig(CorsConfigurationSource configSource) {
		super(configSource);
		
	}

	/*
	 * @Override protected void doFilterInternal(ServletRequest servletRequest,
	 * ServletResponse servletResponse, FilterChain chain) throws IOException,
	 * ServletException { // TODO Auto-generated method stub HttpServletResponse
	 * response = (HttpServletResponse) servletResponse; HttpServletRequest request=
	 * (HttpServletRequest) servletRequest;
	 * 
	 * 
	 * 
	 * //response.setHeader("Access-Control-Allow-Origin",
	 * "http://venbafrontend.s3-website-ap-southeast-2.amazonaws.com"); //
	 * response.setHeader("Access-Control-Allow-Origin",
	 * request.getHeader("Origin"));
	 * //response.setHeader("Access-Control-Allow-Methods",
	 * "GET,POST,DELETE,PUT,OPTIONS");
	 * //response.setHeader("Access-Control-Allow-Headers",
	 * "Origin, Accept, x-auth-token, " // +
	 * "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, token"
	 * ); //response.setHeader("Access-Control-Allow-Credentials", "true");
	 * //response.setHeader("Access-Control-Max-Age", "180");
	 * 
	 * response.setHeader("Access-Control-Allow-Origin",
	 * request.getHeader("Origin"));
	 * response.setHeader("Access-Control-Allow-Credentials", "true");
	 * response.setHeader("Access-Control-Allow-Methods",
	 * "POST, GET, OPTIONS, DELETE"); response.setHeader("Access-Control-Max-Age",
	 * "3600"); response.setHeader("Access-Control-Allow-Headers",
	 * "Content-Type, Accept, X-Requested-With, remember-me");
	 * chain.doFilter(servletRequest, servletResponse);
	 * 
	 * 
	 * }
	 */
	
	 @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
             throws ServletException, IOException {

         response.addHeader("Access-Control-Allow-Headers",
                 "Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
         response.setHeader("Access-Control-Max-Age","3600");
         response.setHeader("Access-Control-Allow-Methods", "POST, GET");
         filterChain.doFilter(request, response);
     }


}
