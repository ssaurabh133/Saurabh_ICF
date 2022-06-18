/**
 * Author Name-      Prashant Kumar
      Date of creation -15/04/2011
      Purpose-          Entry of Leads
      Documentation-     
 */

package com.cp.actions;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.CreditProcessingLeadEntryVo;
import com.cp.vo.LeaddetailDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class LeadEntryProcessAction extends Action {
	private static final Logger logger = Logger.getLogger(LeadEntryProcessAction.class.getName());
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("In LeadEntryProcessAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		int compId=0;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				compId= userobj.getCompanyId();
		}else{
			logger.info("here in execute method of LeadEntryProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		String lbxLeadNo=request.getParameter("lbxLeadNo");
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
	if(isTokenValid(request,true))
	{
		ArrayList leadInfo = new ArrayList();
		int maxId=0;
		CreditProcessingDAO creditProcessingDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessingDAO.getClass()); 			// changed by asesh
		//CreditProcessingDAO creditProcessingDAO = new CreditProcessingDAOImpl();
		logger.debug("In CreditProcessiongLeadEntryAction saveSourcingProcessDetails() ");		
		DynaValidatorForm SourcingProcessDynaValidatorForm = (DynaValidatorForm) form;
		CreditProcessingLeadEntryVo creditProcessingLeadEntryVo = new CreditProcessingLeadEntryVo();
		creditProcessingLeadEntryVo.setUserId("" + userId);
		creditProcessingLeadEntryVo.setBussinessDate(bDate);
		creditProcessingLeadEntryVo.setCompanyId(""+compId);
		org.apache.commons.beanutils.BeanUtils.copyProperties(creditProcessingLeadEntryVo, SourcingProcessDynaValidatorForm);		
		creditProcessingLeadEntryVo.setMakerId(userId);
		creditProcessingLeadEntryVo.setMakerDate(bDate);
		String functionId="";
		if(session.getAttribute("functionId")!=null){
			
			functionId=session.getAttribute("functionId").toString();
		}
		creditProcessingLeadEntryVo.setMakerIdStatus(functionId);
		
		String groupId = "";
		ArrayList<CreditProcessingLeadEntryVo> list1 = new ArrayList<CreditProcessingLeadEntryVo>();
		boolean applformNo=creditProcessingDAO.checkApplicationFormNo(creditProcessingLeadEntryVo.getApplicationFormNo(),creditProcessingLeadEntryVo.getDealId());
		logger.info("In insert saveCPLeadEntry"+applformNo);
		if(!applformNo)
		{
			  maxId = creditProcessingDAO.saveCPLeadEntry(creditProcessingLeadEntryVo);
			  String dealNoStatus="";
			  if(creditProcessingLeadEntryVo.getDealNo()!=null && !CommonFunction.checkNull(creditProcessingLeadEntryVo.getDealNo()).equalsIgnoreCase(""))
			  {
				  StringBuilder query=new StringBuilder();
				  query.append("SELECT DEAL_ID FROM cr_deal_dtl WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(creditProcessingLeadEntryVo.getDealId()).trim())+"'");
				  dealNoStatus=ConnectionDAO.singleReturn(query.toString());
			  }
			  if(dealNoStatus.equalsIgnoreCase(""))
			  {
				  
			  if(!CommonFunction.checkNull(lbxLeadNo).equals("")){
				  ArrayList<LeaddetailDealVo> list=creditProcessingDAO.CustomerDetailsList(lbxLeadNo);
				  if(list.size()>0)
				  {
					 String productId="";
					 String schemeId="";
					 LeaddetailDealVo vo=list.get(0);
					 vo.setMakerDate(bDate);
					 vo.setMakerId(userId);
					 String dealId=maxId+"";
					 vo.setDealId(dealId);
					// GcdGroupMasterVo gvo=new GcdGroupMasterVo();
					 
					 if(list.get(0).getLeadGroupType().equalsIgnoreCase("N")){
						 vo.setLeadGroupName(list.get(0).getLeadGroupName());
						 vo.setLeadGroupType(list.get(0).getLeadGroupType());
						 vo.setGroupId("");
						 //gvo.setRecStatus("on");
						// gvo.setMakerDate(bDate);
						 //gvo.setMakerId(userId);
						 //gvo.setGroupExposureLimit("0");
						/* MasterDAO dao = new MasterDAOImpl();	
						groupId = dao.insertGroupCodeMaster(gvo);
						 vo.setGroupId(groupId);*/
					 }
					 else
					 {
						 vo.setLeadGroupType(list.get(0).getLeadGroupType());
						 vo.setGroupId(list.get(0).getGroupId());
						 vo.setLeadGroupName("");
						 
					 }
					// logger.info("-------getGroupId---------->"+vo.getGroupId());
					 String customerId= creditProcessingDAO.saveLeadCustomerDetails(vo);
					 logger.info("custStatus---------------->"+customerId);
					 vo.setBpId(customerId);
					 boolean addStatus=creditProcessingDAO.saveLeadCustomerAddressDetails(vo);
					 logger.info("addStatus------>"+addStatus);
					 boolean linkStatus=creditProcessingDAO.linkLeadCustomer(vo);
					 logger.info("linkStatus------>"+linkStatus);
					 boolean updateDealStatus=creditProcessingDAO.updateDealCustomer(vo);
					 logger.info("updateDealStatus------>"+updateDealStatus);
				
					 
				
				  }
			  }
			  }
		
		  }
	      
		if (maxId == 0 && applformNo) {
			logger.info("In LeadEntryProcessAction(execute) maxId=0");
			request.setAttribute("appl", "A");
			leadInfo.add(creditProcessingLeadEntryVo);
			
			return mapping.getInputForward();
		}
		else if(maxId == 0)
		{
			request.setAttribute("NotSaved", "X");
			saveToken(request);// Save Token Before Loading jsp in any case
			return mapping.getInputForward();
		}
		
		ArrayList<ApplicantTypeVO> applist = creditProcessingDAO.getApplicantList();
		session.setAttribute("applist", applist);
		
		ArrayList dealHeader = creditProcessingDAO.getDealHeader("" + maxId);
		session.setAttribute("dealHeader", dealHeader);
		request.setAttribute("moveTosecond", "moveTosecond");
		session.setAttribute("maxId", maxId);
		
		logger.info("maxId/dealId: " + maxId);
		ArrayList<Object> leadInfo1 = creditProcessingDAO.getLeadEntryList(""+maxId);
		request.setAttribute("leadInfo", leadInfo1);
		Iterator<Object> it = leadInfo1.iterator();
		int i=0;
		while(i<leadInfo.size()-1)
		{
			list1.add((CreditProcessingLeadEntryVo) it.next());
			i++;
		}

		CreditProcessingLeadEntryVo  tb1 = (CreditProcessingLeadEntryVo) it.next();
		logger.info(".............. "+tb1.getLeadNo());
		session.setAttribute("leadNo", tb1.getLeadNo());
		
	}

	saveToken(request);// Save Token Before Loading jsp in any case
	return mapping.getInputForward();
 }

}
