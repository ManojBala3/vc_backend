package com.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Model.MedicineDetails;

public interface Medicinedao extends JpaRepository<MedicineDetails, Integer>
{
	@Query(value="select * from medicine_details u order by u.medicine_name LIMIT ?1 offset ?2",nativeQuery = true)
	List<MedicineDetails> getupdaterecords(int limit,int offset);
	
	@Query(value="select * from medicine_details u where medicine_name like  ?1 order by u.medicine_name LIMIT  ?2 offset ?3",nativeQuery = true)
	List<MedicineDetails> getcustDetailsMedicine(String medicine,int limit,int offset);

	@Query("select count(*) from MedicineDetails")
	int getrecordscount();
	
	@Query(value="select count(*) from medicine_details u where medicine_name like  ?1 ",nativeQuery = true)
	int getcountbymedicine(String medicine);
	
	@Query(value="select * from medicine_details u where upper(medicine_name) like  upper(?1)",nativeQuery = true)
	List<MedicineDetails> searchmedicine(String medicine);
}
