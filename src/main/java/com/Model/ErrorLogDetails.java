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
	
}
