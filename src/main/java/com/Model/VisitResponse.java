package com.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitResponse {
	
	public int age;
    @JsonProperty("Gender") 
    public Object gender;
    public int visit_id;
    public String mobile_no;
    public String visit_date;
    public String customer_name;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Object getGender() {
		return gender;
	}
	public void setGender(Object gender) {
		this.gender = gender;
	}
	public int getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(int visit_id) {
		this.visit_id = visit_id;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
    
    

}
