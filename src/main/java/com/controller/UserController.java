package com.controller;

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
import com.Model.CustomerResponse;
import com.Model.UserModel;
import com.services.UserService;

//@CrossOrigin
@RestController
@RequestMapping("/venbaclinic/user")
public class UserController
{
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userservice;
	
	@PostMapping("/saveuser")
	public CustomerResponse saveuserdata(@RequestBody UserModel request)
	{
		return userservice.saveuser(request);
	}
	
	@PostMapping("/verifyuser")
	public CustomerResponse verifyuser(@RequestBody UserModel request)
	{
		return userservice.loginverify(request);
	}
	
	@GetMapping("/delete/{custid}")
	public CustomerResponse delete(@PathVariable String custid)
	{
		return userservice.deleteuser(custid);
	}
	
	@GetMapping("/getalluser")
	public CustomerResponse getalldata(@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		return userservice.fetchalluser(limit,offset);
	}
	
	@PostMapping("/searchuser")
	public CustomerResponse searchdata(@RequestBody UserModel request,@PathParam("limit")String limit,@PathParam("offset")String offset)
	{
		return userservice.searchuser(limit,offset,request);
	}

	@GetMapping("/checkserver")
	public String checkserver()
	{
		return "Server is up and running";
	}

}
