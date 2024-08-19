package com.Model;

import java.sql.Timestamp;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



//@Entity
@Document(collection = "user_details")
//@Table(name="user_details")
public class UserModel {
	
	@org.springframework.data.annotation.Id
	
	@Field(name="id")
	private int id;
	
	@Field(name="user_name")
	private String username;
	
	@Field(name="created_date")
	private java.util.Date createddate;
	
	@Field(name="password")
	private  byte[] password;
	
	@Field(name="created_by")
	private String createdby;
	
	@Field(name="user_role")
	private String userrole;
	
	@Field(name="user_salt")
	private byte[] usersalt;

	@org.springframework.data.annotation.Transient
	private String userpassword;
	
	@org.springframework.data.annotation.Transient
	private String createdate;
	
	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public  byte[] getUsersalt() {
		return usersalt;
	}

	public void setUsersalt( byte[] usersalt) {
		this.usersalt = usersalt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public java.util.Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(java.util.Date visitdate) {
		this.createddate = visitdate;
	}

	public  byte[] getPassword() {
		return password;
	}

	public void setPassword( byte[] password) {
		this.password = password;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	
	
}
