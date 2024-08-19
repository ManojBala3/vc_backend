package com.DAO;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.Model.ErrorLogDetails;

public interface ErrorLogDao extends MongoRepository<ErrorLogDetails, Integer>{

}
