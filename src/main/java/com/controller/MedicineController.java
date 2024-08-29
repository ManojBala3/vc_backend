package com.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

import com.DAO.Medicinedao;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.Model.MedicineDetails;
import com.services.MedicineService;

//@CrossOrigin
@RestController
@RequestMapping("/venbaclinic/medicine")

public class MedicineController 
{
	static final Logger logger = LoggerFactory.getLogger(MedicineController.class);
	
	@Autowired
	MedicineService medicineservice;
	
	@Autowired
	Medicinedao medicinedao;
	
	@GetMapping("/getdata")
	public CustomerResponse getcustlatestest(@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			logger.info("coming inside" +limit +" "+offset);
			List<MedicineDetails> resp=medicineservice.getallrecords(limit,offset);
			if(resp!=null && resp.size()>0)
			{
				response.setRespcode("00");
				response.setMedresponse(resp);
				response.setTotalcount(medicineservice.getcount()+"");
			}
			else
			{
				response.setRespcode("01");
				response.setRespdesc("No Records Found.");
			}
			return response;
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			response.setRespcode("01");
			response.setRespdesc(e.getLocalizedMessage());
			return response;
		}
	}
	
	@PostMapping("/save")
	public MedicineDetails savecustomer(@RequestBody MedicineDetails request)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		request.setCreateddate(timestamp);
		try
		{
			return medicineservice.savemed(request);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/getmedicine")
	public CustomerResponse getmeddata(@RequestBody CustomerRequest request)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			logger.info("custid "+request.getCustid());
			Optional<MedicineDetails> resp=medicineservice.getbycustid(Integer.parseInt(request.getCustid()));
			if(resp!=null && resp.isPresent())
			{
				response.setRespcode("00");
				response.setMeddata(resp.get());
			}
			else
			{
				response.setRespcode("01");
				response.setRespdesc("No Records Found.");
			}
			return response;
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			response.setRespcode("01");
			response.setRespdesc(e.getLocalizedMessage());
			return response;
		}
	}
	
	@PostMapping("/updatecustomer")
	public MedicineDetails updatecustomer(@RequestBody MedicineDetails request)
	{
		try
		{
			return medicineservice.updatecust(request);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/deletecustomer/{custid}")
	public CustomerResponse updatecustomer(@PathVariable String custid)
	{
		try
		{
			return medicineservice.deletecust(custid);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/getcustdata")
	public CustomerResponse getcustomer(@RequestBody CustomerRequest request,@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		List<MedicineDetails> resp=null;
		if(request!=null && request.getName()!=null)
			resp= medicineservice.getcustdetails(request.getName(),"medicine",limit,offset);
		else
		{
			response.setRespcode("01");
			response.setRespdesc("Invalid Request");
		}
		if(resp!=null && resp.size()>0)
		{
			response.setRespcode("00");
			response.setMedresponse(resp);
			response.setTotalcount(medicinedao.getcountbymedicine(("%").concat(request.getName().concat("%")))+"");
			response.setRespdesc("Success");
		}
		else
		{
			response.setRespcode("01");
			response.setRespdesc("No Records Found.");
		}
		return response;
	}

	@GetMapping("/searchmedicine/{searchvalue}")
	public CustomerResponse search(@PathVariable String searchvalue)
	{
		return medicineservice.search(searchvalue);
	}
}
