package com.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Model.PrescriptionDetails;

public interface PrescriptionDao extends JpaRepository<PrescriptionDetails, Integer>
{
	@Query(value="select vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%m:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id and vd.queue_status='Completed' order by vd.visit_date desc LIMIT ?1 offset ?2",nativeQuery = true)
	List<Map<String,String>> getvisitdetails(int limit,int offset);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where vd.queue_status='Completed'",nativeQuery = true)
	int getTotalVisits();
	
	@Query(value="select vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id  where cd.mobile_no like ?1 and  vd.queue_status='Completed' order by vd.visit_date desc LIMIT ?2 offset ?3",nativeQuery = true)
	List<Map<String,String>> getvisitdetailsbynumber(String mobileno,int limit,int offset);
	
	@Query(value="select vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id  where cd.customer_id like ?1 and vd.queue_status='Completed' order by vd.visit_date desc LIMIT ?2 offset ?3",nativeQuery = true)
	List<Map<String,String>> getvisitdetailsbycustid(String mobileno,int limit,int offset);
	
	@Query(value="select vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_name like ?1 and vd.queue_status='Completed' order by vd.visit_date desc LIMIT ?2 offset ?3",nativeQuery = true)
	List<Map<String,String>> getvisitdetailsbyname(String name,int limit,int offset);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.mobile_no and vd.queue_status='Completed' like ?1",nativeQuery = true)
	int countbynumber(String mobileno);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_id like ?1 and vd.queue_status='Completed'",nativeQuery = true)
	int countbyid(String custid);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_name like ?1 and vd.queue_status='Completed'",nativeQuery = true)
	int countbyname(String name);
	
	@Query(value="delete from prescription_details where visit_id= ?1",nativeQuery = true)
	void deleteprescription(int id);
	
	@Query("Select u from PrescriptionDetails  u where visitid= ?1")
	ArrayList<PrescriptionDetails> getprescrption(int visitid);
}
