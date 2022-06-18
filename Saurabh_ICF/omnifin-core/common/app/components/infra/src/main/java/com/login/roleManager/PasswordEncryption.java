package com.login.roleManager;

import com.logger.LoggerMsg;

public class PasswordEncryption {
	
	public static String passwordEncryptionManager(String userPassword) {
		String encryptPassWord = null;
		try {
			encryptPassWord = byteArrayToHexString(computeHash(userPassword));
		} catch (Exception e) {
			LoggerMsg.debug(e.getMessage());
			
		}

		return encryptPassWord;
	}
	
	private static byte[] computeHash(String x)   
	  throws Exception  
	  {
	     java.security.MessageDigest d =null;
	     d = java.security.MessageDigest.getInstance("SHA-1");
	     d.reset();
	     d.update(x.getBytes());
	     return  d.digest();
	  }
	  
	  private static String byteArrayToHexString(byte[] b){
	     StringBuffer sb = new StringBuffer(b.length * 2);
	     for (int i = 0; i < b.length; i++){
	       int v = b[i] & 0xff;
	       if (v < 16) {
	         sb.append('0');
	       }
	       sb.append(Integer.toHexString(v));
	     }
	     return sb.toString().toUpperCase();
	  }
}
