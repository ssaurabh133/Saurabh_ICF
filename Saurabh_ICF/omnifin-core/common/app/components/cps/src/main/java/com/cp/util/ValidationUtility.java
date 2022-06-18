package com.cp.util;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;

public class ValidationUtility {
	private static final Logger logger = Logger.getLogger(ValidationUtility.class.getName());
	public static boolean isNumeric(String value)
	{
		
		value = removeComma(value.trim());
		if(value.trim().equalsIgnoreCase("-") || value.trim().endsWith("."))
			return true; 
		return value.matches("-?\\d+(\\.\\d+)?");
	}
	
	
	public static String removeComma(String value)
	{
		
		if(CommonFunction.checkNull(value.trim()).length()>0)
		{
			value = value.replace(",", "");
			value = value.replace(" ", "");
		}
		return value;
	}
	
}
