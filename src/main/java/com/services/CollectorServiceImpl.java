package com.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.DAO.CustomerDetailsDao;
import com.DAO.ErrorLogDao;
import com.Model.CustomerDetails;
import com.Model.CustomerResponse;
import com.Model.ErrorLogDetails;
import com.Model.MasterData;
import com.common.Commonservice;

@Service
public class CollectorServiceImpl implements CollectorService
{
	static final Logger logger = LoggerFactory.getLogger(CollectorServiceImpl.class);
	
	private int startyear=2022;
	
	@Autowired
	private CustomerDetailsDao customerDao;
	
	@Autowired
	private ErrorLogDao errordao;
	
	@Override
	public List<CustomerDetails> getallrecords(String limit,String offset) {
		return customerDao.getupdaterecords(Integer.parseInt(limit),Integer.parseInt(offset));
	}

	@Override
	public List<CustomerDetails> getcustdetails(String searchvalue,String searchtype,String limit,String offset) 
	{
		List<CustomerDetails> custlist=null;
		try
		{
			if(searchtype.equalsIgnoreCase("mobile"))
			{
				searchvalue=("%").concat(searchvalue.concat("%"));
				custlist=customerDao.getcustDetailsMobile(searchvalue,Integer.parseInt(limit),Integer.parseInt(offset));
			}
			else if(searchtype.equalsIgnoreCase("name"))
			{
				searchvalue=("%").concat(searchvalue.concat("%"));
				custlist=customerDao.getcustDetailsName(searchvalue,Integer.parseInt(limit),Integer.parseInt(offset));
			}
			else if(searchtype.equalsIgnoreCase("patientid"))
			{
				searchvalue=("%").concat(searchvalue.concat("%"));
				custlist=customerDao.getcustDetailscustid(searchvalue,Integer.parseInt(limit),Integer.parseInt(offset));
			}
			if(custlist!=null && custlist.size()>0)
			{
				for(CustomerDetails custdata:custlist)
				{
					if(custdata!=null && custdata.getDob()!=null)
					{
						custdata.setAge(calculateAge(custdata.getDob())+"");
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			inserterrorlog(new ErrorLogDetails("getcustdetails",e.getLocalizedMessage(),searchtype.concat(searchvalue)));
		}
		return custlist;
	}

	@Override
	public int getcount() {
		return customerDao.getrecordscount();
		
	}

	@Override
	public CustomerResponse savecustomer(CustomerDetails request) 
	{
		CustomerResponse response=new CustomerResponse();
		try
		{
			if(request.getMobileno()!=null && !request.getMobileno().equals(""))
			{
				int count= customerDao.getcustDetailsMobilecount(request.getMobileno().trim());
				if(count>0)
				{
					response.setRespcode("01");
					response.setRespdesc("Customer with "+request.getMobileno().trim() +" already exists");
				}
				else
				{
					request.setCustomerid(generatecustid());
					customerDao.save(request);
					response.setRespcode("00");
					response.setRespdesc("Success");
				}
			}
			else
			{
				request.setCustomerid(generatecustid());
				customerDao.save(request);
				response.setRespcode("00");
				response.setRespdesc("Success");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("savecustomer",e.getLocalizedMessage(),Commonservice.printresp(request)));
		}
		return response;
	}

	@Override
	public Optional<CustomerDetails> getbycustid(int id) {
		return customerDao.findById(id);
	}

	@Override
	public CustomerResponse updatecust(CustomerDetails request) {
		
		CustomerResponse response=new CustomerResponse();
		try
		{
			Optional<CustomerDetails> old=customerDao.findById(request.getCustid());
			if(old!=null && old.isPresent())
			{
				int custcount=customerDao.checkmobilenocustid(request.getMobileno(), request.getCustid());
				if(custcount==0)
				{
					CustomerDetails olddata=old.get();
					olddata.setCustname(request.getCustname());
					olddata.setDistrict(request.getDistrict());
					olddata.setDob(request.getDob());
					olddata.setEmailid(request.getEmailid());
					olddata.setMobileno(request.getMobileno());
					olddata.setState(request.getState());
					customerDao.save(olddata);
					response.setRespcode("00");
					response.setRespdesc("Success");
				}
				else
				{
					response.setRespcode("01");
					response.setRespdesc("Customer with "+request.getMobileno().trim() +" already exists");
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("FF");
			response.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("updatecust",e.getLocalizedMessage(),Commonservice.printresp(request)));
		}
		return response;
	}

	@Override
	public CustomerResponse deletecust(String custid)
	{
		CustomerResponse response =new CustomerResponse();
		try
		{
			customerDao.deleteById(Integer.parseInt(custid));
			response.setRespcode("00");
			response.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some error occured");
			inserterrorlog(new ErrorLogDetails("updatecust",e.getLocalizedMessage(),custid));
		}
		return response;
	}
	
	public int calculateAge(Date birthDate)
	{
		LocalDate bd=convertToLocalDateViaSqlDate(birthDate);
		return Period.between(bd, LocalDate.now()).getYears();
	}
	
	public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
	
	public String generatecustid()
	{
		String custid="";
		try
		{
			int currentseq=1;
			boolean resettriggered=false;
			LocalDate currentDate=LocalDate.now();
			int month=currentDate.getMonthValue();
			int day=currentDate.getDayOfMonth();
			int year=currentDate.getYear();
			int diffyear=year-startyear;
			Month month_m = currentDate.getMonth();
			List<MasterData> map=customerDao.getmasterdata();
			custid=custid.concat(String.format("%02d", day)).concat(String.format("%02d", month));
			if(map!=null && map.size()>0)
			{
				for(MasterData data:map)
				{
					if(data.getKey().equalsIgnoreCase("customer_id_seq_month"))
					{
						logger.info("checking for reset customer id");
						if(!data.getValue().equalsIgnoreCase(month_m.name()))
						{
							customerDao.updatemaster("customer_id_seq_month", month_m.name());
							customerDao.updatemaster("customer_id_seq", "0");
							resettriggered=true;
						}
					}
					else if(data.getKey().equalsIgnoreCase("customer_id_seq"))
					{
						if(resettriggered)
						{
							currentseq=currentseq+1;
							customerDao.updatemaster("customer_id_seq", "1");
							return custid.concat("01").concat(Commonservice.IntToLetter(diffyear));
						}
						else
						{	currentseq=Integer.parseInt(data.getValue())+1;
							customerDao.updatemaster("customer_id_seq", currentseq+"");
							return custid.concat(String.format("%02d", currentseq)).concat((Commonservice.IntToLetter(diffyear+1)).toUpperCase());
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage());
			inserterrorlog(new ErrorLogDetails("generatecustid",e.getLocalizedMessage(),""));
		}
		return custid;
	}

	public void inserterrorlog(ErrorLogDetails errorlog)
	 {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		errorlog.setCreateddate(timestamp);
		errordao.save(errorlog);
	 }

	
}
