package com.communication.engn.sms.utility;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.comm.CommPortIdentifier;

public class Main {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		
		
		SMSClient sms=new SMSClient(0);
	int status=	sms.sendMessage("9810548019", "hi ");
		System.out.println("status "+status);

	}

}
