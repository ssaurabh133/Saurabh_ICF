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
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.CaseMarkingMakerDAO;
import com.cm.vo.CaseMarkingMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class CaseMarkingMakerDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CaseMarkingMakerDispatchAction.class.getName());
	
	public ActionForward insertCrCaseMarkingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside CaseMarkingMakerDispatchAction........insertCrCaseMarkingDetails");
		HttpSession session =  request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		String businessDate =null;
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		
		
		boolean statusIns;
		String statusCheck=null;
		DynaValidatorForm CaseMarkingMakerDynaValidatorForm = (DynaValidatorForm)form;
		CaseMarkingMakerVO vo = new CaseMarkingMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CaseMarkingMakerDynaValidatorForm);

		vo.setUserId(userId);
		vo.setMakerDate(businessDate);
		
		CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());

		statusCheck=dao.checkLoanNo(vo);
		
		String checkListIds = request.getParameter("checkListIds");
		String checkListIds1 = request.getParameter("checkListIds1");
		String checkListIds2 = request.getParameter("checkListIds2");
		String checkListIds3 = request.getParameter("checkListIds3");
		String checkListIds4 = request.getParameter("checkListIds4");
		String checkListIds5 = request.getParameter("checkListIds5");
		
		String[] checkList= checkListIds.split("\\|");
        String[] checkList1= checkListIds1.split("\\|");
        String[] checkList2= checkListIds2.split("\\|");
        String[] checkList3= checkListIds3.split("\\|");
        String[] checkList4= checkListIds4.split("\\|");
        String[] checkList5= checkListIds5.split("\\|");
  
        if(CommonFunction.checkNull(checkList).equalsIgnoreCase(""))
        	if(checkList!=null)
        logger.info("## In insertCrCaseMarkingDetails(): docId :==>> "+checkList.length);
        ArrayList list= new ArrayList();
        list.add(vo);
        statusIns=dao.insertCrCaseMarkingDetails(vo,checkList,checkList1,checkList2,checkList3,checkList4,checkList5);

		String sms=null;
		// logger.info("## In statusIns()==>> "+statusIns);
		if(statusIns){
			
        	sms="S";
			request.setAttribute("message",sms);
			request.setAttribute("loanId", vo.getLbxDealNo()); 
			request.setAttribute("status", vo.getStatusCase()); 
			request.setAttribute("canForward", "canForward");
	}
		else
		{	sms="E";
			request.setAttribute("message",sms);

		}
		dao=null;
		vo=null;
		checkListIds=null;
		checkListIds1=null;
		checkListIds2=null;
		checkListIds3=null;
		checkListIds4=null;
		checkListIds5=null;
		checkList=null;
		checkList1=null;
		checkList2=null;
		checkList3=null;
		checkList4=null;
		checkList5=null;
		statusCheck=null;
		form.reset(mapping, request);
		return mapping.findForward("save");
	}
	
	public ActionForward newAddCaseMarking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in newAddCaseMarking()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				String strFlag=null;
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
				CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				ArrayList list1= dao.getCaseMarkingFlagList(); 
				request.setAttribute("list1", list1);
				strFlag=null;
				dao=null;
				form.reset(mapping, request);
			    return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward updateCrCaseMarkingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In updateCrCaseMarkingDetails.......");
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String bDate=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		else{
			
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		String strFlag=null;
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
		
		
		CaseMarkingMakerVO vo=new CaseMarkingMakerVO(); 
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);	
		vo.setUserId(userId);
		vo.setMakerDate(bDate);
		
		CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
		String checkListIds = request.getParameter("checkListIds");
		String checkListIds1 = request.getParameter("checkListIds1");
		String checkListIds2 = request.getParameter("checkListIds2");
		String checkListIds3 = request.getParameter("checkListIds3");
		String checkListIds4 = request.getParameter("checkListIds4");
		String checkListIds5 = request.getParameter("checkListIds5");
		String checkListIds6 = request.getParameter("checkListIds6");
		
		logger.info("checkListIds .............. "+checkListIds);
		logger.info("checkListIds1 .............. "+checkListIds1);
		logger.info("checkListIds2 .............. "+checkListIds2);
		logger.info("checkListIds3 .............. "+checkListIds3);
		logger.info("checkListIds4 .............. "+checkListIds4);
		logger.info("checkListIds5 .............. "+checkListIds5);
		logger.info("checkListIds6 .............. "+checkListIds6);
		
        String[] checkList= checkListIds.split("\\|");
        String[] checkList1= checkListIds1.split("\\|");
        String[] checkList2= checkListIds2.split("\\|");
        String[] checkList3= checkListIds3.split("\\|");
        String[] checkList4= checkListIds4.split("\\|");
        String[] checkList5= checkListIds5.split("\\|");
        String[] checkList6= checkListIds6.split("\\|");
        
        if(CommonFunction.checkNull(checkList).equalsIgnoreCase(""))
        if(checkList!=null)
        logger.info("## In saveRepoAsset(): docId :==>> "+checkList.length);
        boolean status=dao.updateCrCaseMarkingDetails(vo,checkList,checkList1,checkList2,checkList3,checkList4,checkList5,checkList6);
        
        
        String sms=null;
        if(status){
        	sms="M";
			request.setAttribute("message",sms);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("loanId", vo.getLbxDealNo()); 
			request.setAttribute("status", vo.getStatusCase());
			request.setAttribute("canForward", "canForward");
			}
        else
			{
				sms="E";
				request.setAttribute("message",sms);
				request.setAttribute("editVal", "editVal");
			}
			request.setAttribute("message",sms);
			ArrayList<CaseMarkingMakerVO> list =new ArrayList<CaseMarkingMakerVO>();
			list.add(vo);
		
			request.setAttribute("editVal", "editVal");
			//request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
		
		
		strFlag=null;
		sms=null;
		dao=null;
		checkList=null;
		checkList1=null;
		checkList2=null;
		checkList3=null;
		checkList4=null;
		checkList5=null;
		checkList6=null;
		checkListIds=null;
		checkListIds1=null;
		checkListIds2=null;
		checkListIds3=null;
		checkListIds4=null;
		checkListIds5=null;
		checkListIds6=null;
		form.reset(mapping, request);
		return mapping.getInputForward();
      }
	
	public ActionForward search(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String branchId=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
		return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");
		
		CaseMarkingMakerVO caseMarkingCheckerVO=new CaseMarkingMakerVO();
	    Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-2387997-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		//logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		caseMarkingCheckerVO.setCurrentPageLink(currentPageLink);
	
		CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
	    logger.info("Implementation class: "+dao.getClass());
	    String statusCase = "P";
	    logger.info("+request.getParameter(statusCase)........ "+request.getParameter("statusCase"));
	    DynaValidatorForm CaseMarkingCheckerDynaValidatorFormSearch = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(caseMarkingCheckerVO, CaseMarkingCheckerDynaValidatorFormSearch);
		if(CommonFunction.checkNull(caseMarkingCheckerVO.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			caseMarkingCheckerVO.setReportingToUserId(userId);
		  
		}
		//logger.info("user Id:::::"+caseMarkingCheckerVO.getReportingToUserId());
		caseMarkingCheckerVO.setBranchId(branchId);
		caseMarkingCheckerVO.setUserId(userId);
		ArrayList<CaseMarkingMakerVO> searchCaseMarkingMakerkerList= dao.searchCaseMarkingMaker(caseMarkingCheckerVO,statusCase);
		request.setAttribute("detailList", searchCaseMarkingMakerkerList);
		//logger.info("searchListGrid    Size:---"+searchCaseMarkingMakerkerList.size());
		session.setAttribute("caseAuthorUserId",userId);
		caseMarkingCheckerVO=null;
		dao=null;
		strFlag=null;
		form.reset(mapping, request);
		return mapping.findForward("success");	
	}
	public ActionForward openEditCaseMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception { 
		
		logger.info("In openEditCaseMarkingMaker::::::::");
		        ServletContext context = getServlet().getServletContext();
		     	HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String bDate=null;
				if(userobj!=null)
				{
						bDate=userobj.getBusinessdate();
				}else{
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
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
						CaseMarkingMakerVO vo=new CaseMarkingMakerVO();
						
						vo.setBusinessdate(bDate);
						vo.setLbxDealNo(request.getParameter("loanId"));
						vo.setStatusCase(request.getParameter("status"));
						CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass());
						ArrayList<CaseMarkingMakerVO> list = dao.openEditCaseMarkingMaker(vo);
						
						request.setAttribute("list", list);
						if(!((CommonFunction.checkNull(vo.getStatusCase())).equalsIgnoreCase("A")))
						{
							request.setAttribute("editVal", "editVal");
						}
						request.setAttribute("canForward", "canForward");
						dao=null;
						strFlag=null;
						vo=null;
						form.reset(mapping, request);
					return mapping.findForward("editCaseMarking");	
		}
	
	public ActionForward forwardCaseMarkingMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In forwardCaseMarkingMaker.......");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		String bDate=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}else{
		return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		String strFlag=null;
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
	
		CaseMarkingMakerVO vo=new CaseMarkingMakerVO(); 
		DynaValidatorForm dynaForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dynaForm);	
		vo.setUserId(userId);
		vo.setMakerDate(bDate);
		CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
		//shivesh
		vo.setStatusCase("P");
		//shivesh
		ArrayList<Object> status=dao.forwardCaseMarkingMaker(vo);
       
		String s1=(String)status.get(1);
	
		String sms=null;
		if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
		{
			//logger.info("s1"+s1);
			sms="F";
			request.setAttribute("message",sms);
		}
		else{
			//logger.info("s122222222222"+s1);
				sms="E";
				request.setAttribute("message",sms);
			}
			ArrayList<CaseMarkingMakerVO> list =new ArrayList<CaseMarkingMakerVO>();
			list.add(vo);
			//logger.info("In update case detail list"+ list.size());
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
			sms=null;
			 dao=null;
			 vo=null;
			 strFlag=null;
			 s1=null;
						
		    form.reset(mapping, request);
			return mapping.getInputForward();
      }
	
	public ActionForward deleteCaseMarking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in deleteCaseMarking()");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null)
				{
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				String strFlag=null;
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
				String caseMarkingLoanId=CommonFunction.checkNull(request.getParameter("caseMarkingLoanId"));
			    String tableStatus=CommonFunction.checkNull(request.getParameter("statusCase"));
			    //logger.info("caseMarkingLoanId: "+caseMarkingLoanId+" tableStatus: "+tableStatus);
				CaseMarkingMakerDAO dao=(CaseMarkingMakerDAO)DaoImplInstanceFactory.getDaoImplInstance(CaseMarkingMakerDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				String deleteMsg= dao.deleteCaseMarking(caseMarkingLoanId,tableStatus); 
				request.setAttribute("deleteMsg", deleteMsg);
				form.reset(mapping, request);
				caseMarkingLoanId=null;
				tableStatus=null;
				dao=null;
				 form.reset(mapping, request);
			    return mapping.findForward("deleteSuccess");	
			}
	
	
}
