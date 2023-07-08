package com.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prescription_details")
public class PrescriptionDetails 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="p_id")
	private int pid;
	
	@Column(name="visit_id")
	private int visitid;
	
	@Column(name="drug_name")
	private String drugname;
	
	@Column(name="morng")
	private int morning;
	
	@Column(name="evening")
	private int evening;
	
	@Column(name="noon")
	private int noon;
	
	@Column(name="night")
	private int night;
	
	@Column(name="b_food")
	private Boolean beforefood;
	
	@Column(name="a_food")
	private Boolean afterfood;
	
	@Column(name="duration")
	private int duration;
	
	@Column(name="medtype")
	private String medtype;
	
	@Column(name="add_info")
	private String addinfo;
	
	@Column(name="created_date")
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
