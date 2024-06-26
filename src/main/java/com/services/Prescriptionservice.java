package com.services;

import com.Model.AddPrescriptionRequest;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;

public interface Prescriptionservice
{
	public CustomerResponse getallvistdtls(String limit,String offset);
	public CustomerResponse saveprescription(AddPrescriptionRequest request);
	public CustomerResponse searchcust(CustomerRequest request,String limit,String offset);
	public CustomerResponse deletevisit(String id);
	public CustomerResponse getvisitdata(String id);
	public CustomerResponse updatevisitdetails(AddPrescriptionRequest request);
}
