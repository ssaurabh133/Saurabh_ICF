package com.connect;
/** 
 * Devloped By . A S Software Solutions Pvt. Ltd.
 * The program(s) herein may be used and/or copied only with
 * the written permission of A S Software Solutions Pvt. Ltd.
 * or in accordance with the terms and conditions stipulated 
 * in the agreement/contract under which the program(s) have 
 * been supplied.
 * 
 * @author Administrator
 * 
 * May 25, 2010  ,  5:00:44 PM  ,  2010 
 * 
 * @see
 *
 */
import java.security.MessageDigest;

import org.apache.log4j.Logger;


public class md5 {
    
	private static final Logger logger = Logger.getLogger(md5.class.getName());
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 public static String en(String pass)throws Exception
	    {
	    	logger.info(md5.class.getName()+" en ");
	    	logger.info("pass--- in en-----"+pass);
	    	MessageDigest m = MessageDigest.getInstance("MD5");
	    	
		    m.update(pass.getBytes("UTF8"));
		    byte s[] = m.digest();
		   // String resultMD = "";
		    String result ="";
		    for (int i = 0; i < s.length; i++) {
		    	result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
			//result=sha256(resultMD);
		    }
		    logger.info("result--- in en-----"+result);
		    return result;
	    }
		
	        public static String sha256(String pass) throws Exception
	        {
	        	logger.info(md5.class.getName()+"  sha256   ");
	        	logger.info("result--- sha256-- pass : "+pass);
	        	String password = pass;
	            String result= null;
	            logger.info("result--- sha256-- password : "+password);
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            md.update(password.getBytes());
	     
	            byte byteData[] = md.digest();
	     
	            //convert the byte to hex format method 1
	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < byteData.length; i++) {
	             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	            }
	     
	            logger.info("result--- sha256 : " + sb.toString());
	            result=sb.toString();
	            //convert the byte to hex format method 2
	            /*StringBuffer hexString = new StringBuffer();
	        	for (int i=0;i<byteData.length;i++) {
	        		String hex=Integer.toHexString(0xff & byteData[i]);
	       	     	if(hex.length()==1) hexString.append('0');
	       	     	hexString.append(hex);
	        	}
	        	System.out.println("Hex format : " + hexString.toString());*/
	        	return result;
	        }
	        
	       
	        public static String randomAlphaNumeric(int count) {
	        	
	            	StringBuilder builder = new StringBuilder();
	        		while (count-- != 0) {
	        			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	        			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	                   
	        		}
	                 
	                String salt=builder.toString();
	                //logger.info("Random salt ================ "+salt);
	                 
	        		return salt;
	        }
	
}
