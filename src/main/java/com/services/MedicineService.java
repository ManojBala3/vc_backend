package com.services;

import java.util.List;
import java.util.Optional;

import com.Model.CustomerResponse;
import com.Model.MedicineDetails;

public interface MedicineService {
	
	public List<MedicineDetails> getallrecords(String limit,String offset);
	public Optional<MedicineDetails> getbycustid(int id);
	public List<MedicineDetails> getcustdetails(String medicine,String searchtype,String limit,String offset);
	public MedicineDetails savemed(MedicineDetails request);
	public int getcount();
	public MedicineDetails updatecust(MedicineDetails request);
	public CustomerResponse deletecust(String custid);
	public CustomerResponse search(String searchvalue);

}
