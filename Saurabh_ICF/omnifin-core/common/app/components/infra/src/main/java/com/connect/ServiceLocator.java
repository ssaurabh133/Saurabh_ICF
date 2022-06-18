package com.connect;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.exception.OmniFinMarkForRollbackException;

public class ServiceLocator {
	
	private static ServiceLocator singleInstance = null;
	
	private final static String LOCAL_LOOKUP_PREFIX = "java://";
	
	private ServiceLocator(){
	}
	
	public static synchronized ServiceLocator getInstance(){
		if(singleInstance==null){
			return new ServiceLocator();
		}else{
			return singleInstance;
		}
	}
	
	public Object getEJBLocalInstance(String ejbName){
		try {
			Context context = new InitialContext();
			Object ejbObject = context.lookup(LOCAL_LOOKUP_PREFIX+ejbName);
			return ejbObject;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OmniFinMarkForRollbackException(e);
		}
	}

}
