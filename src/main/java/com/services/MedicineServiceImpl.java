package com.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.Medicinedao;
import com.Model.CustomerResponse;
import com.Model.MedicineDetails;

@Service
public class MedicineServiceImpl implements MedicineService
{
	@Autowired
	private Medicinedao medicinedao;
	
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
	public MedicineDetails savecustomer(MedicineDetails request) {
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
		}
		return response;
	}

	@Override
	public CustomerResponse search(String searchvalue) {
		
		CustomerResponse response =new CustomerResponse();
		try
		{
			searchvalue=("%").concat(searchvalue.concat("%"));
			List<String> med=medicinedao.searchmedicine(searchvalue);
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
		}
		return response;
	}

}
