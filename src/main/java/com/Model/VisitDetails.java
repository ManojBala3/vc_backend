package com.Model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visit_details")
public class VisitDetails 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="visit_id")
	private int visitid;
	
	@Column(name="cust_id")
	private int custid;
	
	@Column(name="visit_date")
	private Timestamp visitdate;
	
	@Column(name="age_year")
	private int ageyear;
	
	@Column(name="age_month")
	private int agemonth;
	
	@Column(name="age_day")
	private int ageday;
	
	@Column(name="age_week")
	private int ageweek;
	
	@Column(name="advice")
	private String advice;
	
	@Column(name="next_visit")
	private Date nextvisitdate;
	
	@Column(name="ABC")
	private String abc;
	
	@Column(name="vitals")
	private String vitals;
	
	@Column(name="ENT")
	private String ent;
	
	@Column(name="SE")
	private String se;
	
	@Column(name="Height")
	private int height;
	
	@Column(name="Weight")
	private int weight;
	
	@Column(name="HC")
	private int hc;
	
	@Column(name="diagnosis")
	private String diagnosis;
	
	@Column(name="queue_status")
	private String queuestatus="Completed";
	
	@Column(name="queue_no")
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
