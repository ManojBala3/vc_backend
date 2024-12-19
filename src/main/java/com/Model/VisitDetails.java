package com.Model;

import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
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

	@Column(name="updated_date")
	private Timestamp updatedate;
	
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
	private double height;
	
	@Column(name="Weight")
	private double weight;
	
	@Column(name="HC")
	private double hc;
	
	@Column(name="diagnosis")
	private String diagnosis;
	
	@Column(name="queue_status")
	private String queuestatus="Completed";

    @Column(name="queue_no")
	private int queueno;
}
