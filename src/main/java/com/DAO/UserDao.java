package com.DAO;

import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.repository.Query;
import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Model.UserModel;

public interface UserDao  extends MongoRepository<UserModel, Integer>{
	
	@Query("{ 'user_name': ?0 }")
	Document findbyusername(String username);
	
	@Query(value="Select id,user_name,created_by,user_role,DATE_FORMAT(created_date,'%d/%m/%Y %H:%m:%s') as createdate from user_details  LIMIT ?1 offset ?2")
	List<Map<String,String>> getusers(int limit,int offset);

}
