package com.services;

import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.Model.UserModel;

public interface UserService {
	
	public CustomerResponse saveuser(UserModel usermodel);
	public CustomerResponse loginverify(UserModel usermodel);
	public CustomerResponse deleteuser(String id);
	public CustomerResponse fetchalluser(String limit,String offset);
	public CustomerResponse searchuser(String limit,String offset,UserModel request);

}
