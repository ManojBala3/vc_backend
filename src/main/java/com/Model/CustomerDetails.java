package com.Model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name="customer_details")
@Data
public class CustomerDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cust_id")
	private int custid;
	
	@Column(name="mobile_no")
	private String mobileno;
	
	@Column(name="customer_name")
	private String custname;
	
	@Column(name="emailid")
	private String emailid;
	
	@Column(name="state")
	private String state;
	
	@Column(name="District")
	private String district;
	
	@Column(name="Gender")
	private String gender;
	
	@Column(name="customer_id")
	private String customerid;
	
	@Column(name="dob")
	private Date dob;
	
	@Transient
	private String stringdob;
	
	@Transient
	private String age;
	
	@Column(name="created_date")
	private Timestamp createddate;

	@Override
	public String toString() {
		return "CustomerDetails [custid=" + custid + ", mobileno=" + mobileno + ", custname=" + custname + ", emailid="
				+ emailid + ", state=" + state + ", district=" + district + ", gender=" + gender + ", dob=" + dob
				+ ", stringdob=" + stringdob + ", createddate=" + createddate + "]";
	}

	
	
	
}
