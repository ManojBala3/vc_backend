package com.Model;

public class CustomerRequest {
	
	private String mobilenumber;
	private String name;
	private String custid;
	private String visitdate;
	
	public String getCustid() {
		return custid;
	}
	public String getVisitdate() {
		return visitdate;
	}
	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CustomerRequest [mobilenumber=" + mobilenumber + ", name=" + name + "]";
	}
	
	

}
