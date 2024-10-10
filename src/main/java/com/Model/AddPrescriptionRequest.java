package com.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPrescriptionRequest 
{
	public String custid;
	public String patientid;
	public String oldPatientId;
	public String visitid;
	public String customername;
	public int customerageyear;
	public int customeragemonth;
	public int customerageweek;
	public int customerageday;
	public String customeremail;
	public String customergender;
	public String abc;
	public String vitals;
	public String ent;
	public String se;
	public int weight;
	public int height;
	public int hc;
	public Timestamp visitdate;
	public String customermobile;
	public String additionalnote;
	public Date nextreview;
	public String comments;
	public ArrayList<PrescriptionDetails> products;
	private boolean addqueue=false;
	
	public String getOldPatientid() {
		return oldPatientId;
	}

	public void setOldPatientid(String oldPatientid) {
		this.oldPatientId = oldPatientid;
	}
	public boolean isAddqueue() {
		return addqueue;
	}

	public void setAddqueue(boolean addqueue) {
		this.addqueue = addqueue;
	}
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	public Date getNextreview() {
		return nextreview;
	}
	public void setNextreview(Date nextreview) {
		this.nextreview = nextreview;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getCustomerageyear() {
		return customerageyear;
	}
	public void setCustomerageyear(int customerageyear) {
		this.customerageyear = customerageyear;
	}
	public int getCustomeragemonth() {
		return customeragemonth;
	}
	public void setCustomeragemonth(int customeragemonth) {
		this.customeragemonth = customeragemonth;
	}
	public int getCustomerageweek() {
		return customerageweek;
	}
	public void setCustomerageweek(int customerageweek) {
		this.customerageweek = customerageweek;
	}
	public int getCustomerageday() {
		return customerageday;
	}
	public void setCustomerageday(int customerageday) {
		this.customerageday = customerageday;
	}
	public String getVisitid() {
		return visitid;
	}
	public void setVisitid(String visitid) {
		this.visitid = visitid;
	}
	public Timestamp getVisitdate() {
		return visitdate;
	}
	public void setVisitdate(Timestamp visitdate) {
		this.visitdate = visitdate;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomeremail() {
		return customeremail;
	}
	public void setCustomeremail(String customeremail) {
		this.customeremail = customeremail;
	}
	public String getCustomergender() {
		return customergender;
	}
	public void setCustomergender(String customergender) {
		this.customergender = customergender;
	}
	public String getAbc() {
		return abc;
	}
	public void setAbc(String abc) {
		this.abc = abc;
	}
	public String getVitals() {
		return vitals;
	}
	public void setVitals(String vitals) {
		this.vitals = vitals;
	}
	public String getEnt() {
		return ent;
	}
	public void setEnt(String ent) {
		this.ent = ent;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHc() {
		return hc;
	}
	public void setHc(int hc) {
		this.hc = hc;
	}
	public String getCustomermobile() {
		return customermobile;
	}
	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}
	public String getAdditionalnote() {
		return additionalnote;
	}
	public void setAdditionalnote(String additionalnote) {
		this.additionalnote = additionalnote;
	}
	public ArrayList<PrescriptionDetails> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<PrescriptionDetails> products) {
		this.products = products;
	}
	@Override
	public String toString() {
		return "AddPrescriptionRequest [custid=" + custid + ", visitid=" + visitid + ", customername=" + customername
				+ ", customerageyear=" + customerageyear + ", customeragemonth=" + customeragemonth
				+ ", customerageweek=" + customerageweek + ", customerageday=" + customerageday + ", customeremail="
				+ customeremail + ", customergender=" + customergender + ", abc=" + abc + ", vitals=" + vitals
				+ ", ent=" + ent + ", se=" + se + ", weight=" + weight + ", height=" + height + ", hc=" + hc
				+ ", visitdate=" + visitdate + ", customermobile=" + customermobile + ", additionalnote="
				+ additionalnote + ", nextreview=" + nextreview + ", comments=" + comments + ", products=" + products
				+ "]";
	}
	
	
	
	
}
