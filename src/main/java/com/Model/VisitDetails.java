package com.Model;

import java.sql.Date;
import java.sql.Timestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//@Table(name="visit_details")
@Document(collection = "visit_details")
public class VisitDetails 
{
	@org.springframework.data.annotation.Id
	@Field(name="visit_id")
	private int visitid;
	
	@Field(name="cust_id")
	private int custid;
	
	@Field(name="visit_date")
	private Timestamp visitdate;
	
	@Field(name="age_year")
	private int ageyear;
	
	@Field(name="age_month")
	private int agemonth;
	
	@Field(name="age_day")
	private int ageday;
	
	@Field(name="age_week")
	private int ageweek;
	
	@Field(name="advice")
	private String advice;
	
	@Field(name="next_visit")
	private Date nextvisitdate;
	
	@Field(name="ABC")
	private String abc;
	
	@Field(name="vitals")
	private String vitals;
	
	@Field(name="ENT")
	private String ent;
	
	@Field(name="SE")
	private String se;
	
	@Field(name="Height")
	private int height;
	
	@Field(name="Weight")
	private int weight;
	
	@Field(name="HC")
	private int hc;
	
	@Field(name="diagnosis")
	private String diagnosis;
	
	@Field(name="queue_status")
	private String queuestatus="Completed";
	
	@Field(name="queue_no")
	private int queueno;
	

	public int getQueueno() {
		return queueno;
	}

	public void setQueueno(int queueno) {
		this.queueno = queueno;
	}

	public String getQueuestatus() {
		return queuestatus;
	}

	public void setQueuestatus(String queuestatus) {
		this.queuestatus = queuestatus;
	}

	public int getVisitid() {
		return visitid;
	}

	public void setVisitid(int visitid) {
		this.visitid = visitid;
	}

	public int getCustid() {
		return custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public Timestamp getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(Timestamp visitdate) {
		this.visitdate = visitdate;
	}
	
	public int getAgeyear() {
		return ageyear;
	}

	public void setAgeyear(int ageyear) {
		this.ageyear = ageyear;
	}

	public int getAgemonth() {
		return agemonth;
	}

	public void setAgemonth(int agemonth) {
		this.agemonth = agemonth;
	}

	public int getAgeday() {
		return ageday;
	}

	public void setAgeday(int ageday) {
		this.ageday = ageday;
	}

	public int getAgeweek() {
		return ageweek;
	}

	public void setAgeweek(int ageweek) {
		this.ageweek = ageweek;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Date getNextvisitdate() {
		return nextvisitdate;
	}

	public void setNextvisitdate(Date nextvisitdate) {
		this.nextvisitdate = nextvisitdate;
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHc() {
		return hc;
	}

	public void setHc(int hc) {
		this.hc = hc;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	

}
