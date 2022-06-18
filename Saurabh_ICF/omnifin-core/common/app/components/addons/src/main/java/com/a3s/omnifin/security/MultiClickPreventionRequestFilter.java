package com.a3s.omnifin.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieStore;
import java.util.Calendar;

import javax.ejb.Init;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;









import org.apache.log4j.Logger;

import com.connect.CommonFunction;

public class MultiClickPreventionRequestFilter implements Filter {

	private static final Logger logger = Logger.getLogger(MultiClickPreventionRequestFilter.class.getName());
	
	public static final String KEY_LAST_REQUEST_TIME_MILLIS="KEY_LAST_REQUEST_TIME_MILLIS";
	public static final String ServletPath="ServletPath";
	public static final String Method="Method";
	
	public static final long MIN_ALLOWED_REQUEST_TIME_MILLIS_DIFF = 1000;

	 
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String servletPath = request.getServletPath();
		logger.info("Path: "+servletPath);
		
		int[] isValid = validateRequestForMultiClick(request,response);
		
	
		if(isValid[0]==1){
			addMultiClickToken(request,response);
			chain.doFilter(request, response);
			HttpSession session = request.getSession();
			logger.info("ended.............");
			session.setAttribute("IS_LAST_REQUEST_COMPLETED", "Y");
		}else{
			
			//TODO: send to appropriate action for message to user
			logger.error("Request invalidated by MultiClickPreventionRequestFilter, as has been observed as a multi click case. Minimum allowed difference between subsequent request: "+isValid[1]+ ". Request: "+request.getRequestURI());

		
			
				//String path=	((HttpServletRequest) req).getServletPath() +"?"+ ((HttpServletRequest) req).getQueryString();
				String error=".....Please Wait for previous action to complete,before clicking for another action ......";
			 	/*StringBuffer strBuf = new StringBuffer();
			 	response.setContentType("text/html");  
				strBuf.append(" <script type=\"text/javascript\"> ");
				strBuf.append("alert('"+error+"'); ");
				strBuf.append(" </script> ");
				logger.info("redirct page : "+strBuf);
				ServletOutputStream servOutputStream;  
	            servOutputStream = res.getOutputStream();  
	            servOutputStream.println(strBuf.toString());  
	           
	          //  response.sendRedirect(path);
	            servOutputStream.flush(); */
				// request.getRequestDispatcher(path).forward(request, response);
	        	/*	Rohit Changes Starts for redirect*/
					  HttpSession session = request.getSession();
					  session.setAttribute("IS_LAST_REQUEST_COMPLETED", "Y");
					  String path=  (String) session.getAttribute(ServletPath);
				   	  response.setContentType("text/html");  
					  PrintWriter pw=response.getWriter(); 
					//  session.setAttribute("sendpath", path);
					  logger.info("sendPath::::"+session.getAttribute("sendpath"));
					  if(servletPath.equalsIgnoreCase("/AllReports.do") || servletPath.equalsIgnoreCase("/GenerateAllReports.do") || servletPath.equalsIgnoreCase("/GenerateAllGLReports.do")|| servletPath.equalsIgnoreCase("/reportforDayBook.do") || 
							  servletPath.equalsIgnoreCase("/reportforGL.do") || servletPath.equalsIgnoreCase("/reportforLoanWise.do") || servletPath.equalsIgnoreCase("/glTrialBalance.do") || servletPath.equalsIgnoreCase("/reportforSubLedgerTrial.do") || 
							  servletPath.equalsIgnoreCase("/reportforBranchBalanceSheet.do") || servletPath.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do") || servletPath.equalsIgnoreCase("/reportforBankReconStatement.do") ||
							  servletPath.equalsIgnoreCase("/reportForBillStatement.do") || servletPath.equalsIgnoreCase("/glVoucherPrinting.do") || servletPath.equalsIgnoreCase("/accountingListAction.do") || servletPath.equalsIgnoreCase("/glLedgerDump.do")||
							  servletPath.equalsIgnoreCase("/glFASchedule5.do") || servletPath.equalsIgnoreCase("/glDepreciationDetail.do") || servletPath.equalsIgnoreCase("/saleDiscardReport.do") ||
							  servletPath.equalsIgnoreCase("/reportForPartyWiseExp.do") || servletPath.equalsIgnoreCase("/reportforbill.do")|| servletPath.equalsIgnoreCase("/billreceivablereport.do") || servletPath.equalsIgnoreCase("/reportforBankReconcilation.do") || servletPath.equalsIgnoreCase("/reportForPartyMaster.do") || servletPath.equalsIgnoreCase("/glBalanceSheet.do)"))
					  {
						  session.setAttribute("timeForReports", "timeForReports");
					  }
					  if(servletPath.equalsIgnoreCase("/reportForPartyMaster.do"))
					  {
						  session.setAttribute("sendpath", "/partyMasterReport.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportForPartyWiseExp.do"))
					  {
						  session.setAttribute("sendpath", "/partyWiseExpRpt.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportforbill.do"))
					  {
						  session.setAttribute("sendpath", "/billreceivablepayablereport.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/billreceivablereport.do"))
					  {
						  session.setAttribute("sendpath", "/billreceivablereport.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportforDayBook.do"))
					  {
						  session.setAttribute("sendpath", "/glDayBook.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportforDayBook.do"))
					  {
						  session.setAttribute("sendpath", "/glDayBook.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportforBankReconcilation.do"))
					  {
						  session.setAttribute("sendpath", "/bankreconcilation.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportforBankReconStatement.do"))
					  {
						  session.setAttribute("sendpath", "/bankReconStatement.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/reportForBillStatement.do"))
					  {
						  session.setAttribute("sendpath", "/bankstatement.do");
					  }
					  else if(servletPath.equalsIgnoreCase("/glVoucherPrinting.do"))
					  {
						  session.setAttribute("sendpath", "/glVoucherPrinting.do");
					  }
					  else
					  {
						  session.setAttribute("sendpath", path);
					  }
					  response.sendRedirect("JSP/multiClickError.jsp");
					  pw.close(); 
					  return;
				 /*	Rohit Changes end for redirect*/
		}
		
	}
	
	private int[] validateRequestForMultiClick(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession session = req.getSession(false);
		Long lastMultipartRequestTime = null; 
		logger.info("Session:::"+session);
		
		//Result array index specification: 
		//			1 - boolean result(0:false/1:true); 
		//			2 - applicable minimum allowed time gap in milliseconds between subsequent requests
		//			3 - boolean isLastRequestServed(0:false/1:true)
		int[] arrResult = new int[3];
		
		
	if(session!=null){
	
		String SAME_PATH_GET_METHODS =req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_SAME_PATH_GET_METHODS_PREV_RES_COMP_N");
	long SPGM= Long.parseLong(SAME_PATH_GET_METHODS);
	logger.info("SPGM::::::::::::::::::::::::::::::::::::"+SPGM);
	
	String DIFF_PATH_GET_METHODS =req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_DIFF_PATH_GET_METHODS_PREV_RES_COMP_N");
	long DPGM= Long.parseLong(DIFF_PATH_GET_METHODS);
	logger.info("DPGM::::::::::::::::::::::::::::::::::::"+DPGM);
	
	String SAME_PATH_POST_METHODS =req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_SAME_PATH_POST_METHODS_PREV_RES_COMP_N");
	long SPPM= Long.parseLong(SAME_PATH_POST_METHODS);
	logger.info("SPPM::::::::::::::::::::::::::::::::::::"+SPPM);
	
	String DIFF_PATH_POST_METHODS =req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_DIFF_PATH_POST_METHODS_PREV_RES_COMP_N");
	long DPPM= Long.parseLong(DIFF_PATH_POST_METHODS);
	logger.info("DPPM::::::::::::::::::::::::::::::::::::"+DPPM);
	
	String MIN_MILLISEC_DIFF_FOR_SAME_PATH_GET_METHODS_RESP_EQL_Y=req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_SAME_PATH_GET_METHODS_PREV_RES_COMP_Y");
	long SAMEGET_Y= Long.parseLong(MIN_MILLISEC_DIFF_FOR_SAME_PATH_GET_METHODS_RESP_EQL_Y);
	logger.info("SAMEGET_Y::::::::::::::::::::::::::::::::::::"+SAMEGET_Y);
	
	String MIN_MILLISEC_DIFF_FOR_DIFF_PATH_GET_METHODS_RESP_EQL_Y=req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_DIFF_PATH_GET_METHODS_PREV_RES_COMP_Y");
	long DIFFGET_Y= Long.parseLong(MIN_MILLISEC_DIFF_FOR_DIFF_PATH_GET_METHODS_RESP_EQL_Y);
	logger.info("DIFFGET_Y::::::::::::::::::::::::::::::::::::"+DIFFGET_Y);

	String MIN_MILLISEC_DIFF_FOR_SAME_PATH_POST_METHODS_RESP_EQL_Y=req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_SAME_PATH_POST_METHODS_PREV_RES_COMP_Y");
	long SAMEPOST_Y= Long.parseLong(MIN_MILLISEC_DIFF_FOR_SAME_PATH_POST_METHODS_RESP_EQL_Y);
	logger.info("SAMEPOST_Y::::::::::::::::::::::::::::::::::::"+SAMEPOST_Y);
	
	String MIN_MILLISEC_DIFF_FOR_DIFF_PATH_POST_METHODS_RESP_EQL_Y=req.getSession().getServletContext().getInitParameter("MIN_MILLISEC_DIFF_FOR_DIFF_PATH_POST_METHODS_PREV_RES_COMP_Y");
	long DIFFPOST_Y= Long.parseLong(MIN_MILLISEC_DIFF_FOR_DIFF_PATH_POST_METHODS_RESP_EQL_Y);
	logger.info("DIFFPOST_Y::::::::::::::::::::::::::::::::::::"+DIFFPOST_Y);
	
	String Report=req.getSession().getServletContext().getInitParameter("Reports_TimeOut_N");
	long time_report_N= Long.parseLong(Report);
	logger.info("time_report_N::::::::::::::::::::::::::::::::::::"+time_report_N);
	
	String Report_Y=req.getSession().getServletContext().getInitParameter("Reports_TimeOut_Y");
	long time_report_y= Long.parseLong(Report_Y);
	logger.info("time_report_Y::::::::::::::::::::::::::::::::::::"+time_report_y);
	
	String lastServletPath=(String) session.getAttribute(ServletPath);
		//String ServletPath=		req.getServletPath();
	String currentUrl = req.getServletPath() +"?"+  CommonFunction.checkNull(req.getQueryString());
		logger.info("lastServletPath::::"+lastServletPath);
		logger.info("ServletPath::::"+currentUrl);
		String lastMethod=(String) session.getAttribute(Method);
		String CurrentMethod=req.getMethod();
		logger.info("CurrentMethod:::"+ CurrentMethod);
		logger.info("lastMethod:::"+ lastMethod);

		String lastMultipartRequestTimeString = (String) session.getAttribute(KEY_LAST_REQUEST_TIME_MILLIS);
			if(lastMultipartRequestTimeString!=null && !lastMultipartRequestTimeString.equals("")){
				lastMultipartRequestTime = Long.parseLong(lastMultipartRequestTimeString);
			}
			
			if(lastMultipartRequestTime==null)
			{
				lastMultipartRequestTime=(long) 0;
			}

			Long currentTime = Calendar.getInstance().getTimeInMillis();
			logger.info("currentTime::"+currentTime);
			long timeDiff = currentTime-lastMultipartRequestTime;
			logger.info("lastMultipartRequestTime::"+lastMultipartRequestTime);
			logger.info("timeDiff::"+timeDiff);
			
			
			String Result=(String) session.getAttribute("IS_LAST_REQUEST_COMPLETED");
		logger.info("Result::"+Result);
			session.setAttribute("IS_LAST_REQUEST_COMPLETED", "N");
			logger.info("IS_LAST_REQUEST_COMPLETED is N");
			if(Result!= null && CommonFunction.checkNull(Result).equalsIgnoreCase("Y"))
			{
				
			
				
				
				if(timeDiff < SAMEGET_Y && lastServletPath.equalsIgnoreCase(currentUrl)   && lastMethod.equalsIgnoreCase("GET") && CurrentMethod.equalsIgnoreCase("GET") && 
						!currentUrl.equalsIgnoreCase("/AllReports.do?") && (!currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) SAMEGET_Y;
					arrResult[2]=1;
			}
				else if(timeDiff < DIFFGET_Y && !lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("GET") && CurrentMethod.equalsIgnoreCase("GET") &&( !currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) DIFFGET_Y;
					arrResult[2]=1;
			}
				else if(timeDiff < SAMEPOST_Y && lastServletPath.equalsIgnoreCase(currentUrl) && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST")&& (!currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) SAMEPOST_Y;
					arrResult[2]=1;
			}
				else if(timeDiff < DIFFPOST_Y && !lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST") && (!currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?")  && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) DIFFPOST_Y;
					arrResult[2]=1;
			}
				else if(timeDiff < time_report_y && lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST") && ( currentUrl.equalsIgnoreCase("/AllReports.do?") || currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") || currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") || currentUrl.equalsIgnoreCase("/reportforDayBook.do?") || currentUrl.equalsIgnoreCase("/reportforGL.do?") || currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") || currentUrl.equalsIgnoreCase("/glTrialBalance.do?") || currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") || currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") || currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") || currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") || currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") || currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")||currentUrl.equalsIgnoreCase("/accountingListAction.do?")||currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						|| currentUrl.equalsIgnoreCase("/glFASchedule5.do?") || currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") || currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") || currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") || currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") || currentUrl.equalsIgnoreCase("/reportforbill.do?")  || currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") || currentUrl.equalsIgnoreCase("/billreceivablereport.do?") || currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) time_report_y;
					arrResult[2]=1;
			}
			
				else 
					arrResult[0]=1;
					arrResult[1]=0;
					arrResult[2]=1;
			
			}	
			else if( Result!= null && CommonFunction.checkNull(Result).equalsIgnoreCase("N"))
			{
				if(timeDiff < SPGM && lastServletPath.equalsIgnoreCase(currentUrl)   && lastMethod.equalsIgnoreCase("GET") && CurrentMethod.equalsIgnoreCase("GET") && (!currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?")  && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) SPGM;
					arrResult[2]=0;
			}
				else if(timeDiff < DPGM && !lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("GET") && CurrentMethod.equalsIgnoreCase("GET") && ( !currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?")  && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) DPGM;
					arrResult[2]=0;
			}
				else if(timeDiff < SPPM && lastServletPath.equalsIgnoreCase(currentUrl) && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST") && (!currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?")  && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) SPPM;
					arrResult[2]=0;
			}
				else if(timeDiff < DPPM && !lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST") && (!currentUrl.equalsIgnoreCase("/AllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") && !currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") && !currentUrl.equalsIgnoreCase("/reportforDayBook.do?") && !currentUrl.equalsIgnoreCase("/reportforGL.do?") && !currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") && !currentUrl.equalsIgnoreCase("/glTrialBalance.do?") && !currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") && !currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") && !currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") && !currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") && !currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") && !currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")&&!currentUrl.equalsIgnoreCase("/accountingListAction.do?")&&!currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						&& !currentUrl.equalsIgnoreCase("/glFASchedule5.do?") && !currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") && !currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") && !currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") && !currentUrl.equalsIgnoreCase("/reportforbill.do?")  && !currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") && !currentUrl.equalsIgnoreCase("/billreceivablereport.do?") && !currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) DPPM;
					arrResult[2]=0;
			}
				else if(timeDiff < time_report_N && lastServletPath.equalsIgnoreCase(currentUrl)  && lastMethod.equalsIgnoreCase("POST") && CurrentMethod.equalsIgnoreCase("POST") && (currentUrl.equalsIgnoreCase("/AllReports.do?") || currentUrl.equalsIgnoreCase("/GenerateAllReports.do?") || currentUrl.equalsIgnoreCase("/GenerateAllGLReports.do?") || currentUrl.equalsIgnoreCase("/reportforDayBook.do?") || currentUrl.equalsIgnoreCase("/reportforGL.do?") || currentUrl.equalsIgnoreCase("/reportforLoanWise.do?") || currentUrl.equalsIgnoreCase("/glTrialBalance.do?") || currentUrl.equalsIgnoreCase("/reportforSubLedgerTrial.do?") || currentUrl.equalsIgnoreCase("/reportforBranchBalanceSheet.do?") || currentUrl.equalsIgnoreCase("/reportforGLDailyCashRegisterConsolidated.do?") || currentUrl.equalsIgnoreCase("/reportforBankReconStatement.do?") || currentUrl.equalsIgnoreCase("/reportForBillStatement.do?") || currentUrl.equalsIgnoreCase("/glVoucherPrinting.do?")||currentUrl.equalsIgnoreCase("/accountingListAction.do?")||currentUrl.equalsIgnoreCase("/glLedgerDump.do?")
						|| currentUrl.equalsIgnoreCase("/glFASchedule5.do?") || currentUrl.equalsIgnoreCase("/glDepreciationDetail.do?") || currentUrl.equalsIgnoreCase("/saleDiscardReport.do?") || currentUrl.equalsIgnoreCase("/reportForPartyMaster.do?") || currentUrl.equalsIgnoreCase("/reportForPartyWiseExp.do?") || currentUrl.equalsIgnoreCase("/reportforbill.do?")  || currentUrl.equalsIgnoreCase("/reportforBankReconcilation.do?") || currentUrl.equalsIgnoreCase("/billreceivablereport.do?") || currentUrl.equalsIgnoreCase("/glBalanceSheet.do"))){
					arrResult[0]=0;
					arrResult[1]=(int) time_report_N;
					arrResult[2]=0;
			}
				else 
				{
					arrResult[0]=1;
					arrResult[1]=0;
					arrResult[2]=0;
				}
			
			}
			else{
				arrResult[0]=1;
				arrResult[1]=0;
				arrResult[2]=0;
			}
		}
	else{
		arrResult[0]=1;
		arrResult[1]=0;
		arrResult[2]=0;
	}

		return arrResult;
	
	}
	
	private void addMultiClickToken(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession session = req.getSession();
	
		String Method_val=req.getMethod();
		logger.info("LastMethod:::"+ Method_val);
		String fullRequestedURL = req.getServletPath() +"?"+ CommonFunction.checkNull(req.getQueryString());

		logger.info("LastServletPath:::"+ fullRequestedURL);
		session.removeValue(KEY_LAST_REQUEST_TIME_MILLIS);
		if(session!=null){
			String token = Long.toString(Calendar.getInstance().getTimeInMillis());
			session.setAttribute(KEY_LAST_REQUEST_TIME_MILLIS,token);
			session.setAttribute(ServletPath, fullRequestedURL);
			session.setAttribute(Method, Method_val);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
