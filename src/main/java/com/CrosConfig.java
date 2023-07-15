package com;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CrosConfig extends  CorsFilter  {

	public CrosConfig(CorsConfigurationSource configSource) {
		super(configSource);
		
	}
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
