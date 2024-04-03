package com.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.ErrorLogDao;
import com.DAO.Medicinedao;
import com.Model.CustomerResponse;
import com.Model.ErrorLogDetails;
import com.Model.MedicineDetails;

@Service
public class MedicineServiceImpl implements MedicineService
{
	@Autowired
	private Medicinedao medicinedao;
	
	@Autowired
	private ErrorLogDao errordao;
	
	@Override
	public List<MedicineDetails> getallrecords(String limit, String offset) {
		return medicinedao.getupdaterecords(Integer.parseInt(limit),Integer.parseInt(offset));
	}

	@Override
	public Optional<MedicineDetails> getbycustid(int id) {
		return medicinedao.findById(id);
	}

	@Override
	public List<MedicineDetails> getcustdetails(String searchvalue, String searchtype, String limit, String offset) {
		if(searchtype.equalsIgnoreCase("medicine"))
		{
			searchvalue=("%").concat(searchvalue.concat("%"));
			return medicinedao.getcustDetailsMedicine(searchvalue,Integer.parseInt(limit),Integer.parseInt(offset));
		}
		return null;
	}

	@Override
	public MedicineDetails savemed(MedicineDetails request) {
		return medicinedao.save(request);
	}

	@Override
	public int getcount() {
		return medicinedao.getrecordscount();
	}

	@Override
	public MedicineDetails updatecust(MedicineDetails request)
	{
		Optional<MedicineDetails> old=medicinedao.findById(request.getMedid());
		if(old!=null && old.isPresent())
		{
			MedicineDetails olddata=old.get();
			olddata.setMedicinename(request.getMedicinename());
			olddata.setMedicinetype(request.getMedicinetype());
			return medicinedao.save(request);
		}
		return null;
	}

	@Override
	public CustomerResponse deletecust(String custid) {
		CustomerResponse response =new CustomerResponse();
		try
		{
			medicinedao.deleteById(Integer.parseInt(custid));
			response.setRespcode("00");
			response.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some error occured");
			inserterrorlog(new ErrorLogDetails("deletecust - Medical",e.getLocalizedMessage(),custid));
		}
		return response;
	}

	@Override
	public CustomerResponse search(String searchvalue) {
		
		CustomerResponse response =new CustomerResponse();
		try
		{
			searchvalue=("%").concat(searchvalue.concat("%"));
			List<MedicineDetails> med=medicinedao.searchmedicine(searchvalue);
			if(med!=null && med.size()>0)
			{
				response.setRespcode("00");
				response.setRespdesc("Success");
				response.setCommon(med);
			}
			else
			{
				response.setRespcode("01");
				response.setRespdesc("No Records found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some error occured");
			inserterrorlog(new ErrorLogDetails("search - Medical",e.getLocalizedMessage(),searchvalue));
		}
		return response;
	}
	
	public void inserterrorlog(ErrorLogDetails errorlog)
	 {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		errorlog.setCreateddate(timestamp);
		errordao.save(errorlog);
	 }

}
