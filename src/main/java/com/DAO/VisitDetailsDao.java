package com.DAO;

import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Model.VisitDetails;

public interface VisitDetailsDao extends MongoRepository<VisitDetails, Integer>{
	
	@Query(value="SELECT MAX(queue_no) FROM visit_details where date(visit_date)=current_date ")
	String getqueueno();
	
	@Query(value="select vd.queue_no,vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%m:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id and vd.queue_status='Waiting' and date(vd.visit_date)=current_date order by vd.queue_no  LIMIT ?1 offset ?2")
	List<Map<String,String>> getqueuelist(int limit,int offset);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where vd.queue_status='Waiting' and date(vd.visit_date)=current_date")
	int getTotalQueueVisits();
	
	@Query(value="select vd.queue_no,vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id  where cd.mobile_no like ?1 and  vd.queue_status='Waiting' and date(vd.visit_date)=current_date order by vd.queue_no  LIMIT ?2 offset ?3")
	List<Map<String,String>> getvisitdetailsbynumber(String mobileno,int limit,int offset);
	
	@Query(value="select vd.queue_no,vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id  where cd.customer_id like ?1 and vd.queue_status='Waiting' and date(vd.visit_date)=current_date order by vd.queue_no  LIMIT ?2 offset ?3")
	List<Map<String,String>> getvisitdetailsbycustid(String mobileno,int limit,int offset);
	
	@Query(value="select vd.queue_no,vd.visit_id,cd.customer_name,cd.mobile_no,DATE_FORMAT(vd.visit_date,'%d/%m/%Y %H:%M:%s') as visitdate,vd.age_year,vd.age_month,vd.age_week,vd.age_day,cd.Gender as gender,cd.customer_id as custid from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_name like ?1 and vd.queue_status='Waiting' and date(vd.visit_date)=current_date order by vd.queue_no  LIMIT ?2 offset ?3")
	List<Map<String,String>> getvisitdetailsbyname(String name,int limit,int offset);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.mobile_no and vd.queue_status='Waiting' and date(vd.visit_date)=current_date like ?1")
	int countbynumber(String mobileno);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_id like ?1 and vd.queue_status='Waiting' and date(vd.visit_date)=current_date")
	int countbyid(String custid);
	
	@Query(value="select count(*) from visit_details vd inner join customer_details cd on vd.cust_id=cd.cust_id where cd.customer_name like ?1 and vd.queue_status='Waiting' and date(vd.visit_date)=current_date")
	int countbyname(String name);

}
