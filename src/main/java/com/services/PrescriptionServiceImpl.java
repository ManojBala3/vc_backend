package com.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.CustomerDetailsDao;
import com.DAO.ErrorLogDao;
import com.DAO.PrescriptionDao;
import com.DAO.VisitDetailsDao;
import com.Model.AddPrescriptionRequest;
import com.Model.CustomerDetails;
import com.Model.CustomerRequest;
import com.Model.CustomerResponse;
import com.Model.ErrorLogDetails;
import com.Model.PrescriptionDetails;
import com.Model.VisitDetails;
import com.common.Commonservice;

@Service
public class PrescriptionServiceImpl implements Prescriptionservice
{
	static final org.slf4j.Logger logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);
	
	@Autowired
	private PrescriptionDao prescriptiondao;
	
	@Autowired
	private CustomerDetailsDao customerDao;
	
	@Autowired
	private VisitDetailsDao visitdao;
	
	@Autowired
	private ErrorLogDao errordao;
	
	@Autowired
	private CollectorService collectorservice;

	@Override
	public CustomerResponse getallvistdtls(String limit, String offset) 
	{
		CustomerResponse custresponse=new CustomerResponse();
		List<Map<String,String>> vresp=prescriptiondao.getvisitdetails(Integer.parseInt(limit), Integer.parseInt(offset));
		if(vresp!=null && vresp.size()>0)
		{
			custresponse.setCommon(vresp);
			custresponse.setRespcode("00");
			custresponse.setRespdesc("Success");
			custresponse.setTotalcount(prescriptiondao.getTotalVisits()+"");
		}
		else
		{
			custresponse.setRespcode("01");
			custresponse.setRespdesc("No Records Found");
		}
		return custresponse;
	}

	@Override
	public CustomerResponse saveprescription(AddPrescriptionRequest request) {
		CustomerResponse response=new CustomerResponse();
		CustomerDetails custdata=null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		try
		{
			if(request.getCustid()!=null && !Objects.equals(request.getCustid(), ""))
			{
				logger.info("Exisiting customer");
				Optional<CustomerDetails> dcustdata=customerDao.findById(Integer.parseInt(request.getCustid()));
				custdata=dcustdata.get();
			}
			else
			{
				logger.info("New customer");
				custdata=customerDao.getcustwithmobile(request.getCustomermobile());
				if(custdata==null)
				{
					custdata=new CustomerDetails();
					custdata.setCreateddate(timestamp);
					if (request.getOldPatientid() != null && !request.getOldPatientid().isEmpty()) {
					    if (request.getOldPatientid().matches("\\d{6}[A-Za-z]")) {
					        custdata.setCustomerid(request.getOldPatientid());
					    } else {
					    	System.out.println("Invalid old patient ID format. It must be 6 digits followed by 1 letter.");
					    	throw new Exception("Invalid old patient ID format. It must be 6 digits followed by 1 letter.");
					    }
					} else {
						custdata.setCustomerid(collectorservice.generatecustid());
					    System.out.println("Old patient ID is null or empty. So, Generating new patientID");
					}
				}
			}
			custdata.setCustname(request.getCustomername());
			if(request.getCustomergender()!=null)
				custdata.setGender(request.getCustomergender());
			if(request.getCustomeremail()!=null)
				custdata.setEmailid(request.getCustomeremail());
			if(request.getCustomermobile()!=null)
				custdata.setMobileno(request.getCustomermobile());
			custdata=customerDao.save(custdata);
			VisitDetails visitdetails=new VisitDetails();
			if(request.isAddqueue())
			{
				visitdetails.setQueuestatus("Waiting");
				visitdetails.setQueueno(validatequeueno(visitdao.getqueueno()+""));
			}
			visitdetails.setCustid(custdata.getCustid());
			visitdetails.setAbc(Commonservice.HandleNULL(request.getAbc()));
			visitdetails.setVitals(Commonservice.HandleNULL(request.getVitals()));
			visitdetails.setEnt(Commonservice.HandleNULL(request.getEnt()));
			visitdetails.setSe(Commonservice.HandleNULL(request.getSe()));
			visitdetails.setHeight(request.getHeight());
			visitdetails.setWeight(request.getWeight());
			visitdetails.setHc(request.getHc());
			visitdetails.setAgeyear(request.getCustomerageyear());
			visitdetails.setAgemonth(request.getCustomeragemonth());
			visitdetails.setAgeweek(request.getCustomerageweek());
			visitdetails.setAgeday(request.getCustomerageday());
			visitdetails.setDiagnosis(Commonservice.HandleNULL(request.getAdditionalnote()));
			visitdetails.setVisitdate(timestamp);
			visitdetails.setNextvisitdate(request.getNextreview());
			visitdetails.setAdvice(Commonservice.HandleNULL(request.getComments()));
			visitdetails=visitdao.save(visitdetails); 
			if(request.getProducts()!=null && !request.getProducts().isEmpty())
			{
				ArrayList<PrescriptionDetails> products=request.getProducts();
				for(PrescriptionDetails pd:products)
				{
					pd.setCreateddate(timestamp);
					pd.setVisitid(visitdetails.getVisitid());
				}
				prescriptiondao.saveAll(products);
			}
			
			response.setRespcode("00");
			response.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("saveprescription - Visit",e.getLocalizedMessage(),Commonservice.printresp(request)));
		}
		return response;
	}
	
	private int validatequeueno(String queueno)
	{
		if(queueno!=null && !queueno.equals("0") && !queueno.equalsIgnoreCase("null"))
			return (Integer.parseInt(queueno))+1;
		else
			return 1;
	}

	@Override
	public CustomerResponse searchcust(CustomerRequest request, String limit, String offset) 
	{
		CustomerResponse custresponse=new CustomerResponse();
		List<Map<String,String>> obj=null;
		String searchvalue="";
		try
		{
			if(request.getName()!=null)
			{
				searchvalue=("%").concat(request.getName().concat("%"));
				obj=prescriptiondao.getvisitdetailsbyname(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(prescriptiondao.countbyname(searchvalue)+""); 
			}
			else if(request.getMobilenumber()!=null)
			{
				searchvalue=("%").concat(request.getMobilenumber().concat("%"));
				obj=prescriptiondao.getvisitdetailsbynumber(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(prescriptiondao.countbynumber(searchvalue)+""); 
			}
			else if(request.getCustid()!=null)
			{
				searchvalue=("%").concat(request.getCustid().concat("%"));
				obj=prescriptiondao.getvisitdetailsbycustid(searchvalue,Integer.parseInt(limit), Integer.parseInt(offset));
				custresponse.setTotalcount(prescriptiondao.countbyid(searchvalue)+""); 
			}
			if(obj!=null && obj.size()>0) 
			{ 
				custresponse.setRespcode("00");
				custresponse.setRespdesc("Success");
				custresponse.setCommon(obj);
			}
			else
			{ 
				custresponse.setRespcode("01");
				custresponse.setRespdesc("No Records Found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			custresponse.setRespcode("01");
			custresponse.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("searchcust - Visit",e.getLocalizedMessage(),Commonservice.printresp(request)));
		}
		return custresponse;
	}

	@Override
	public CustomerResponse deletevisit(String id)
	{
		CustomerResponse custresponse=new CustomerResponse();
		try
		{
			visitdao.deleteById(Integer.parseInt(id));
			prescriptiondao.deleteById(Integer.parseInt(id));
			custresponse.setRespcode("00");
			custresponse.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			custresponse.setRespcode("01");
			custresponse.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("Delete Visit",e.getLocalizedMessage(),id));
		}
		
		return custresponse;
	}

	@Override
	public CustomerResponse getvisitdata(String id)
	{
		CustomerResponse custresponse =new CustomerResponse();
		AddPrescriptionRequest presp=new AddPrescriptionRequest();
		try
		{
			Optional<VisitDetails> visitdtls= visitdao.findById(Integer.parseInt(id));
			if(visitdtls!=null && visitdtls.isPresent())
			{
				VisitDetails vdtls=visitdtls.get();
				presp.setVisitid(id);
				presp.setAbc(vdtls.getAbc());
				presp.setEnt(vdtls.getEnt());
				presp.setHc(vdtls.getHc());
				presp.setSe(vdtls.getSe());
				presp.setHeight(vdtls.getHeight());
				presp.setWeight(vdtls.getWeight());
				presp.setVitals(vdtls.getVitals());
				presp.setAdditionalnote(vdtls.getDiagnosis());
				presp.setVisitdate(vdtls.getVisitdate());
				presp.setNextreview(vdtls.getNextvisitdate());
				presp.setComments(vdtls.getAdvice());
				
				//customer info
				Optional<CustomerDetails> custdtls=customerDao.findById(vdtls.getCustid());
				if(custdtls!=null && custdtls.isPresent())
				{
					CustomerDetails custdata=custdtls.get();
					presp.setCustomername(custdata.getCustname());
					presp.setCustomerageyear(vdtls.getAgeyear());
					presp.setCustomeragemonth(vdtls.getAgemonth());
					presp.setCustomerageweek(vdtls.getAgeweek());
					presp.setCustomerageday(vdtls.getAgeday());
					presp.setCustomergender(custdata.getGender());
					presp.setCustomeremail(custdata.getEmailid());
					presp.setCustomermobile(custdata.getMobileno());
					presp.setPatientid(custdata.getCustomerid());
				}
				
				//prescption details
				ArrayList<PrescriptionDetails> pdtls=prescriptiondao.getprescrption(vdtls.getVisitid());
				if(pdtls!=null && pdtls.size()>0)
				{
					presp.setProducts(pdtls);
				}
				custresponse.setCommon(presp);
				custresponse.setRespcode("00");
				custresponse.setRespdesc("Success");
			}
			else
			{
				custresponse.setRespcode("01");
				custresponse.setRespdesc("No Records Found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			custresponse.setRespcode("01");
			custresponse.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("getvisitdata",e.getLocalizedMessage(),id));
		}
		return custresponse;	
	}
	
	public CustomerResponse updatevisitdetails(AddPrescriptionRequest request)
	{
		CustomerResponse response=new CustomerResponse();
		CustomerDetails custdata=null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(request.toString());
		try
		{
			Optional<VisitDetails> visitdtls= visitdao.findById(Integer.parseInt(request.getVisitid()));
			if(visitdtls!=null && visitdtls.isPresent())
			{
				VisitDetails visitdetails=visitdtls.get();
				visitdetails.setAbc(request.getAbc());
				visitdetails.setVitals(request.getVitals());
				visitdetails.setEnt(request.getEnt());
				visitdetails.setSe(request.getSe());
				visitdetails.setHeight(request.getHeight());
				visitdetails.setWeight(request.getWeight());
				visitdetails.setHc(request.getHc());
				visitdetails.setDiagnosis(request.getAdditionalnote());
				visitdetails.setNextvisitdate(request.getNextreview());
				visitdetails.setAgeyear(request.getCustomerageyear());
				visitdetails.setAgemonth(request.getCustomeragemonth());
				visitdetails.setAgeweek(request.getCustomerageweek());
				visitdetails.setAgeday(request.getCustomerageday());
				visitdetails.setAdvice(request.getComments());
				if(request.isAddqueue())
					visitdetails.setQueuestatus("Waiting");
				else		
					visitdetails.setQueuestatus("Completed");
				visitdao.save(visitdetails);
			}
			custdata=customerDao.getcustwithmobile(request.getCustomermobile());
			if(custdata!=null)
			{
				custdata.setCustname(request.getCustomername());
				if(request.getCustomergender()!=null)
					custdata.setGender(request.getCustomergender());
				if(request.getCustomeremail()!=null)
					custdata.setEmailid(request.getCustomeremail());
				if(request.getCustomermobile()!=null)
					custdata.setMobileno(request.getCustomermobile());
				custdata=customerDao.save(custdata);
			}
			
			ArrayList<PrescriptionDetails> pdtls=prescriptiondao.getprescrption(Integer.parseInt(request.getVisitid()));
			if(pdtls!=null && pdtls.size()>0)
			{
				for(PrescriptionDetails userdata:request.getProducts())
				{
					boolean deletedata=true;
					if(request.getProducts()!=null && request.getProducts().size()>0)
					{
						boolean data_avaliable=false;
						for(PrescriptionDetails dbdata:pdtls)
						{
							if(userdata.getPid()==dbdata.getPid())
							{
								deletedata=false;
								dbdata.setDrugname(userdata.getDrugname());
								dbdata.setMedtype(userdata.getMedtype());
								dbdata.setMorning(userdata.getMorning());
								dbdata.setNoon(userdata.getNoon());
								dbdata.setEvening(userdata.getEvening());
								dbdata.setNight(userdata.getNight());
								dbdata.setAfterfood(userdata.getAfterfood());
								dbdata.setBeforefood(userdata.getBeforefood());
								dbdata.setDuration(userdata.getDuration());
								dbdata.setAddinfo(userdata.getAddinfo());
								prescriptiondao.save(dbdata);
								data_avaliable=true;
							}
						}
						if(!data_avaliable)
						{
							deletedata=false;
							userdata.setVisitid(Integer.parseInt(request.getVisitid()));
							userdata.setCreateddate(timestamp);
							prescriptiondao.save(userdata);
						}
					}
				}
				//for delete function
					for(PrescriptionDetails dbdata:pdtls)
					{
						boolean deleteflag=true;
						for(PrescriptionDetails userdata:request.getProducts())
						{
							if(dbdata.getPid()==userdata.getPid())
							{
								deleteflag=false;
								continue;
							}
						}
						if(deleteflag)
							prescriptiondao.deleteById(dbdata.getPid());
					}
			}
			response.setRespcode("00");
			response.setRespdesc("Success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setRespcode("01");
			response.setRespdesc("Some Error Occured");
			inserterrorlog(new ErrorLogDetails("updatevisitdetails",e.getLocalizedMessage(),Commonservice.printresp(request)));
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
	
	public void inserterrorlog(ErrorLogDetails errorlog)
	 {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		errorlog.setCreateddate(timestamp);
		errordao.save(errorlog);
	 }
}
