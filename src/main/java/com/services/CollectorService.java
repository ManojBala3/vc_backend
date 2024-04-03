package com.services;

import java.util.List;
import java.util.Optional;

import com.Model.CustomerDetails;
import com.Model.CustomerResponse;

public interface CollectorService {
	
	public List<CustomerDetails> getallrecords(String limit,String offset);
	public Optional<CustomerDetails> getbycustid(int id);
	public List<CustomerDetails> getcustdetails(String mobileno,String searchtype,String limit,String offset);
	public CustomerResponse savecustomer(CustomerDetails request);
	public int getcount();
	public CustomerResponse updatecust(CustomerDetails request);
	public CustomerResponse deletecust(String custid);
	public String generatecustid();
}
