
package com.cm.actions;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.cm.dao.BeginOfDayProcessDAO;
import com.cm.vo.SmsEmailDTO;
import com.cm.vo.processVO;
import com.communication.engn.vo.SmsVO;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.utils.async.LMSMessagingClient;

public class  BeginingOfDayDispatchAction extends DispatchAction
{
	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	private static final Logger logger = Logger.getLogger(BeginingOfDayDispatchAction.class.getName());
	public ActionForward beginingOfProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		logger.info(" value of userobj **************************** "+userobj);
		String userId ="";
		String bDate ="";
		int compid=0;
		if(userobj!=null){
			userId = userobj.getUserId();
			bDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
		}else{
			logger.info(" in beginingOfProcess method of BeginingOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String strFlag = "";
		ServletContext context = getServlet().getServletContext();
		
		boolean flag=false;
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
			logger.info("function id **************************** "+functionId);
		}
		
		Object sessionId = session.getAttribute("sessionID");

		
		//for check User session start
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"4001001",request);
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
		//HttpSession sess=request.getSession();
		
		//userobj=(UserObject)session.getAttribute("userobject");
		
		processVO vo =new processVO();
		
		//String strCompid = compid+"";
		vo.setCompanyId(compid);
		
		vo.setUserId(userId);
		vo.setCurrDate(bDate);
		
