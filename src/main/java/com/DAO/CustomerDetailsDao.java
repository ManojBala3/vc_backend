package com.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.Model.CustomerDetails;
import com.Model.MasterData;

public interface CustomerDetailsDao extends JpaRepository<CustomerDetails, Integer>
{
	@Query(value="select * from customer_details u order by u.created_date desc LIMIT ?1 offset ?2",nativeQuery = true)
	List<CustomerDetails> getupdaterecords(int limit,int offset);
	
	@Query(value="select * from customer_details u where mobile_no like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3",nativeQuery = true)
	List<CustomerDetails> getcustDetailsMobile(String mobileno,int limit,int offset);
	
	@Query(value="select * from customer_details u where customer_name like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3",nativeQuery = true)
	List<CustomerDetails> getcustDetailsName(String name,int limit,int offset);
	
	@Query(value="select * from customer_details u where customer_id like  ?1 order by u.created_date desc LIMIT  ?2 offset ?3",nativeQuery = true)
	List<CustomerDetails> getcustDetailscustid(String custid,int limit,int offset);

	@Query("select count(*) from CustomerDetails u ")
	int getrecordscount();
	
	@Query(value="select count(*) from customer_details u where mobile_no like ?1",nativeQuery = true)
	int getcustDetailsMobilecount(String mobileno);
	
	@Query(value="select count(*) from customer_details u where customer_name like ?1",nativeQuery = true)
	int getcustDetailsname(String name);
	
	@Query(value="select count(*) from customer_details u where customer_id like ?1",nativeQuery = true)
	int getcustDetailsid(String custid);
	
	@Query(value="select count(*) from customer_details u where mobile_no=?1 and cust_id!=?2",nativeQuery = true)
	int checkmobilenocustid(String mobileno,int custid);
	
	@Query(value="select * from customer_details u where mobile_no like  ?1",nativeQuery = true)
	CustomerDetails getcustwithmobile(String mobileno);
	
	@Query("select u from  MasterData u")
	List<MasterData> getmasterdata();
	
	@Modifying
	@Transactional
	@Query(value="update master set master_value=?2 where master_key=?1",nativeQuery = true)
	void updatemaster(String key,String value);
	
}
