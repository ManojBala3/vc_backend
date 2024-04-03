package com.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicine_details")
public class MedicineDetails 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="med_id")
	private int medid;
	
	@Column(name="medicine_name")
	private String medicinename;
	
	@Column(name="medicine_type")
	private String medicinetype;
	
	@Column(name="created_date")
	private Timestamp createddate;
	
	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public int getMedid() {
		return medid;
	}

	public void setMedid(int medid) {
		this.medid = medid;
	}

	public String getMedicinename() {
		return medicinename;
	}

	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}

	public String getMedicinetype() {
		return medicinetype;
	}

	public void setMedicinetype(String medicinetype) {
		this.medicinetype = medicinetype;
	}

	@Override
	public String toString() {
		return "MedicineDetails [medid=" + medid + ", medicinename=" + medicinename + ", medicinetype=" + medicinetype
				+ ", createddate=" + createddate + "]";
	}
	
	

}
