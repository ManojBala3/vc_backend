package com.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.DAO.UserDao;
import com.Model.CustomerResponse;
import com.Model.UserModel;
import com.common.Commonservice;

@Service
public class UserServiceImpl implements UserService{
	
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDao userdao;
	
	@Autowired
    private MongoTemplate mongoTemplate;

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
			Document document=userdao.findbyusername(usermodel.getUsername());
			usermodelDB=mongoTemplate.getConverter().read(UserModel.class, document);
			if(usermodelDB!=null && usermodelDB.getUsername()!=null)
			{
				byte[] enterpass=Commonservice.hashPassword(usermodel.getUserpassword(),usermodelDB.getUsersalt());
				if(Arrays.equals(enterpass, usermodelDB.getPassword()))
				{
					response.setRespcode("00");
					response.setRespdesc("Success");
					userdata.put("role", usermodelDB.getUserrole());
					userdata.put("username", usermodelDB.getUsername());
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
		return null;
	}
	
	

}
