package com.cp.actions;

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
import com.cm.dao.ReportsDAO;

public class ReportBehindActionForCP extends Action 
{
		private static final Logger logger = Logger.getLogger(ReportBehindActionForCP.class.getName());

		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception
			{	
		
			logger.info("In  ReportBehindActionForCP.. "); 
			HttpSession session = request.getSession();
			
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String businessDate=null;
			String userId=null;
			if(userobj==null){
				logger.info(" in execute method of ReportBehindActionForCP action the session is out----------------");
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
			ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
    		logger.info("In ReportBehindActionForCP(********) Implementation class: "+dao.getClass());
			ArrayList<ReportsForm> list1=dao.getReportFormat();
			request.setAttribute("list",list1);	
			String dateRengeLimit=dao.getDateRangeLimit();
			request.setAttribute("dateRengeLimit",dateRengeLimit);
			ArrayList<ReportsForm> product=dao.getProductName();
			request.setAttribute("productlist",product);	
			int role_id;
			role_id=Integer.parseInt(session.getAttribute("roleID").toString());
			ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
    		logger.info("Implementation class: "+reportdao.getClass());
    		int function_Id=Integer.parseInt(session.getAttribute("functionId").toString());
    		String module_id=session.getAttribute("moduleID").toString();
    		
    		logger.info("Role_id is--->"+role_id);
    		logger.info("function_id is--->"+function_Id);
    		logger.info("module_Id is--->"+module_id);
    		
			ArrayList<ReportsForm> report=reportdao.getReportNameForCp(role_id,function_Id,module_id);
			request.setAttribute("reportlist",report);
			//code added by neeraj tripathi
			String functionId=(String)session.getAttribute("functionId");
//			logger.info("functionId  :  "+functionId);
			if(functionId.trim().equalsIgnoreCase("3000902")||functionId.trim().equalsIgnoreCase("10000652"))
			{
				request.setAttribute("sourceReport","CP");	
				businessDate=CommonFunction.changeFormat(businessDate);
				String stDate=reportdao.getStartingDate(businessDate);
				logger.info("Starting Date  :  "+stDate);
				request.setAttribute("stDate",stDate);
				String source=(String)request.getParameter("source");
				logger.info("source  :  "+source);
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
					{e.printStackTrace();
					}finally{
						in.clear();
						out.clear();
						outMessages.clear();
						s1=null;
						s2=null;
					}
				}
				source=null;
			}
			businessDate=null;
			functionId=null;
			strFlag=null;
			userId=null;
			//tripathi's space end
			return mapping.findForward("success");
		}
	}
