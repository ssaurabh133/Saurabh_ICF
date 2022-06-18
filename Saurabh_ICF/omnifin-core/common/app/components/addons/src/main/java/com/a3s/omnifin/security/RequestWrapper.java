package com.a3s.omnifin.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.log4j.Logger;

public final class RequestWrapper extends HttpServletRequestWrapper
{
   private static final Logger logger = Logger.getLogger(RequestWrapper.class.getName());

  
    public RequestWrapper(HttpServletRequest servletRequest)
    {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter)
    {
        String values[] = super.getParameterValues(parameter);
        if(values == null)
            return null;
        int count = values.length;
        String encodedValues[] = new String[count];
        for(int i = 0; i < count; i++)
            encodedValues[i] = cleanXSS(values[i]);

        return encodedValues;
    }

    public String getParameter(String parameter)
    {
       
        String value = super.getParameter(parameter);
       
        if(value == null)
            return null;
        else
            return cleanXSS(value);
    }

    public String getHeader(String name)
    {
        String value = super.getHeader(name);
        if(value == null)
            return null;
        else
            return cleanXSS(value);
    }

    private String cleanXSS(String value)
    {
      
        int maliciousElement1 = value.indexOf("<");
        int maliciousElement12 = value.indexOf(">");
        int maliciousElement13 = value.indexOf("alert");

      
            value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
            value = value.replaceAll("alert", "");
            value = value.replaceAll("<", ""); //replacing angle brackets
            value = value.replaceAll(">", "");
            value = value.replaceAll("\"", ""); //replacing double quotes
            value = value.replaceAll("\'", ""); //replacing single quotes
           
      
        return value;
    }

}