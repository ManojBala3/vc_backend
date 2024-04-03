package com.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Model.ErrorLogDetails;

public interface ErrorLogDao extends JpaRepository<ErrorLogDetails, Integer>{

}
