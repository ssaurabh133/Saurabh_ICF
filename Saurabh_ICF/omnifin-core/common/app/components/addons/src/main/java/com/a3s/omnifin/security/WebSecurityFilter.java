package com.a3s.omnifin.security;

	
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

	public class WebSecurityFilter implements Filter {
	 private static final Logger logger = Logger.getLogger(WebSecurityFilter.class.getName());
	private FilterConfig filterConfig;
	    public void SecurityFilter()
	    {
	        filterConfig = null;
	    }

	 

	    public void init(FilterConfig filterConfig)
	        throws ServletException
	    {
	        this.filterConfig = filterConfig;
	    }

	    public void destroy()
	    {
	        filterConfig = null;
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws ServletException, IOException
	    {
	   	 logger.info("--in doFilter()----"); 
	    	
	/*    	 HttpServletRequest httpRequest = (HttpServletRequest) request;        
	         if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")){
	        	System.out.println("HTTP METHOD");
	         }
	     	System.out.println("HTTP METHOD::::"+httpRequest.getMethod());
	    	
	    	*/
	    	//Rohit Changes starts
					
	  
	            String sessionid = ((HttpServletRequest) request).getSession().getId();
	            String contextPath = ((HttpServletRequest) request).getContextPath();
	         	
	        
	  //  System.out.println("WebSecurityFilter ..................................");
	    // Protection against Type 1 Reflected XSS attacks
	 
	    StringBuffer URL = ((HttpServletRequest) request).getRequestURL();
	    
	    //pooja 
	    /*String queryString = ((HttpServletRequest)request).getQueryString();
	    if ("GET".equalsIgnoreCase(((HttpServletRequest)request).getMethod()))
	    {
	      if ((URL.toString().contains("searchLoanDealBehindAction.do")) || (URL.toString().contains("repaymentScheduleDisbursal.do")) || (URL.toString().contains("viewLoanSummaryCustomerService.do")) || (URL.toString().contains("loanDetailsForClosure.do")) || ((URL.toString().contains("updateRepaySchedule.do")) && (queryString.toString().equalsIgnoreCase("method=newRepayScheduleDueDate"))) || ((URL.toString().contains("disbursalMaker.do")) && (queryString.toString().equalsIgnoreCase("method=generateRepaymentSch"))) || (URL.toString().contains("newRepaymentScheduleDeferral.do")) || (URL.toString().contains("repaymentScheduleAdditionalDisbursal.do")))
	      {
	        ((HttpServletResponse)response).sendRedirect("JSP/Login.jsp");
	        return;
	      }
	    }*/
	     
	    String queryString = ((HttpServletRequest)request).getQueryString();
	    if ("GET".equalsIgnoreCase(((HttpServletRequest)request).getMethod()))
	    {
	      if ((URL.toString().contains("loginInput.do")))
	      {
	        ((HttpServletResponse)response).sendRedirect("JSP/Login.jsp");
	        return;
	      }
	    }
	    //pooja
	  
	    if(((HttpServletRequest) request).isSecure() || URL.toString().contains("https") || URL.toString().contains("HTTPS") )
	    {
	    	System.out.println("HTTPS.........");
	    	  ((HttpServletResponse) response).setHeader("Set-Cookie", "JSESSIONID=" + sessionid
	                  + "; Path=" + contextPath + ";HttpOnly;secure");
	    }
	 
	
	    ((HttpServletResponse) response).addHeader("X-XSS-Protection", "1; mode=block");
	    // Disabling browsers to perform risky mime sniffing
	    ((HttpServletResponse) response).addHeader("X-Content-Type-Options", "nosniff");
		
//Rohit Changes end
					
	    	// chain.doFilter(new RequestWrapper((HttpServletRequest)request), response);
	        ((HttpServletResponse)response).addHeader("X-FRAME-OPTIONS", "SAMEORIGIN" );
	        //pooja

	        ((HttpServletResponse)response).addHeader("X-Robots-Tag", "none");

	        if (((HttpServletRequest)request).getMethod().equals("OPTIONS"))
	        {
	          ((HttpServletResponse)response).sendError(405);
	        }
	        else if (((HttpServletRequest)request).getMethod().equals("PUT"))
	        {
	          ((HttpServletResponse)response).sendError(405);
	        }
	        else if (((HttpServletRequest)request).getMethod().equals("DELETE"))
	        {
	          ((HttpServletResponse)response).sendError(405);
	        }
	        else
	        {
	          chain.doFilter(new RequestWrapper((HttpServletRequest)request), response);
	        }
	        //pooja
	     
	    }
	}