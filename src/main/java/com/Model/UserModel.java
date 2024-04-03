package com.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="user_details")
public class UserModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="created_date")
	private Timestamp createddate;
	
	@Column(name="password")
	private  byte[] password;
	
	@Column(name="created_by")
	private String createdby;
	
	@Column(name="user_role")
	private String userrole;
	
	@Column(name="user_salt")
	private byte[] usersalt;

	@Transient
	private String userpassword;
	
	@Transient
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

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp visitdate) {
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
