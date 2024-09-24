package com.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Model.AddPrescriptionRequest;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.services.Prescriptionservice;

@CrossOrigin
@RestController
@RequestMapping("/venbaclinic/visit")
public class PrescriptionController 
{
	static final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);
	
	@Autowired
	private Prescriptionservice service;
	
	@GetMapping("/getdata")
	public CustomerResponse getallvisits(@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			return service.getallvistdtls(limit, offset);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			response.setRespcode("FF");
			response.setRespdesc("Some Error Occured!");
		}
		return response;
	}
	
	@PostMapping("/saveprescrption")
	public CustomerResponse saveprescription(@RequestBody AddPrescriptionRequest request)
	{
		return service.saveprescription(request);
	}
	
	@PostMapping("/searchvisit")
	public CustomerResponse searchvisit(@RequestBody CustomerRequest request,@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		return service.searchcust(request,limit,offset);
	}
	
	@GetMapping("/deletevisit/{id}")
	public CustomerResponse deletevisit(@PathVariable String id)
	{
		return service.deletevisit(id);
	}
	
	@GetMapping("/getfullvistdetails/{id}")
	public CustomerResponse getvisitdata(@PathVariable String id)
	{
		return service.getvisitdata(id);
	}

	@PostMapping("/updateprescrption")
	public CustomerResponse updateprescription(@RequestBody AddPrescriptionRequest request)
	{
		return service.updatevisitdetails(request);
	}
}
