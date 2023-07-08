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

import com.DAO.CustomerDetailsDao;
import com.Model.CustomerDetails;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.common.Commonservice;
import com.services.CollectorService;


@CrossOrigin
@RestController
@RequestMapping
public class CollectorController
{
	static final Logger logger = LoggerFactory.getLogger(CollectorController.class);
	
	@Autowired
	private CollectorService collectorservice;
	
	@Autowired
	private CustomerDetailsDao customerdao;

	@GetMapping("/getdata")
	public CustomerResponse getcustlatestest(@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			logger.info("coming inside" +limit +" "+offset);
			List<CustomerDetails> resp=collectorservice.getallrecords(limit,offset);
			if(resp!=null && resp.size()>0)
			{
				response.setRespcode("00");
				response.setResponse(resp);
				response.setTotalcount(collectorservice.getcount()+"");
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
			response.setRespcode("01");
			response.setRespdesc(e.getLocalizedMessage());
			return response;
		}
	}
	
	@PostMapping("/getcustdata")
	public CustomerResponse getcustomer(@RequestBody CustomerRequest request,@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		CustomerResponse response=new CustomerResponse();
		List<CustomerDetails> resp=null;
		int recordcount=0;
		if(request!=null && request.getMobilenumber()!=null)
		{
			resp= collectorservice.getcustdetails(request.getMobilenumber(),"mobile",limit,offset);
			if(resp!=null && resp.size()>0)
				recordcount=customerdao.getcustDetailsMobilecount(("%").concat(request.getMobilenumber().concat("%")));
		}
		else if(request!=null && request.getName()!=null)
		{
			resp= collectorservice.getcustdetails(request.getName(),"name",limit,offset);
			if(resp!=null && resp.size()>0)
				recordcount=customerdao.getcustDetailsname(("%").concat(request.getName().concat("%")));
		}
		
		else if(request!=null && request.getCustid()!=null)
		{
			resp= collectorservice.getcustdetails(request.getCustid(),"patientid",limit,offset);
			if(resp!=null && resp.size()>0)
				recordcount=customerdao.getcustDetailsid(("%").concat(request.getCustid().concat("%")));
		}
		else
		{
			response.setRespcode("01");
			response.setRespdesc("Invalid Request");
		}
		if(resp!=null && resp.size()>0)
		{
			response.setRespcode("00");
			response.setResponse(resp);
			response.setTotalcount(recordcount+"");
			response.setRespdesc("Success");
		}
		else
		{
			response.setRespcode("01");
			response.setRespdesc("No Records Found.");
		}
		return response;
	}
	
	@PostMapping("/savecustomer")
	public CustomerResponse savecustomer(@RequestBody CustomerDetails request)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		request.setCreateddate(timestamp);
		logger.info(request.toString());
		try
		{
			return collectorservice.savecustomer(request);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/getbycustid/{custid}")
	public CustomerResponse getcustdata(@PathVariable int custid)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			System.out.println("custid "+custid);
			Optional<CustomerDetails> resp=collectorservice.getbycustid(custid);
			if(resp!=null && resp.isPresent())
			{
				response.setRespcode("00");
				response.setCustdata(resp.get());
				if(response.getCustdata().getDob()!=null)
					response.getCustdata().setStringdob(Commonservice.convertdateformat(response.getCustdata().getDob(), "dd-MM-yyyy"));
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
	public CustomerResponse updatecustomer(@RequestBody CustomerDetails request)
	{
		logger.info("updatecustomer "+request);
		try
		{
			return collectorservice.updatecust(request);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/deletecustomer/{custid}")
	public CustomerResponse deletecustomer(@PathVariable String custid)
	{
		try
		{
			return collectorservice.deletecust(custid);
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}