		ArrayList list=new ArrayList();
		session.removeAttribute("eodData");
		logger.info("In ..beginingOfProcess..");
		logger.info("In ..getBusinessdate.."+userobj.getBusinessdate());
		//CreditManagementDAO service=new CreditManagementDAOImpl();
    	//CAHNGE BY SACHIN
    	BeginOfDayProcessDAO service=(BeginOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(BeginOfDayProcessDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
        //END BY SACHIN
//		BeginOfDayProcessDAO service=new BeginOfDayProcessDAOImpl();
		StringBuilder bq=new StringBuilder();
		bq.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and ");
		bq.append(dbo);
		bq.append("DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+userobj.getBusinessdate()+"'");
		//String bq="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+userobj.getBusinessdate()+"'";
		logger.info("Query: "+bq.toString());
		boolean bflg=ConnectionDAO.checkStatus(bq.toString());
		logger.info("bussiness date status: "+bflg);
		String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_BOD_FLAG'";
		logger.info("Query: "+q);
		String flg=ConnectionDAO.singleReturn(q);
	if(bflg)
	{
		  if(flg.equalsIgnoreCase("B"))
		  {
			list=service.showBodProcessData(vo);
			//boolean status=service.startBodProcess();
			//status=true;
			//if(status)
			//{			
				request.setAttribute("msg", "S");
				session.setAttribute("ProcessDone", list);
				
				//Rohit Changes Starts for SMS & Email
				SmsEmailDTO dto= new SmsEmailDTO();
				dto.setBusiness_date(bDate);
				dto.setUserId(userId);
				LMSMessagingClient lms =new LMSMessagingClient();
				lms.sendObjectMessage(dto,"SmsEmailQueue");
				//Rohit Changes end
				logger.info("In ..beginingOfProcess().DispatchAction flag"+flg);
				logger.info("In ..beginingOfProcess().DispatchAction list"+list);
		  }
		
			else
			{
				request.setAttribute("msg", "E");
				//list=service.showBodProcessData(vo);
				//boolean status=service.startBodProcess();
				//status=true;
				//if(status)
				//{			
				ArrayList	list1=service.showBodData(bDate);
				session.setAttribute("ProcessDone", list1);
				
				logger.info("In ..beginingOfProcess().DispatchAction list"+list1);
				logger.info("In ..beginingOfProcess().DispatchAction flag"+flg);
				
				
			}
	}
		else
		{
			request.setAttribute("msg", "P");
			//list=service.showBodProcessData(vo);
			//boolean status=service.startBodProcess();
			//status=true;
			//if(status)
			//{			
			ArrayList	list1=service.showBodData(bDate);
			session.setAttribute("ProcessDone", list1);
			
			logger.info("In ..beginingOfProcess.DispatchAction list"+list1);
			logger.info("In ..beginingOfProcess.DispatchAction flag"+flg);
		}
	//change by sachin
	
	if(flg.equalsIgnoreCase("B"))
	{
		request.setAttribute("bodRunningFlag", flg);
	}
	
	//end by sachin
		session.setAttribute("disbButtonBOD","true"); // By Amit to disable Start Process Button.
		return mapping.findForward("beginingOfProcess");
	}
		
	
	public ActionForward errorLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In ..errorLink()..of..beginingOfProcessDispatchAction");
		HttpSession session =  request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null){
		logger.info(" in errorLink method of BeginingOfDayDispatchAction action the session is out----------------");
		return mapping.findForward("sessionOut");
		}
		String strFlag = "";
		ServletContext context = getServlet().getServletContext();
	
		boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
			logger.info("function id **************************** "+functionId);
		}
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"4001001",request);
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
    	//CAHNGE BY SACHIN
    	BeginOfDayProcessDAO service=(BeginOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(BeginOfDayProcessDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
        //END BY SACHIN
//		BeginOfDayProcessDAO service=new BeginOfDayProcessDAOImpl();
		
		ArrayList list= new ArrayList();
		String ProcessName=(String)request.getParameter("pname");
		logger.info("ProcessName is "+ProcessName);
		list=service.showBodErrorData(ProcessName);
			request.setAttribute("errorList", list);
			logger.info("List value is..."+list);
	
		return mapping.findForward("errorLink");
	}
	public ActionForward refreshGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session=request.getSession();
		logger.info("In ..refreshGrid()..of..beginingOfProcessDispatchAction");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String busnissDate="";
		if(userobj != null){
			 busnissDate = userobj.getBusinessdate();			
		}		
		else{
			logger.info(" in refreshGrid method of BeginingOfDayDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	
		//for check User session start
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";		
		boolean flag=false;		
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
			logger.info("function id **************************** "+functionId);
		}
		if(sessionId!=null && userobj != null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"4001001",request);
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
		session.removeAttribute("ProcessDone");
    	session.removeAttribute("ProcessNotDone");
    	//CreditManagementDAO service=new CreditManagementDAOImpl();
    	//CAHNGE BY SACHIN
    	BeginOfDayProcessDAO service=(BeginOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(BeginOfDayProcessDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
        //END BY SACHIN
//    	BeginOfDayProcessDAO service=new BeginOfDayProcessDAOImpl();
    	String Rec_status=service.getBodProcessStatus();
    	ArrayList list=service.showBodData(busnissDate);
    	
    	//change by sachin
    	String startProcess="";
    	if(request.getParameter("startFlag")!=null){
    		startProcess=request.getParameter("startFlag");
    	}
    	logger.info("value of startProcess"+startProcess);
    	String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_BOD_FLAG'";
		logger.info("Query: "+q);
		String flg=ConnectionDAO.singleReturn(q);
		if(startProcess.equalsIgnoreCase("Y"))
		{
			if(flg.equalsIgnoreCase("B"))
			{
			session.setAttribute("disbButtonBOD","true");
			request.setAttribute("bodRunningFlag", flg);
			}
		}
    	//end by sachin
    	if(Rec_status.equalsIgnoreCase("p"))
    	{
    		 if(list.size()>0)
 	    	{
 	    	request.setAttribute("RefreshData", list);
 	    	logger.info("In ..refreshGrid()..of.beginingOfProcessDispatchAction List is"+list);
 	    	} 
 	
 	    else
 	    {
 	    	logger.info("In ..refreshGrid()..of..beginingOfProcessDispatchAction List is"+list);	
 	    }
 	    
 		return mapping.findForward("refreshGrid");
    		
    	
    	}
    	else
    	{
    		logger.info("In ..refreshGrid()..of..beginingOfProcessDispatchAction..endOfDayFinished..");
    		request.setAttribute("ProcessFinishedList", list);
    		return mapping.findForward("ProcessStatus");
    	}
	}
	
}