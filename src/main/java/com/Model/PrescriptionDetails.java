package com.Model;

import java.sql.Timestamp;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//@Table(name="prescription_details")
@Document(collection = "prescription_details")
public class PrescriptionDetails 
{
	@org.springframework.data.annotation.Id
	@Field(name="p_id")
	private int pid;
	
	@Field(name="visit_id")
	private int visitid;
	
	@Field(name="drug_name")
	private String drugname;
	
	@Field(name="morng")
	private int morning;
	
	@Field(name="evening")
	private int evening;
	
	@Field(name="noon")
	private int noon;
	
	@Field(name="night")
	private int night;
	
	@Field(name="b_food")
	private Boolean beforefood;
	
	@Field(name="a_food")
	private Boolean afterfood;
	
	@Field(name="duration")
	private int duration;
	
	@Field(name="medtype")
	private String medtype;
	
	@Field(name="add_info")
	private String addinfo;
	
	@Field(name="created_date")
	private Timestamp createddate;

	public String getAddinfo() {
		return addinfo;
	}

	public void setAddinfo(String addinfo) {
		this.addinfo = addinfo;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getVisitid() {
		return visitid;
	}

	public void setVisitid(int visitid) {
		this.visitid = visitid;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public int getMorning() {
		return morning;
	}

	public void setMorning(int morning) {
		this.morning = morning;
	}

	public int getEvening() {
		return evening;
	}

	public void setEvening(int evening) {
		this.evening = evening;
	}

	public int getNoon() {
		return noon;
	}

	public void setNoon(int noon) {
		this.noon = noon;
	}

	public int getNight() {
		return night;
	}

	public void setNight(int night) {
		this.night = night;
	}

	public String getMedtype() {
		return medtype;
	}

	public void setMedtype(String medtype) {
		this.medtype = medtype;
	}

	public Boolean getBeforefood() {
		return beforefood;
	}

	public void setBeforefood(Boolean beforefood) {
		this.beforefood = beforefood;
	}

	public Boolean getAfterfood() {
		return afterfood;
	}

	public void setAfterfood(Boolean afterfood) {
		this.afterfood = afterfood;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	@Override
	public String toString() {
		return "PrescriptionDetails [pid=" + pid + ", visitid=" + visitid + ", drugname=" + drugname + ", morning="
				+ morning + ", evening=" + evening + ", noon=" + noon + ", night=" + night + ", beforefood="
				+ beforefood + ", afterfood=" + afterfood + ", duration=" + duration + ", createddate=" + createddate
				+ "]";
	}
	
	

}
