package com.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.ErrorLogDao;
import com.DAO.VisitDetailsDao;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.Model.ErrorLogDetails;
import com.common.Commonservice;

@Service
public class PatientQueueServiceImpl implements PatientQueueService{
	
	@Autowired
	VisitDetailsDao visitdao;
	
	@Autowired
	private ErrorLogDao errordao;

	@Override
	public CustomerResponse getqueuelist(String limit, String offset) {
		CustomerResponse custresponse=new CustomerResponse();
		List<Map<String,String>> vresp=visitdao.getqueuelist(Integer.parseInt(limit), Integer.parseInt(offset));
		if(vresp!=null && vresp.size()>0)
		{
			custresponse.setCommon(vresp);
			custresponse.setRespcode("00");
			custresponse.setRespdesc("Success");
			custresponse.setTotalcount(visitdao.getTotalQueueVisits()+"");
		}
		else
		{
			custresponse.setRespcode("01");
			custresponse.setRespdesc("No Records Found");
		}
		return custresponse;
	}
	
	@Override
	public CustomerResponse searchcust(CustomerRequest request, String limit, String offset) 
	{
		CustomerResponse custresponse=new CustomerResponse();
		List<Map<String,String>> obj=null;
		String searchvalue="";
		try
		{
			if(request.getName()!=null)
			{
				searchvalue=("%").concat(request.getName().concat("%"));
				obj=visitdao.getvisitdetailsbyname(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(visitdao.countbyname(searchvalue)+""); 
			}
			else if(request.getMobilenumber()!=null)
			{
				searchvalue=("%").concat(request.getMobilenumber().concat("%"));
				obj=visitdao.getvisitdetailsbynumber(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(visitdao.countbynumber(searchvalue)+""); 
			}
			else if(request.getCustid()!=null)
			{
				searchvalue=("%").concat(request.getCustid().concat("%"));
				obj=visitdao.getvisitdetailsbycustid(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(visitdao.countbyid(searchvalue)+""); 
			}
			if(obj!=null && obj.size()>0) 
			{ 
				custresponse.setRespcode("00");
				custresponse.setRespdesc("Success");
				custresponse.setCommon(obj);
			}
			else
			{ 
				custresponse.setRespcode("01");
				custresponse.setRespdesc("No Records Found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			custresponse.setRespcode("01");
			custresponse.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("searchcust - Queue",e.getLocalizedMessage(),Commonservice.printresp(request)));
		}
		return custresponse;
	}
	
	public void inserterrorlog(ErrorLogDetails errorlog)
	 {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		errorlog.setCreateddate(timestamp);
		errordao.save(errorlog);
	 }

}
