package com.Model;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;


@Document(collection = "customer_details")
//@Table(name="customer_details")
@Data
public class CustomerDetails {

	@Id
	@Field(name="cust_id")
	private int custid;
	
	@Field(name="mobile_no")
	private String mobileno;
	
	@Field(name="customer_name")
	private String custname;
	
	@Field(name="emailid")
	private String emailid;
	
	@Field(name="state")
	private String state;
	
	@Field(name="District")
	private String district;
	
	@Field(name="Gender")
	private String gender;
	
	@Field(name="customer_id")
	private String customerid;
	
	@Field(name="dob")
	private Date dob;
	
	@Transient
	private String stringdob;
	
	@Transient
	private String age;
	
	@Field(name="created_date")
	private Timestamp createddate;

	@Override
	public String toString() {
		return "CustomerDetails [custid=" + custid + ", mobileno=" + mobileno + ", custname=" + custname + ", emailid="
				+ emailid + ", state=" + state + ", district=" + district + ", gender=" + gender + ", dob=" + dob
				+ ", stringdob=" + stringdob + ", createddate=" + createddate + "]";
	}

	
	
	
}
