package com.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.services.PatientQueueService;

@CrossOrigin
@RestController
@RequestMapping("/queue")
public class PatientQueueController {
	
	static final Logger logger = LoggerFactory.getLogger(PatientQueueController.class);
	
	@Autowired
	PatientQueueService queueservice;
	
	@GetMapping("/getdata")
	public CustomerResponse getallvisits(@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			return queueservice.getqueuelist(limit, offset);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			response.setRespcode("FF");
			response.setRespdesc("Some Error Occured!");
		}
		return response;
	}
	
	@PostMapping("/searchvisit")
	public CustomerResponse searchvisit(@RequestBody CustomerRequest request,@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		return queueservice.searchcust(request,limit,offset);
	}

}
