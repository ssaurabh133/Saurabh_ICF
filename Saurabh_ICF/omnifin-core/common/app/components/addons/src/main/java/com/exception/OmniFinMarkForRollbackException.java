package com.exception;

import java.sql.SQLException;

public class OmniFinMarkForRollbackException extends RuntimeException {
	
	private Exception rootException = null;

	public OmniFinMarkForRollbackException(SQLException exp) {
		rootException = exp;
	}
	
	public OmniFinMarkForRollbackException(Exception exp) {
		rootException = exp;
	}
	
	public Exception getRootException(){
		return rootException;
	}
	
}
