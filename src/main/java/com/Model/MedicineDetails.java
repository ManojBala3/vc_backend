package com.Model;

import java.sql.Timestamp;



import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//@Table(name="medicine_details")
@Document(collection = "medicine_details")
public class MedicineDetails 
{
	@org.springframework.data.annotation.Id
	@Field(name="med_id")
	private int medid;
	
	@Field(name="medicine_name")
	private String medicinename;
	
	@Field(name="medicine_type")
	private String medicinetype;
	
	@Field(name="created_date")
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
