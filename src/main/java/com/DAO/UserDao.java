package com.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Model.UserModel;

public interface UserDao  extends JpaRepository<UserModel, Integer>{
	
	@Query("Select u from UserModel  u where username= ?1")
	UserModel validateuser(String username);
	
	@Query(value="Select id,user_name,created_by,user_role,DATE_FORMAT(created_date,'%d/%m/%Y %H:%m:%s') as createdate from user_details  LIMIT ?1 offset ?2",nativeQuery = true)
	List<Map<String,String>> getusers(int limit,int offset);
	
	@Query(value="Select id,user_name,created_by,user_role,DATE_FORMAT(created_date,'%d/%m/%Y %H:%m:%s') as createdate from user_details where lower(user_role) like lower(?1) LIMIT ?2 offset ?3",nativeQuery = true)
	List<Map<String,String>> searchbyrole(String role,int limit,int offset);
	
	@Query(value="Select id,user_name,created_by,user_role,DATE_FORMAT(created_date,'%d/%m/%Y %H:%m:%s') as createdate from user_details where lower(user_name) like lower(?1) LIMIT ?2 offset ?3",nativeQuery = true)
	List<Map<String,String>> searchbyusername(String username,int limit,int offset);

}
