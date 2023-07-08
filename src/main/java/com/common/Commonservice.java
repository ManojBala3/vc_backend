package com.common;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Commonservice {
	
	
	public static String convertdateformat(Date date,String afterformat)
	{
		try
		{
			DateFormat dateFormat_after = new SimpleDateFormat(afterformat); 
			return dateFormat_after.format(date); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
			
		}
	}
	
	public static String HandleNULL(String data)
	{
		if(data!=null)
			return data;
		else
			return "";
	}
	
	public static String HandleNULL_NA(String data)
	{
		if(data!=null)
			return data;
		else
			return "";
	}
	
	 public static String IntToLetter(int Int) {
		    if (Int<27){
		      return Character.toString((char)(Int+96));
		    } else {
		      if (Int%26==0) {
		        return IntToLetter((Int/26)-1)+IntToLetter(((Int-1)%26+1));
		      } else {
		        return IntToLetter(Int/26)+IntToLetter(Int%26);
		      }
		    }
		  }
	

}
