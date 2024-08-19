package com.Model;

import java.sql.Timestamp;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "errorlogs")
//@Table(name="errorlogs")
@Data
public class ErrorLogDetails
{
	@org.springframework.data.annotation.Id
	@Field(name="errorid")
	private int errorid;
	
	@Field(name="module")
	private String module;
	
	@Field(name="created_date")
	private Timestamp createddate;
	
	@Field(name="error_msg")
	private String error_msg;
	
	@Field(name="request")
	private String request;

	public ErrorLogDetails(String module, String error_msg, String request) {
		super();
		this.module = module;
		this.error_msg = error_msg;
		this.request = request;
	}
	
}
