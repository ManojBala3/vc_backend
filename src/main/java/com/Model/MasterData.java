package com.Model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//@Table(name="master")
@Document(collection = "master")
public class MasterData {

	@org.springframework.data.annotation.Id
	@Field(name="master_id")
	private int id;
	
	@Field(name="master_key")
	private String key;
	
	@Field(name="master_value")
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
