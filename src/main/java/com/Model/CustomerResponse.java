package com.Model;

import java.util.List;

public class CustomerResponse {

	List<CustomerDetails> response;
	List<MedicineDetails> medresponse;
	CustomerDetails custdata;
	Object common;
	MedicineDetails meddata;
	String respcode;
	String respdesc;
	String totalcount;
	
	public Object getCommon() {
		return common;
	}
	public void setCommon(Object common) {
		this.common = common;
	}
	public List<MedicineDetails> getMedresponse() {
		return medresponse;
	}
	public void setMedresponse(List<MedicineDetails> medresponse) {
		this.medresponse = medresponse;
	}
	public MedicineDetails getMeddata() {
		return meddata;
	}
	public void setMeddata(MedicineDetails meddata) {
		this.meddata = meddata;
	}
	public CustomerDetails getCustdata() {
		return custdata;
	}
	public void setCustdata(CustomerDetails custdata) {
		this.custdata = custdata;
	}
	public List<CustomerDetails> getResponse() {
		return response;
	}
	public void setResponse(List<CustomerDetails> response) {
		this.response = response;
	}
	public String getRespcode() {
		return respcode;
	}
	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}
	public String getRespdesc() {
		return respdesc;
	}
	public void setRespdesc(String respdesc) {
		this.respdesc = respdesc;
	}
	public String getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	
	
}
