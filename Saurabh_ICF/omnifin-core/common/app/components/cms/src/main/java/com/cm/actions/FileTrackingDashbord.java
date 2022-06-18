package com.cm.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.ReportsDAO;


public class FileTrackingDashbord extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(FileTrackingDashbord.class.getName());
	public ActionForward defaultMethod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in defaultMethod()  of FileTrackingDashbord ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in defaultMethod()  of FileTrackingDashbord ");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String source=CommonFunction.checkNull(request.getParameter("source")).trim();
		boolean status=false;
		String p_user_Id=userobj.getUserId();
		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("D"))
		{
			ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			status=dao.deleteUserOldRecord(p_user_Id);
		}
		String reportName="fileTrackingDashBoard";
		String p_report_format=request.getParameter("p_report_format");
		if(CommonFunction.checkNull(p_report_format).trim().equalsIgnoreCase("H"))
		{
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();       
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(p_user_Id);
			in.add("0");
			out.add(s1);
			out.add(s2);  
			try
			{
				logger.info("FILE_TRACKING_REPORT ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("FILE_TRACKING_REPORT",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1  : "+s1);
				logger.info("s2  : "+s2);	
				if(s1.equalsIgnoreCase("E"))
				{ 
					request.setAttribute("error", s2);
					logger.info("In FILE_TRACKING_REPORT reports can't be generate ");  
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				in.clear();
				out.clear();
				outMessages.clear();
				in=null;
				out=null;
				outMessages=null;
				s1=null;
				s2=null;
			}
		}
		Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		
		String p_company_name=userobj.getConpanyName();
		String p_printed_date=userobj.getBusinessdate();
		p_printed_date=formate(p_printed_date);
		String p_printed_by=userobj.getUserName();
		String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_printed_by",p_printed_by );
		hashMap.put("p_user_Id",p_user_Id );
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("p_app_path",p_app_path );		
		hashMap.put("IS_IGNORE_PAGINATION",true);
		
		logger.info("report Name      :  "+reportPath+reportName+".jasper");
		logger.info("p_report_format  :  "+p_report_format);
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(p_report_format.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			else if(p_report_format.equals("H"))				
				methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			hashMap.clear();	
			p_company_logo=null;
			p_company_name=null;
			p_printed_date=null;
			p_printed_by=null;
			p_user_Id=null;
			p_report_format=null;
			p_app_path=null;
		}
		return null;
	}		
	public ActionForward branchMethod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in branchMethod()  of FileTrackingDashbord ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in branchMethod()  of FileTrackingDashbord ");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String reportName="fileTrackingDashBoardBranch";
		String state=CommonFunction.checkNull(request.getParameter("state")).trim();
		String p_report_format=request.getParameter("p_report_format");
		String p_user_Id=userobj.getUserId();
		if(CommonFunction.checkNull(p_report_format).trim().equalsIgnoreCase("H"))
		{
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();       
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(p_user_Id);
			in.add(state);
			out.add(s1);
			out.add(s2);  
			try
			{
				logger.info("FILE_TRACKING_REPORT ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("FILE_TRACKING_REPORT",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1  : "+s1);
				logger.info("s2  : "+s2);	
				if("E".equalsIgnoreCase(s1))
				{ 
					request.setAttribute("error", s2);
					logger.info("In FILE_TRACKING_REPORT reports can't be generate ");  
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				in.clear();
				out.clear();
				outMessages.clear();
				in=null;
				out=null;
				outMessages=null;	
				s1=null;
				s2=null;
			}
		}
		Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		
		String p_company_name=userobj.getConpanyName();
		String p_printed_date=userobj.getBusinessdate();
		p_printed_date=formate(p_printed_date);
		String p_printed_by=userobj.getUserName();
		String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_printed_by",p_printed_by );
		hashMap.put("p_user_Id",p_user_Id );
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("p_app_path",p_app_path );		
		hashMap.put("IS_IGNORE_PAGINATION",true);
		hashMap.put("state_id",state);
		
		
		logger.info("report Name      :  "+reportPath+reportName+".jasper");
		logger.info("p_report_format  :  "+p_report_format);
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(p_report_format.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			else if(p_report_format.equals("H"))				
				methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			hashMap.clear();	
			p_company_logo=null;
			p_company_name=null;
			p_printed_date=null;
			p_printed_by=null;
			p_user_Id=null;
			p_report_format=null;
			p_app_path=null;
		}
		return null;
	}
	public ActionForward fileTrackingDtl(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in fileTrackingDtl()  of FileTrackingDashbord ");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in fileTrackingDtl()  of FileTrackingDashbord ");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String reportName="fileTrackingDashBoardDTL";
		Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		String p_report_format=request.getParameter("p_report_format");
		String state=request.getParameter("state");
		String branch=request.getParameter("branch");
		String type=request.getParameter("type");
		
		logger.info("p_report_format  :  "+p_report_format);
		logger.info("state            :  "+state);
		logger.info("type             :  "+type);
		
		StringBuffer query=new StringBuffer();
		
				
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("DF")||CommonFunction.checkNull(type).trim().equalsIgnoreCase(""))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE,' ' REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS IS NULL ");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("SO"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='SO'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("RO"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='RO'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("RU"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='RU'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("H"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='H'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("C"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='C'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("SS"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='SS'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}
		if(CommonFunction.checkNull(type).trim().equalsIgnoreCase("RS"))
		{
			query.append("SELECT A.LOAN_NO,CONCAT(B.CUSTOMER_NAME,' ')CUSTOMER_NAME,CONCAT(C.PRODUCT_DESC,' ')PRODUCT_DESC,CONCAT(D.SCHEME_DESC,' ')SCHEME_DESC,A.LOAN_LOAN_AMOUNT,CONCAT(F.BRANCH_DESC,' ')BRANCH, ");
			query.append(" case A.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as LOAN_STATUS, ");
			query.append(" CONCAT(E.USER_NAME,' ') MAKER_ID,A.MAKER_DATE ");
			if(CommonFunction.checkNull(dbType).trim().equalsIgnoreCase("MSSQL"))
				query.append(" ,CONCAT(ISNULL(FL.REMARKS,''),' ')REMARKS ");
			else
				query.append(" ,CONCAT(IFNULL(FL.REMARKS,''),' ')REMARKS ");
			query.append(" from cr_loan_dtl A ");
			query.append(" JOIN cr_file_track_dtl FL ON(FL.LOAN_ID=A.LOAN_ID) ");			
			query.append(" JOIN gcd_customer_m B ON(A.LOAN_CUSTOMER_ID=B.CUSTOMER_ID) ");
			query.append(" JOIN cr_product_m C ON(A.LOAN_PRODUCT=C.PRODUCT_ID) ");
			query.append(" JOIN cr_scheme_m D ON(A.LOAN_SCHEME=D.SCHEME_ID) ");
			query.append(" JOIN sec_user_m E ON(A.MAKER_ID=E.USER_ID) ");
			query.append(" JOIN com_branch_m F ON(A.LOAN_BRANCH=F.BRANCH_ID) ");
			query.append(" WHERE A.FILE_STATUS='RS'");
			if(!(CommonFunction.checkNull(state).trim().equalsIgnoreCase("All")||CommonFunction.checkNull(state).trim().equalsIgnoreCase("")))
				query.append(" AND F.STATE_ID='"+CommonFunction.checkNull(state).trim()+"' ");
			if(!(CommonFunction.checkNull(branch).trim().equalsIgnoreCase("")))
				query.append(" AND A.LOAN_BRANCH='"+CommonFunction.checkNull(branch).trim()+"' ");
		}	
		logger.info("Report Query         :  "+query.toString());
		
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_company_name=userobj.getConpanyName();
		String p_printed_date=userobj.getBusinessdate();
		p_printed_date=formate(p_printed_date);
		String p_printed_by=userobj.getUserName();
		String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		
		
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_printed_by",p_printed_by );
		hashMap.put("p_app_path",p_app_path );	
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("state",state );
		hashMap.put("type",type );	
		hashMap.put("query",query.toString());		
		hashMap.put("IS_IGNORE_PAGINATION",true);
		
		logger.info("report Name      :  "+reportPath+reportName+".jasper");
		logger.info("p_report_format  :  "+p_report_format);
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(p_report_format.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
			else if(p_report_format.equals("H"))				
				methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
			p_company_logo=null;
			p_company_name=null;
			p_printed_date=null;
			p_printed_by=null;
			p_report_format=null;
			p_app_path=null;
			p_report_format=null;
			state=null;
			type=null;
			query=null;
		}
		return null;
	}
	public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{	 
		JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
			outStream.write(buffer, 0, n);			
		outStream.flush();
		fin.close();
		outStream.close();
	}
	public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{		
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");  			
		JRHtmlExporter exporter = new JRHtmlExporter();		
		response.setContentType("text/html");
        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);		
		float f1=1.2f;	
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
       	exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
       	exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
       	exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
       	exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());      
       	exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
       	ServletContext context = this.getServlet().getServletContext();
       	exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,"");      
       	exporter.exportReport();    
	}
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String excelReportFileName=reportName+".xls";		
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		exporterXLS.exportReport();
	}	
	String formate(String date)
	{
		String result="";
		int m1=Integer.parseInt(date.substring(3,5));		
		switch(m1)
		{					
			case 1: result=date.substring(0,2)+"-Jan-"+date.substring(6);
					break;					
			case 2: result=date.substring(0,2)+"-Feb-"+date.substring(6);
					break;							
			case 3: result=date.substring(0,2)+"-Mar-"+date.substring(6);
					break;						
			case 4: result=date.substring(0,2)+"-Apr-"+date.substring(6);
					break;					
			case 5: result=date.substring(0,2)+"-May-"+date.substring(6);
					break;					
			case 6: result=date.substring(0,2)+"-Jun-"+date.substring(6);
					break;					
			case 7: result=date.substring(0,2)+"-Jul-"+date.substring(6);
					break;					
			case 8: result=date.substring(0,2)+"-Aug-"+date.substring(6);
					break;				
			case 9: result=date.substring(0,2)+"-Sep-"+date.substring(6);
					break;					
			case 10: result=date.substring(0,2)+"-Oct-"+date.substring(6);
					break;					
			case 11: result=date.substring(0,2)+"-Nov-"+date.substring(6);
					break;					
			case 12: result=date.substring(0,2)+"-Dec-"+date.substring(6);						
		}	
		return result;
	}
}