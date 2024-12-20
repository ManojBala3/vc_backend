package com.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.config.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.UserDao;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.Model.UserModel;
import com.common.Commonservice;

@Service
public class UserServiceImpl implements UserService{
	
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDao userdao;

	@Autowired
	JwtTokenUtil tokenutil;

	@Override
	public CustomerResponse saveuser(UserModel usermodel) {
		CustomerResponse response=new CustomerResponse();
		try
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			usermodel.setCreateddate(timestamp);
			byte[] salt=Commonservice.generateSalt(10);
			usermodel.setPassword(Commonservice.hashPassword(usermodel.getUserpassword(),salt));
			usermodel.setUsersalt(salt);
			userdao.save(usermodel);
			response.setRespcode("00");
			response.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			if(e.getLocalizedMessage().contains("user_name_UNIQUE"))
					response.setRespdesc("Username Already Exists");
			else
				response.setRespdesc("Some Error Occured "+e.getLocalizedMessage());
		}
		return response;
	}
	
	
	public CustomerResponse loginverify(UserModel usermodel) {
		CustomerResponse response=new CustomerResponse();
		UserModel usermodelDB=null;
		Map<String,String> userdata=new HashMap<String,String>();
		try
		{
			usermodelDB=userdao.validateuser(usermodel.getUsername());
			if(usermodelDB!=null && usermodelDB.getUsername()!=null)
			{
				byte[] enterpass=Commonservice.hashPassword(usermodel.getUserpassword(),usermodelDB.getUsersalt());
				if(Arrays.equals(enterpass, usermodelDB.getPassword()))
				{
					response.setRespcode("00");
					response.setRespdesc("Success");
					userdata.put("role", usermodelDB.getUserrole());
					userdata.put("username", usermodelDB.getUsername());
					userdata.put("token",tokenutil.generateToken(usermodelDB.getUsername(),usermodelDB.getUserrole(),usermodelDB.getId()+""));
					response.setCommon(userdata);

					return response;
				}	
			}
				response.setRespcode("01");
				response.setRespdesc("failure");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured "+e.getLocalizedMessage());
		}
		finally
		{
			usermodelDB=null;
			userdata=null;
		}
		return response;
	}


	@Override
	public CustomerResponse deleteuser(String id)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			userdao.deleteById(Integer.parseInt(id));
			response.setRespcode("00");
			response.setRespdesc("Success");
			return response;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured "+e.getLocalizedMessage());
		}
		return response;
	}
	
	
	public CustomerResponse fetchalluser(String limit,String offset)
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			List<Map<String,String>> usermodel=userdao.getusers(Integer.parseInt(limit),Integer.parseInt(offset));
			if(usermodel!=null && !usermodel.isEmpty())
			{
				response.setRespcode("00");
				response.setRespdesc("Success");
				response.setCommon(usermodel);
				response.setTotalcount(usermodel.size() +"");
				return response;
			}
			response.setRespcode("01");
			response.setRespdesc("No records found.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured "+e.getLocalizedMessage());
		}
		return response;
	}


	@Override
	public CustomerResponse searchuser(String limit, String offset,UserModel request) {

		CustomerResponse response=new CustomerResponse();
		List<Map<String,String>> usermodel=null;
		try
		{
			if(request.getUsername()!=null)
				usermodel=userdao.searchbyusername((("%").concat(request.getUsername().concat("%"))),Integer.parseInt(limit),Integer.parseInt(offset));
			else
				usermodel=userdao.searchbyrole((("%").concat(request.getUserrole().concat("%"))),Integer.parseInt(limit),Integer.parseInt(offset));
			if(usermodel!=null && !usermodel.isEmpty())
			{
				response.setRespcode("00");
				response.setRespdesc("Success");
				response.setCommon(usermodel);
				response.setTotalcount(usermodel.size() +"");
				return response;
			}
			response.setRespcode("01");
			response.setRespdesc("No records found.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured "+e.getLocalizedMessage());
		}
		return response;
	}
	
	

}
