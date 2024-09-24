package com.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="errorlogs")
@Data
public class ErrorLogDetails
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="errorid")
	private int errorid;
	
	@Column(name="module")
	private String module;
	
	@Column(name="created_date")
	private Timestamp createddate;
	
	@Column(name="error_msg")
	private String error_msg;
	
	@Column(name="request")
	private String request;

	public ErrorLogDetails(String module, String error_msg, String request) {
		super();
		this.module = module;
		this.error_msg = error_msg;
		this.request = request;
	}

	public int getErrorid() {
		return errorid;
	}

	public void setErrorid(int errorid) {
		this.errorid = errorid;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}
