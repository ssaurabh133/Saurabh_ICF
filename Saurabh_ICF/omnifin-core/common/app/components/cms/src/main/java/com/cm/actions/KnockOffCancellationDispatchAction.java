
package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.KnockOffDAO;
import com.cm.vo.KnockOffMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class KnockOffCancellationDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(KnockOffCancellationDispatchAction.class.getName());


	public ActionForward newKnockOff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();		
		}
		else
		{
			logger.info("here in newKnockOff method of KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");	
		return mapping.findForward("success");
	}
	public ActionForward openKnockOffCanAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
		{
			userId = userobj.getUserId();		
		}
		else
		{
			logger.info("here in openKnockOffCanAuthor method of KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		session.removeAttribute("decision");
		session.removeAttribute("remarks");
		session.removeAttribute("save");
		session.removeAttribute("notSave");
		session.removeAttribute("errorMsg");
        return mapping.findForward("author");
	}
	
	public ActionForward saveKOCAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("In saveKOCAuthor() of KnockOffCancellationDispatchAction.");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String currentdate="";
		int company_id=0;
		if(userobj!=null)
		{
			userId = userobj.getUserId();
			currentdate=userobj.getBusinessdate();
			company_id=userobj.getCompanyId();
		}
		else
		{
			logger.info("here in saveKOCAuthor method of KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		String loanId="";
		int knockOffId=0;
		String decision="";
		String remarks="";
		
		loanId=(String)session.getAttribute("loanId");
		knockOffId=Integer.parseInt((String)session.getAttribute("knockOffId"));
		decision=request.getParameter("decision");
		remarks=request.getParameter("remarks");
		session.setAttribute("decision",decision);
		session.setAttribute("remarks",remarks);
		logger.info("In saveKOCAuthor() loanId  : "+loanId);	
		logger.info("In saveKOCAuthor() knockOffId  : "+knockOffId);
		logger.info("In saveKOCAuthor() decision  : "+decision);
		logger.info("In saveKOCAuthor() remarks  : "+remarks);
		
//		KNOCKOFF_CANCELATION_AUTHOR`(IN I_COMPANY_ID INT, IN I_KNOCKOFF_ID INT(10), 
//                IN I_CURR_DATE DATE, IN I_USER_ID VARCHAR(10),
//IN I_STATUS VARCHAR(1) , IN I_REMARKS VARCHAR(500),
//                OUT O_ERROR_FLAG varchar(1), OUT O_ERROR_MSG Varchar(100))
		
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		in.add(company_id);//I_COMPANY_ID
		in.add(knockOffId);//I_KNOCKOFF_ID
		currentdate=CommonFunction.changeFormat(currentdate);
		in.add(currentdate);//I_CURR_DATE DATE
		in.add(userId);//I_USER_ID
		in.add(decision);//I_STATUS
		in.add(remarks);//I_REMARKS
		out.add(s1);
		out.add(s2);
		   	
		try
		{			
			logger.info("KNOCKOFF_CANCELATION_AUTHOR ("+in.toString()+","+out.toString());
			outMessages=(ArrayList) ConnectionDAO.callSP("KNOCKOFF_CANCELATION_AUTHOR",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);
	        
	        session.removeAttribute("save");
			session.removeAttribute("notSave");
			
	        if(s1.equalsIgnoreCase("S"))
	        	session.setAttribute("save","save");
	        if(s1.equalsIgnoreCase("E"))
	        {
	        	session.setAttribute("notSave","notSave");
	        	session.setAttribute("errorMsg",s2);
	        }	    	  
	    }
		catch (Exception e) {
		e.printStackTrace();}		
		return mapping.findForward("author");
	}
	
	
	public ActionForward openKnockOffCancellationAuthorValues(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		logger.info("In openKnockOffCancellationAuthorValues() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
			userId=userobj.getUserId();
		else
		{
			logger.info("in openKnockOffCancellationAuthorValues method of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");
		
		String loanId ="";
		String knockOffId = "";
		String val="" ;
	
		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
		session.setAttribute("loanId",loanId);
		session.setAttribute("knockOffId",knockOffId);
		
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 	

		session.setAttribute("hideLov","hideLov");
		session.setAttribute("forAuthor","forAuthor");		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffCancellationData(knockOffId,"A","F");
		session.setAttribute("headerList", knockOffSearchList);		
		ArrayList<Object> receivableList = service.getKnockOffDetailsDataMaker(knockOffId,"R","A");
		session.setAttribute("receivableList", receivableList);		
		ArrayList<Object> payableList = service.getKnockOffDetailsDataMaker(knockOffId,"P","A"); 	    		
		session.setAttribute("payableList", payableList);		
		ArrayList<Object> totalReceivable = service.getTotalReceivableR(knockOffId,"A");
		ArrayList<Object> totalPayable = service.getTotalReceivableP(knockOffId,"A");
		session.setAttribute("totalReceivable", totalReceivable);
		session.setAttribute("totalPayable", totalPayable);		
		return mapping.findForward("authorFram");
	}
	public ActionForward openKnockOffCanValuesAuthor(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		logger.info("In openKnockOffCancellationAuthorValues() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
			userId=userobj.getUserId();
		else
		{
			logger.info("in openKnockOffCancellationAuthorValues method of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");		
		String loanId ="";
		String knockOffId = "";
		String val="" ;
	
		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
			
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		
		session.setAttribute("hideLov","hideLov");
		session.setAttribute("forAuthor","forAuthor");		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffCancellationData(knockOffId,"A","F");
		session.setAttribute("headerList", knockOffSearchList);		
		ArrayList<Object> receivableList = service.getKnockOffDetailsDataMaker(knockOffId,"R","A");
		session.setAttribute("receivableList", receivableList);		
		ArrayList<Object> payableList = service.getKnockOffDetailsDataMaker(knockOffId,"P","A"); 	    		
		session.setAttribute("payableList", payableList);		
		ArrayList<Object> totalReceivable = service.getTotalReceivableR(knockOffId,"A");
		ArrayList<Object> totalPayable = service.getTotalReceivableP(knockOffId,"A");
		session.setAttribute("totalReceivable", totalReceivable);
		session.setAttribute("totalPayable", totalPayable);		
		return mapping.findForward("success");
	}
	
	
	public ActionForward openKnockOffCancellationValues(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		logger.info("In openKnockOffCancellationValues() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null)
			userId=userobj.getUserId();
		else
		{
			logger.info("in openKnockOffCancellationValues method of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");
		String loanId ="";
		String knockOffId = "";
		String val="" ;
	
		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));	
		val= CommonFunction.checkNull(request.getParameter("val"));
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		if(val.trim().equals("P"))
			request.setAttribute("hideLov","hideLov");
		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffCancellationData(knockOffId,"A",val);
		request.setAttribute("headerList", knockOffSearchList);		
		ArrayList<Object> receivableList = service.getKnockOffDetailsDataMaker(knockOffId,"R","A");
		request.setAttribute("receivableList", receivableList);		
		ArrayList<Object> payableList = service.getKnockOffDetailsDataMaker(knockOffId,"P","A"); 	    		
		request.setAttribute("payableList", payableList);		
		ArrayList<Object> totalReceivable = service.getTotalReceivableR(knockOffId,"A");
		ArrayList<Object> totalPayable = service.getTotalReceivableP(knockOffId,"A");
		request.setAttribute("totalReceivable", totalReceivable);
		request.setAttribute("totalPayable", totalPayable);	
		return mapping.findForward("success");
	}
	public ActionForward cancelknockOff(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In cancelknockOff() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String makerDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("in cancelknockOff method of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");
	
		String loanId="";
		String knockOffId="";
		String makerRemarks="" ;
		String saveForward="";
		
		loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
		makerRemarks= CommonFunction.checkNull(request.getParameter("makerRemarks"));
		saveForward= CommonFunction.checkNull(request.getParameter("val"));
		logger.info("loanId  :  "+loanId);
		logger.info("knockOffId  :  "+knockOffId);
		logger.info("makerRemarks  :  "+makerRemarks);
		logger.info("makerRemarks Flag :  "+makerRemarks);
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		
		boolean status=false;
		status=service.saveKnockOffCancelData(knockOffId,makerRemarks,userId,makerDate,saveForward);
		logger.info("In cancelknockOff() of KnockOffCancellationDispatchAction  Update Ststus  :  "+status);
		
		ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffCancellationData(knockOffId,"A",saveForward);
		request.setAttribute("headerList", knockOffSearchList);		
		ArrayList<Object> receivableList = service.getKnockOffDetailsDataMaker(knockOffId,"R","A");
		request.setAttribute("receivableList", receivableList);		
		ArrayList<Object> payableList = service.getKnockOffDetailsDataMaker(knockOffId,"P","A"); 	    		
		request.setAttribute("payableList", payableList);		
		ArrayList<Object> totalReceivable = service.getTotalReceivableR(knockOffId,"A");
		ArrayList<Object> totalPayable = service.getTotalReceivableP(knockOffId,"A");
		request.setAttribute("totalReceivable", totalReceivable);
		request.setAttribute("totalPayable", totalPayable);		
		
		session.removeAttribute("save");
		session.removeAttribute("notSave");
		
		if(saveForward.trim().equals("P"))
		{
			if(status)
			{
				request.setAttribute("hideLov","hideLov");
				request.setAttribute("save","save");	
			}
			else
				request.setAttribute("notSave","notSave");	
		}
		if(saveForward.trim().equals("F"))
		{
			if(status)
				request.setAttribute("forward","forward");	
			else
				request.setAttribute("notSave","notSave");	
		}
		return mapping.findForward("success");		
	}	
	
	public ActionForward searchKOCAuthorData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In searchKOCAuthorData() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String makerDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("in searchKOCAuthorData() of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		String knockOffId="";
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<KnockOffMakerVO> KOCList = service.searchKOCData(knockOffId,currentPageLink,"F",userId);
		request.setAttribute("KOCList", KOCList);	
		return mapping.findForward("searchAuthorSuccess");		
		
	}
	
	public ActionForward searchKOCData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In searchKOCData() of KnockOffCancellationDispatchAction");	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String makerDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("in searchKOCData() of  KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		String knockOffId="";
		knockOffId = CommonFunction.checkNull(request.getParameter("knockOffId"));
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<KnockOffMakerVO> KOCList = service.searchKOCData(knockOffId,currentPageLink,"P",userId);
		request.setAttribute("KOCList", KOCList);	
		return mapping.findForward("searchSuccess");	
	}	
	
	//Ritu
	public ActionForward deleteknockOffCancellation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In KnockOffCancellationDispatchAction deleteknockOffCancellation().... ");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String branchId="";
		//String result="success";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				branchId=userobj.getBranchId();
		}else{
			logger.info("here in deleteknockOffCancellation () of KnockOffCancellationDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();

		// String cond = request.getParameter("saveForward");
		// logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+cond);

		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
	
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

		String knockOffID = "";

		knockOffID = request.getParameter("knockOffID");
		
		logger.info("In   KnockOffCancellationDispatchAction ----1-------------->knockOffID " + knockOffID);
		//logger.info("In ConsumerDispatchAction  execute id: " + userobj.getBranchId());

		
		//PaymentDAO service = new PaymentDAOImpl();
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		boolean status = service.deleteKnockOffCancellation(knockOffID);
		String msg="";
		if(status){
        	msg="DS";
			request.setAttribute("msg",msg);
			 
		}
		else{
			msg="DN";
			request.setAttribute("msg",msg);
		}
		return mapping.findForward("success");
	}
}
