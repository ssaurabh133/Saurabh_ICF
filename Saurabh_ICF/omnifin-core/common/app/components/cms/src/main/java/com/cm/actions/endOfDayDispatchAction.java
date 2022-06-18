package com.cm.actions;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;

import com.cm.dao.EndOfDayProcessDAO;
//import com.cm.dao.EndOfDayProcessDAOImpl;
import com.cm.dao.ReportsDAO;
import com.cm.vo.processVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.utils.EmailWithPdf;

public class  endOfDayDispatchAction extends DispatchAction
{
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String strFlag = "";
	private static final Logger logger = Logger.getLogger(endOfDayDispatchAction.class.getName());
	public ActionForward endOfProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info(" in endOfDay method of endOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		//for check User session start
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
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		EndOfDayProcessDAO service=(EndOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(EndOfDayProcessDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		//HttpSession sess=request.getSession();


		processVO vo =new processVO();
		
		//String strCompid = compid+"";
		vo.setCompanyId(compid);
		
		vo.setUserId(userId);
		vo.setCurrDate(businessDate);
		
		ArrayList list=new ArrayList();
		session.removeAttribute("eodData");
		//sess.removeAttribute("bodFlag");
		logger.info("In ..endOfProcess()..of..endOfDayDispatchAction");
      /*  StringBuilder bq=new StringBuilder();
		bq.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and ");
		bq.append(dbo);
		bq.append("DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+businessDate+"'");
		
		//String bq="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+businessDate+"'";
		logger.info("Query: "+bq.toString());
		boolean bflg=ConnectionDAO.checkStatus(bq.toString());  */
		boolean bflg=service.checkBdateStatus(businessDate);
		logger.info("bussiness date status: "+bflg); 
		String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_BOD_FLAG'";
		logger.info("Query: "+q);
		String flg=ConnectionDAO.singleReturn(q);
		
		if(bflg)
		{
			String count = "SELECT COUNT(1) FROM EOD_REPORTS_DTL WHERE REPORT_NAME IN('Business_Comparison_Report','login_breakup_report')";
			if(!(count.equalsIgnoreCase("0")||count.equalsIgnoreCase("")))
			{
				generateReport("Business_Comparison_Report",businessDate, request, response);
				generateReport("login_breakup_report",businessDate, request, response);
				ArrayList<String> mailToDetail = service.getMailToDetail();
				if (mailToDetail != null && mailToDetail.size() > 0) {
		            for (String mailToInfo: mailToDetail) 
					{
						String host = service.getSmtpHost();
				        String port = service.getSmtpPort();
				        String mailFrom =service.getSmtpMail();
				        String password = service.getSmtpPwd();
				        String mailTo = mailToInfo;
				        String subject = "OmniFin-Reports";
				        String bodyMessage = "<html><b>Hello,</b><br/><i>This is a Reports Detail email with attachment.</i></html>";
				        EmailWithPdf sender = new EmailWithPdf();
				        ArrayList<String> fileAttachment = service.getFileAttachment(mailTo,businessDate);
				        if(fileAttachment.size()>0)
				        	sender.sendEmail(host, port, mailFrom, password, mailTo, subject, bodyMessage, fileAttachment); 
					}
		        }
			}
		}
	if(bflg)
	{
	  if(flg.equalsIgnoreCase("E"))
	  {
		session.setAttribute("disbButtonEOD","true");
		list=service.showProcessData(vo);
		//boolean status=service.startEodBodProcess();
		//status=true;
		//if(status)
		//{	
		logger.info("Si8ze():---------------------------------"+list.size());
		
			if(list!=null && list.size()>1)
			{
				request.setAttribute("msg", "L");
				
				context.setAttribute("msg1", "R");
				list.remove(list.size()-1);
				session.setAttribute("ProcessDone", list);
				logger.info("In ..beginingOfProcess().DispatchAction flag********************* "+flg);
				logger.info("In ..endOfProcess()..of..endOfDayDispatchAction list"+list);
				service.updateUserLoginStatus(vo);
			}
			else
			{
				String procMsg="";
				String queryParam="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHECK_TRANSACTIONS_EOD' ";
				String paramValue=ConnectionDAO.singleReturn(queryParam);
				logger.info("else procdure msg : eod not processed "+list.size());
				//logger.info("list last element : "+list.get(list.size()-1));
				logger.info("paramValue : "+paramValue);
				request.setAttribute("paramValue", paramValue);
				if(list.size()>0)
				{
					String message=list.get(list.size()-1).toString();
					/*logger.info("message : "+message);
					String[] procMess=message.split("\\|");
					logger.info("procMess : "+procMess);
					if(procMess!=null && procMess.length!=0)
					{
						for(int i=0;i<procMess.length;i++)
						{
							logger.info("list last procMess : "+procMess[i]+" procMess.length: "+procMess.length);
							procMsg=procMsg+procMess[i]+"\n";
						
						}
						logger.info("list last procMsg : "+procMsg.toString());
						
					}*/
					request.setAttribute("procMsg", message);
				}
				
			}
			
			//sess.setAttribute("msg", "logout");
		//}
	  }
	  else
		{
			request.setAttribute("msg", "E");
			//list=service.showEodBodData(businessDate);
			//list=service.showProcessData(vo);
			//boolean status=service.startEodBodProcess();
			//status=true;
			//if(status)
			//{			
			//session.setAttribute("ProcessDone", list);
				logger.info("In ..beginingOfProcess().DispatchAction flag"+flg);
				logger.info("In ..endOfProcess()..of..endOfDayDispatchAction list"+list);
			
		}
	}
	else
	{
		request.setAttribute("msg", "P");
		//list=service.showEodBodData(businessDate);
		//list=service.showProcessData(vo);
		//boolean status=service.startEodBodProcess();
		//status=true;
		//if(status)
		//{			
		//session.setAttribute("ProcessDone", list);
			logger.info("In ..beginingOfProcess().DispatchAction flag"+flg);
			logger.info("In ..endOfProcess()..of..endOfDayDispatchAction list"+list);
	}
 	String eodRunningFlag=service.getEodRunningFlag();
	request.setAttribute("eodRunningFlag", eodRunningFlag);
	list=service.showEodBodData(businessDate);
	session.setAttribute("ProcessDone", list);
	session.setAttribute("disbButtonEOD","true"); // By Amit to disable Start Process Button.
		return mapping.findForward("endOfProcess");
	}
	
	public ActionForward errorLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In ..errorLink()..of..endOfDayDispatchAction");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in errorLink method of endOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		//for check User session start
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

		//CreditManagementDAO service= new CreditManagementDAOImpl();
		EndOfDayProcessDAO service=(EndOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(EndOfDayProcessDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList list= new ArrayList();
		String ProcessName=(String)request.getParameter("pname");
		logger.info("ProcessName is "+ProcessName);
		list=service.showErrorData(ProcessName);
			request.setAttribute("errorList", list);
			logger.info("List value is..."+list.toString());
	
		return mapping.findForward("errorLink");
	}
	public ActionForward refreshGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session=request.getSession();
		logger.info("In ..refreshGrid()..of..endOfDayDispatchAction");

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String businessDate="";
		if(userobj != null){
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info(" in refresh grid method of  endOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");

		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		/*if(!strFlag.equalsIgnoreCase(""))
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
		}*/

		session.removeAttribute("ProcessDone");
    	session.removeAttribute("ProcessNotDone");
    	//CreditManagementDAO service=new CreditManagementDAOImpl();
    	EndOfDayProcessDAO service=(EndOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(EndOfDayProcessDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
    	String Rec_status=service.getProcessStatus();
    	ArrayList list=service.showEodBodData(businessDate);
    	String eodRunningFlag=service.getEodRunningFlag();
    	request.setAttribute("eodRunningFlag", eodRunningFlag);
    	logger.info("Rec_status:-------------------------------------"+Rec_status);
    	if(Rec_status.equalsIgnoreCase("p"))
    	{
    		 if(list.size()>0)
 	    	{
 	    	request.setAttribute("RefreshData", list);
 	    	logger.info("In ..refreshGrid()..of..endOfDayDispatchAction List is"+list);
 	    	} 
 	
 	    else
 	    {
 	    	logger.info("In ..refreshGrid()..of..endOfDayDispatchAction List is"+list);	
 	    }
 	    
 		return mapping.findForward("refreshGrid");
    		
    	
    	}
    	else
    	{
    		logger.info("In ..refreshGrid()..of..endOfDayDispatchAction..endOfDayFinished..");
    		request.setAttribute("ProcessFinishedList", list);
    		
    		return mapping.findForward("ProcessStatus");
    	}
	}
	
	public ActionForward endOfProcessAfterConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" In  endOfProcessAfterConfirm method of endOfDayDispatchAction ----------------");
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info(" in endOfProcessAfterConfirm method of endOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		//for check User session start
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
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		EndOfDayProcessDAO service=(EndOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(EndOfDayProcessDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		
		//HttpSession sess=request.getSession();


		processVO vo =new processVO();
		
		//String strCompid = compid+"";
		vo.setCompanyId(compid);
		
		vo.setUserId(userId);
		vo.setCurrDate(businessDate);
		
		ArrayList list=new ArrayList();
		session.removeAttribute("eodData");
		//sess.removeAttribute("bodFlag");
		logger.info("In ..endOfProcessAfterConfirm()..of..endOfDayDispatchAction");
		
	/*	String bq="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and dbo.DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+businessDate+"'";
		logger.info("Query: "+bq);
		boolean bflg=ConnectionDAO.checkStatus(bq); */
		boolean bflg=service.checkBdateStatus(businessDate);
		logger.info("bussiness date status: "+bflg);
		String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_BOD_FLAG'";
		logger.info("Query: "+q);
		String flg=ConnectionDAO.singleReturn(q);
	if(bflg)
	{
	  if(flg.equalsIgnoreCase("E"))
	  {
		boolean updateStatus=service.updateEodRunningFlag("Y", userId, businessDate);
		logger.info("endOfProcessAfterConfirm updateEodRunningFlagupdateStatus:---"+updateStatus);
		list=service.showProcessDataAfterConfirm(vo);
		//boolean status=service.startEodBodProcess();
		//status=true;
		//if(status)
		//{		
			if(list!=null && list.size()>1)
			{
				request.setAttribute("msg", "L");
				
				context.setAttribute("msg1", "R");
				list.remove(list.size()-1);
				session.setAttribute("ProcessDone", list);
				logger.info("In ..endOfProcessAfterConfirm().DispatchAction flag********************* "+flg);
				logger.info("In ..endOfProcessAfterConfirm()..of..endOfDayDispatchAction list"+list);
				service.updateUserLoginStatus(vo);
			}
			else
			{
				String queryParam="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHECK_TRANSACTIONS_EOD' ";
				String paramValue=ConnectionDAO.singleReturn(queryParam);
				int size=list.size();
				logger.info("else procdure msg : eod not processed "+size);
				if(size>0)
				{
					logger.info("list last element : "+list.get(list.size()-1));
					request.setAttribute("procMsg", list.get(list.size()-1));
				}
				
				logger.info("paramValue : "+paramValue);
				request.setAttribute("paramValue", paramValue);
				
			}
			
			//sess.setAttribute("msg", "logout");
		//}
	  }
	  else
		{
			request.setAttribute("msg", "E");
			list=service.showEodBodData(businessDate);
			//list=service.showProcessData(vo);
			//boolean status=service.startEodBodProcess();
			//status=true;
			//if(status)
			//{			
			session.setAttribute("ProcessDone", list);
				logger.info("In ..endOfProcessAfterConfirm().DispatchAction flag"+flg);
				logger.info("In ..endOfProcessAfterConfirm()..of..endOfDayDispatchAction list"+list);
			
		}
	}
	else
	{
		request.setAttribute("msg", "P");
		list=service.showEodBodData(businessDate);
		//list=service.showProcessData(vo);
		//boolean status=service.startEodBodProcess();
		//status=true;
		//if(status)
		//{			
		session.setAttribute("ProcessDone", list);
			logger.info("In ..endOfProcessAfterConfirm().DispatchAction flag"+flg);
			logger.info("In ..endOfProcessAfterConfirm()..of..endOfDayDispatchAction list"+list);
	}
	request.setAttribute("eod", "eod");
		//return mapping.findForward("endOfProcess");
	return mapping.findForward("ProcessStatus");
	}
	
	
	
	
	public void generateReport(String reportName,String businessDate,HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		logger.info("In EodReportAction.........");
				Connection connectDatabase = ConnectionDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();
				
				//String reportName="Business_Comparison_Report";//will take from parameter				
				String reporttype="P";
				ReportsDAO dao=(ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				//ReportsDAO dao = new ReportsDAOImpl();
				
				String p_last_from_date=dao.getLastMonthDate(businessDate);
				String p_last_to_date=dao.getLastMonthDate(businessDate);
				String p_current_from_date=businessDate;
				String p_current_to_date=businessDate;
				String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
				String p_date_from=CommonFunction.changeFormat(businessDate);
				String p_date_to=CommonFunction.changeFormat(businessDate);
				
				hashMap.put("p_last_from_date",p_last_from_date);	
				hashMap.put("p_last_to_date",p_last_to_date);	
				hashMap.put("p_current_from_date",p_current_from_date);	
				hashMap.put("p_current_to_date",p_current_to_date);	
				hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);	
				hashMap.put("p_date_from",p_date_from);	
				hashMap.put("p_date_to",p_date_to);	
				logger.info("report Name  :  "+ reportName + ".jasper");
				
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream("/reports/" + reportName + ".jasper");
				JasperPrint jasperPrint = null;
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					if(reporttype.equals("P"))
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					ConnectionDAO.closeConnection(connectDatabase, null);
					hashMap.clear();					
				}
					

	}
	public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
			String path="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_REPORT_PATH'";
			String reportPath=ConnectionDAO.singleReturn(path);
			JasperExportManager.exportReportToPdfFile(jasperPrint,reportPath + "/" +reportName+".pdf");

	}	

	
	
}