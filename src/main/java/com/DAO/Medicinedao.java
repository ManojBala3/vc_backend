package com.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Model.MedicineDetails;

public interface Medicinedao extends MongoRepository<MedicineDetails, Integer>
{
	@Query(value="select * from medicine_details u order by u.medicine_name LIMIT ?1 offset ?2")
	List<MedicineDetails> getupdaterecords(int limit,int offset);
	
	@Query(value="select * from medicine_details u where medicine_name like  ?1 order by u.medicine_name LIMIT  ?2 offset ?3")
	List<MedicineDetails> getcustDetailsMedicine(String medicine,int limit,int offset);

	@Query("select count(*) from MedicineDetails")
	int getrecordscount();
	
	@Query(value="select count(*) from medicine_details u where medicine_name like  ?1 ")
	int getcountbymedicine(String medicine);
	
	@Query(value="select * from medicine_details u where upper(medicine_name) like  upper(?1)")
	List<MedicineDetails> searchmedicine(String medicine);
}
