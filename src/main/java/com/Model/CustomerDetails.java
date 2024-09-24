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

	public int getCustid() {
		return custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getStringdob() {
		return stringdob;
	}

	public void setStringdob(String stringdob) {
		this.stringdob = stringdob;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	
	
	
}
