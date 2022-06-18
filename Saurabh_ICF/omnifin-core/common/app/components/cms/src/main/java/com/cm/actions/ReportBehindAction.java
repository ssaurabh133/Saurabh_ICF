package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actionform.ReportsForm;
import com.cm.dao.DownloadDAO;
import com.cm.dao.ReportsDAO;


public class ReportBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(ReportBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String businessDate="";
		String userId="";
		if(userobj==null){
			logger.info(" in execute method of  ReportsAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			businessDate=userobj.getBusinessdate();
			userId=userobj.getUserId();
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
		int role_id;
		role_id=Integer.parseInt(session.getAttribute("roleID").toString());
		logger.info("role_id  "+role_id);
		ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+reportdao.getClass());
		//ReportsDAO reportdao = new ReportsDAOImpl();
		String dateRengeLimit=reportdao.getDateRangeLimit();
		request.setAttribute("dateRengeLimit",dateRengeLimit);
		String DateRangeLimitSpecial=reportdao.getDateRangeLimitSpecial();
		request.setAttribute("DateRangeLimitSpecial",DateRangeLimitSpecial);
		int function_Id=Integer.parseInt(session.getAttribute("functionId").toString());
		String module_id=session.getAttribute("moduleID").toString();
		ArrayList<ReportsForm> list1=reportdao.getReportFormat();
		//Changes Start for Report generate on EXCEL(2007)
		if(module_id.equalsIgnoreCase("CMS"))
		{
			ReportsForm repoForm=new ReportsForm();
			repoForm.setReportformat("X");
			repoForm.setReportformatid("EXCEL(2007)");
			if(list1.size()>0)
			{
				list1.add(repoForm);
			}
		}
		//Changes Start for Report generate on EXCEL(2007)
		request.setAttribute("list",list1);			
		logger.info("Role_id is--->"+role_id);
		logger.info("function_id is--->"+function_Id);
		logger.info("module_Id is--->"+module_id);
		
		ArrayList<ReportsForm> report=reportdao.getReportNameForCp(role_id,function_Id,module_id);
		ArrayList<ReportsForm> product=reportdao.getProductName();
		ArrayList<ReportsForm> loanClassification=reportdao.getLoanClassification();
		ArrayList<ReportsForm> sponsor=reportdao.getSponsorCode();
		ArrayList<ReportsForm> financeYear=reportdao.getfinanceYear();
		String maxDate=reportdao.getMaxDefaultDate();
		request.setAttribute("maxDate",maxDate);
		request.setAttribute("financeYear",financeYear);	
		request.setAttribute("reportlist",report);	
		request.setAttribute("productlist",product);	
		request.setAttribute("loanClasslist",loanClassification);
		request.setAttribute("sponsorlist",sponsor);
		session.setAttribute("userId", userId);
		//code added by neeraj tripathi
		//DownloadDAO ddao =new DownloadDAOImpl();
		DownloadDAO ddao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
	//	logger.info("Implementation class: "+ddao.getClass()); 
		String eodList=ddao.getCutoffDate();
	//	logger.info("List Off Cut_Off_Date  :  "+eodList);
		request.setAttribute("eodList",eodList);
		String functionId=(String)session.getAttribute("functionId");
	//	logger.info("functionId  :  "+functionId);
		if(functionId.trim().equalsIgnoreCase("4000209"))
		{
			ArrayList<ReportsForm> scheme=reportdao.getScheme();
			request.setAttribute("scheme",scheme);	
			return mapping.findForward("success");
		}
		if(functionId.trim().equalsIgnoreCase("4000206")||functionId.trim().equalsIgnoreCase("10000676"))
		{
			String cutOffDate=reportdao.getcutOffDate();
			request.setAttribute("cutOffDate",cutOffDate);	
		}
		if(functionId.trim().equalsIgnoreCase("4000207")||functionId.trim().equalsIgnoreCase("10000677"))
		{
			request.setAttribute("sourceReport","CM");	
			businessDate=CommonFunction.changeFormat(businessDate);
			String stDate=reportdao.getStartingDate(businessDate);
		//	logger.info("Starting Date  :  "+stDate);
			request.setAttribute("stDate",stDate);
			String source=(String)request.getParameter("source");
			String dasRreport=(String)request.getParameter("report");
			if(dasRreport==null)
				dasRreport="P";
			request.setAttribute("report", dasRreport);
		//	logger.info("report  :  "+dasRreport);
		//	logger.info("source  :  "+source);
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("R"))
			{
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				String s1="";
				String s2="";
				in.add(userId); // userID
				in.add("N"); //parent report
				in.add("N"); // current report
				in.add("N"); // current URL
				in.add("B"); // backForward
				out.add(s1);
				out.add(s2);
				try
				{
					logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
					outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					logger.info("s1      : "+s1);
				    logger.info("s2      : "+s2);
				}
				catch (Exception e) 
				{e.printStackTrace();}finally{
					in.clear();
					out.clear();
					s2=null;
					s2=null;
				}
			}
			source=null;
		}
		//tripathi's space end
		
		//Nishant starts
		if(functionId.trim().equalsIgnoreCase("4000201")||functionId.trim().equalsIgnoreCase("10000671"))
		{
			String cutOffDateForIncipient=reportdao.getCutoffDateForIncipient();
			cutOffDateForIncipient=CommonFunction.changeFormatJSP(cutOffDateForIncipient);
			logger.info("cutOffDateForIncipient : "+cutOffDateForIncipient);
			request.setAttribute("cutOffDateForIncipient",cutOffDateForIncipient);	
		}
		if(functionId.trim().equalsIgnoreCase("4000250"))
		{
			logger.info(" CMS DOWN LOAD : ");
		}
		//Nishant End
		
						
//		ReportsDAO service = new ReportsDAOImpl();
//		ArrayList list = service.getStage();
//		logger.info("Size of list: "+list.size());
//		request.setAttribute("docStage", list);
		saveToken(request);// Save Token Before Loading jsp in any case
		request.setAttribute("generateReport","N");
		session.removeAttribute("reportName");
		session.removeAttribute("reporttype");
		session.removeAttribute("hashMap");
		String loginUserId=userobj.getUserId();
		session.setAttribute("loginUserId",loginUserId);
		businessDate=null;
		userId=null;
		strFlag=null;
		functionId=null;
		return mapping.findForward("success");
	}
	

}
