package com.common;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Commonservice {
	
	  private static final int ITERATIONS = 65536;
	  private static final int KEY_LENGTH = 512;
	  private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	
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
	 
	 public static String printresp(Object obj)
		{
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				return ow.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}
	 
	 public static byte[] hashPassword (String chars, byte[] bytes) {


		    PBEKeySpec spec = new PBEKeySpec(chars.toCharArray(), bytes, ITERATIONS, KEY_LENGTH);

		    try {
		      SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
		      byte[] securePassword = fac.generateSecret(spec).getEncoded();
		      return securePassword;

		    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
		      System.err.println("Exception encountered in hashPassword()");
		      return null;

		    } finally {
		      spec.clearPassword();
		    }
		  }
	 
	 public static byte[] generateSalt (final int length)
	 {
		 SecureRandom RAND = new SecureRandom();
		    if (length < 1) {
		      System.err.println("error in generateSalt: length must be > 0");
		      return null;
		    }

		    byte[] salt = new byte[length];
		    RAND.nextBytes(salt);

		    return salt;
		  }
	
}
