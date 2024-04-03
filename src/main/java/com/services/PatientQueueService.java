package com.services;

import com.Model.CustomerRequest;
import com.Model.CustomerResponse;

public interface PatientQueueService {
	
	public CustomerResponse getqueuelist(String limit,String offset);
	public CustomerResponse searchcust(CustomerRequest request, String limit, String offset);

}
