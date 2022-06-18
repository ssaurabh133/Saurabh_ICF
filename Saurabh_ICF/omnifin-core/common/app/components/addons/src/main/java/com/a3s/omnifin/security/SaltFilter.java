package com.a3s.omnifin.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.md5;


public class SaltFilter implements Filter {

	 private static final Logger logger = Logger.getLogger(SaltFilter.class.getName());
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		//logger.info("SaltFilter  is start");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) res;
		 String servletPath = request.getServletPath();
			//logger.info("Path:============ "+servletPath);
		 if(!servletPath.equalsIgnoreCase("/changePasswordMaster.do"))
		 {
		 this.saltGenerator(request, response);
		 }
		 boolean flg = true;
		if(servletPath.equalsIgnoreCase("/changePasswordMaster.do"))
		{
			
			flg=this.saltveladeter(request, response);
			if(flg==false)
			{
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/logoff.do");
				dispatcher.forward(request, response);
			//	logger.info("Path:============ flg  "+flg);
				
			}
		}
		if(flg)
		chain.doFilter(request, response);
		
	}

	public void saltGenerator (HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session =request.getSession();
		 String randomAlphaNumericSaltFroPass= md5.randomAlphaNumeric(7);
		 session.setAttribute("randomAlphaNumericSalt",randomAlphaNumericSaltFroPass);
		 logger.info("SaltFilter salt is  "+randomAlphaNumericSaltFroPass);
		
	}
	
	public boolean saltveladeter (HttpServletRequest request, HttpServletResponse response)
	{
		String forcechangepassForSalt="";
		boolean rtn = true;
		HttpSession session =request.getSession();
		 HttpServletRequestWrapper RequestWrapper = new HttpServletRequestWrapper(request);
		String randomAlphaNumericSaltFroPassveladater = RequestWrapper.getParameter("randomAlphaNumericSaltFroPass");
		logger.info("HttpServletRequestWrapper value"+randomAlphaNumericSaltFroPassveladater);
		 String randomAlphaNumericSaltFroPass=CommonFunction.checkNull( session.getAttribute("randomAlphaNumericSalt"));
		 forcechangepassForSalt= (String) session.getAttribute("forcechangepassForSalt");
		 if(forcechangepassForSalt!="forcechangepass")
			{
		rtn= randomAlphaNumericSaltFroPass .equals(randomAlphaNumericSaltFroPassveladater);
			}
		return rtn;
	}
}
