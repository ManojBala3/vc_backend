package com.DAO;

import java.util.List;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.Model.CustomerDetails;
import com.Model.MasterData;

public interface CustomerDetailsDao extends MongoRepository<CustomerDetails, Integer>
{
	@Query(value="select * from customer_details u order by u.created_date desc LIMIT ?1 offset ?2")
	List<CustomerDetails> getupdaterecords(int limit,int offset);
	
	@Query(value="select * from customer_details u where mobile_no like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3")
	List<CustomerDetails> getcustDetailsMobile(String mobileno,int limit,int offset);
	
	@Query(value="select * from customer_details u where customer_name like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3")
	List<CustomerDetails> getcustDetailsName(String name,int limit,int offset);
	
	@Query(value="select * from customer_details u where customer_id like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3")
	List<CustomerDetails> getcustDetailscustid(String custid,int limit,int offset);

	@Query("select count(*) from CustomerDetails u ")
	int getrecordscount();
	
	@Query(value="select count(*) from customer_details u where mobile_no like ?1")
	int getcustDetailsMobilecount(String mobileno);
	
	@Query(value="select count(*) from customer_details u where customer_name like ?1")
	int getcustDetailsname(String name);
	
	@Query(value="select count(*) from customer_details u where customer_id like ?1")
	int getcustDetailsid(String custid);
	
	@Query(value="select count(*) from customer_details u where mobile_no=?1 and cust_id!=?2")
	int checkmobilenocustid(String mobileno,int custid);
	
	@Query(value="select * from customer_details u where mobile_no like  ?1")
	CustomerDetails getcustwithmobile(String mobileno);
	
	@Query("select u from  MasterData u")
	List<MasterData> getmasterdata();
	
	@Transactional
	@Query(value="update master set master_value=?2 where master_key=?1")
	void updatemaster(String key,String value);
	
}
