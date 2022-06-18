package com.masters.actions;

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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.masters.vo.CrSchemeMasterVo;



public class CrSchemeDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(CrSchemeDispatchAction.class.getName());
	
	public ActionForward searchSchemeCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		
		DynaValidatorForm crSchemeMasterDynaValidatorForm=(DynaValidatorForm)form;
		CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crSchemeMasterDynaValidatorForm);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String chargeCode=(String)crSchemeMasterVo.getSchemeId();
		logger.info("In CrSchemeDispatchAction-->searchSchemeCode bankBranchCode.. "+chargeCode);
		
		ArrayList<CrSchemeMasterVo> detailList = cpm.searchScemeCodeDao(crSchemeMasterVo);
		
		request.setAttribute("list", detailList);
	
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
		
		return mapping.findForward("searchScheme");
	
	}
	
	public ActionForward addScemeCodeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
				
				logger.info(" IN CrSchemeDispatchAction addScemeCodeDetails()....");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				//
				DynaValidatorForm crSchemeMasterDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crSchemeMasterDynaValidatorForm);
					
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList repaymentModeList=cpm.getRepaymentModeList();
				request.setAttribute("repaymentModeList", repaymentModeList);
				request.setAttribute("save", "save");
			    return mapping.findForward("addScemeCodeDetails");	
			}
	
	
//Scheme master insert 
	
	public ActionForward saveSchemeCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
		//HttpSession session=request.getSession(false);
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		DynaValidatorForm crSchemeMasterDynaValidatorForm=(DynaValidatorForm)form;
		CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
		String allBranch=request.getParameter("allselection");
		crSchemeMasterVo.setAllselection(allBranch);
		
		
		String[] rateMethod=request.getParameterValues("rateMethod");
		String[] reviewEvent=request.getParameterValues("reviewEvnet");
		String rwEve="";
		String ratMet="";
		if(rateMethod==null)
			ratMet = "";
		else
			ratMet = CommonFunction.checkNull(rateMethod[0]);
		
		if(reviewEvent==null)
			rwEve = "";
		else
			rwEve = CommonFunction.checkNull(reviewEvent[0]);
		
		crSchemeMasterVo.setMakerId(userId);
		crSchemeMasterVo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crSchemeMasterDynaValidatorForm);
		String msg="";
		String procval="";

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		procval = cpm.insertSchemeCodeMaster(crSchemeMasterVo,ratMet,rwEve);
		//if(result){
		if((procval.equalsIgnoreCase("S"))){
			msg="S";
			 request.setAttribute("msg",msg);
			// request.setAttribute("procval", procval);
		}else{
			msg="E";
			request.setAttribute("msg",msg);
			 request.setAttribute("procval", procval);
		}
		logger.info("In CrSchemeDispatchAction saveSchemeCode msg is.... "+msg);
		   return mapping.getInputForward();
		}

	//update mode for scheme master(getting data)
	
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In CrSchemeDispatchAction modifyDetails()....");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				
				DynaValidatorForm crSchemeMasterDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crSchemeMasterDynaValidatorForm);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String schemeId = request.getParameter("schemeId");
				
					
				ArrayList<CrSchemeMasterVo> detailList = cpm.modifySchemeDetailsDao(crSchemeMasterVo);
				ArrayList<CrSchemeMasterVo> branchList = cpm.getBranchIdsDao(crSchemeMasterVo.getSchemeId());
				request.setAttribute("branchList", branchList);
				
				crSchemeMasterVo=detailList.get(0);
				
				
				crSchemeMasterVo.setSchemeId(schemeId);
				
				logger.info("checking Status reports: "+crSchemeMasterVo.getStatus()+"--"+crSchemeMasterVo.getReschAllowed()+"--"+crSchemeMasterVo.getDeferralAllowed()+"--"+crSchemeMasterVo.getPrepayAllowed()+"--"+crSchemeMasterVo.getAdditionalDisbAllowed());
			//	logger.info("crSchemeMasterVo.getSelectionAccecc() >>>:: " + crSchemeMasterVo.getSelectionAccecc());
				request.setAttribute("selectionAccecc", crSchemeMasterVo.getSelectionAccecc());
				request.setAttribute("list", detailList);	
				request.setAttribute("status",crSchemeMasterVo.getStatus());
				request.setAttribute("reschAllowed",crSchemeMasterVo.getReschAllowed());
				request.setAttribute("deferralAllowed",crSchemeMasterVo.getDeferralAllowed());
				request.setAttribute("prepayAllowed",crSchemeMasterVo.getPrepayAllowed());
				request.setAttribute("terminationAllowed",crSchemeMasterVo.getTerminationAllowed());
				
				request.setAttribute("rateType",crSchemeMasterVo.getRateType());
				request.setAttribute("rateMethod",crSchemeMasterVo.getRateMethod());
				request.setAttribute("repaymentFreq",crSchemeMasterVo.getRepaymentFreq());
				request.setAttribute("installmentType",crSchemeMasterVo.getInstallmentType());
				request.setAttribute("repaymentMode", crSchemeMasterVo.getRepaymentMode());
				request.setAttribute("installmentMode", crSchemeMasterVo.getInstallmentMode());
				
				request.setAttribute("modify", "modify");
				
				request.setAttribute("additionalDisbAllowedStatus", crSchemeMasterVo.getAdditionalDisbAllowed());
				//ArrayList repaymentModeList=cpm.getRepaymentModeList();
				request.setAttribute("repaymentModeList", cpm.getRepaymentModeList());
				
			   return mapping.findForward("modifyDetails");	
			}
	
//update save Code for schme master	
	public ActionForward saveModifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In CrSchemeDispatchAction saveModifyDetails().... ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				String strFlag="";	
				
				String userId="";
					String bDate="";
					if(userobj!=null)
					{
							userId=userobj.getUserId();
							bDate=userobj.getBusinessdate();
					}
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
				
				DynaValidatorForm crSchemeMasterDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crSchemeMasterDynaValidatorForm);
					
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String allBranch=request.getParameter("allselection");
				//logger.info("in all branch>>>>>>>>>>>"+allBranch);
				crSchemeMasterVo.setAllselection(allBranch);  
				
				String[] rateMethod=request.getParameterValues("rateMethod");
				String[] reviewEvent=request.getParameterValues("reviewEvnet");
				String rwEve="";
				String ratMet="";
				if(rateMethod==null)
					ratMet = "";
				else
					ratMet = CommonFunction.checkNull(rateMethod[0]);
				
				if(reviewEvent==null)
					rwEve = "";
				else
					rwEve = CommonFunction.checkNull(reviewEvent[0]);
				
				
				String schemeId = request.getParameter("schemeId");
				crSchemeMasterVo.setSchemeId(schemeId);
				String msg="";
				crSchemeMasterVo.setMakerId(userId);
				crSchemeMasterVo.setMakerDate(bDate);
				boolean result = cpm.saveModifySchemeDetailsDao(crSchemeMasterVo,ratMet,rwEve);
				logger.info("in update action");

				if(result){				
					msg="M";
					 request.setAttribute("msg",msg);
				}else{
					msg="E";
					request.setAttribute("msg",msg);
					
				}
				logger.info("In CrSchemeDispatchAction saveModifyDetails msg is.... "+msg);
				//request.setAttribute("modify", "modify");
			   return mapping.getInputForward();
			}
	
	public ActionForward stageMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In CrSchemeDispatchAction stageMapping().... ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				request.setAttribute("save", "save");
				CrSchemeMasterVo vo = new CrSchemeMasterVo();
				
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();
				vo.setMakerId(userId);

				ArrayList list=new ArrayList();
				list=masterDAO.stageDetailMapping(vo);
			
				request.setAttribute("list",list);
		      return mapping.findForward("stageMapping");

			}
	
	public ActionForward accountMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In CrSchemeDispatchAction accountMapping().... ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				DynaValidatorForm crAccountDetailDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo vo = new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, crAccountDetailDynaValidatorForm);
				
				MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();
				String schemeId = request.getParameter("schemeId");
				logger.info("schemeId...."+schemeId);
				vo.setSchemeId(schemeId);
				ArrayList list=new ArrayList();
				list=masterDAO.accountDetailMapping(vo);
				request.setAttribute("list",list);
				request.setAttribute("schemeId",schemeId);
		      return mapping.findForward("accountMapping");

			}
	
	
	public ActionForward saveAccountDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
			
				logger.info("In CrSchemeDispatchAction saveAccountDetails().... ");
				
				DynaValidatorForm crAccountDetailDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crAccountDetailDynaValidatorForm);
				
				crSchemeMasterVo.setMakerId(userId);
				String accountFlag  = CommonFunction.checkNull(request.getParameter("accountFlagList"));
				logger.info("accountFlag...."+accountFlag);
		        String[] accountFlagList= accountFlag.split("/");
		        
				String checkbox[] = CommonFunction.checkNull(request.getParameter("stagId")).split("/");
				String msg="";
		        logger.info("accountFlagList.............."+accountFlagList);
		        MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();	
				
				boolean result = masterDAO.saveAccountDtl(accountFlagList,checkbox,crSchemeMasterVo);
				if(result){				
					msg="S";
					 request.setAttribute("msg",msg);
				}else{
					msg="E";
					request.setAttribute("msg",msg);
				}

		      return mapping.findForward("saveAcDetail");

			}

	public ActionForward modifyAccountDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
			
				logger.info("In CrSchemeDispatchAction modifyAccountDetails().... ");
				ServletContext context = getServlet().getServletContext();
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
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
				
				String userId="";
				String bDate="";
				if(userobj!=null)
				{
						userId=userobj.getUserId();
						bDate=userobj.getBusinessdate();
				}
				
				DynaValidatorForm crAccountDetailDynaValidatorForm=(DynaValidatorForm)form;
				CrSchemeMasterVo crSchemeMasterVo= new CrSchemeMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(crSchemeMasterVo, crAccountDetailDynaValidatorForm);
				crSchemeMasterVo.setMakerId(userId);
				String schemId = request.getParameter("schemId");
				logger.info("schemId...."+schemId);
				crSchemeMasterVo.setSchemeId(schemId);
				
				String accountFlag  = CommonFunction.checkNull(request.getParameter("accountFlagList"));
				logger.info("accountFlag...."+accountFlag);
		        String[] accountFlagList= accountFlag.split("/");
		        
				String checkbox[] = CommonFunction.checkNull(request.getParameter("stagId")).split("/");
				String msg="";
		        logger.info("accountFlagList.......checkbox..length....."+checkbox.length);
		        MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+masterDAO.getClass());
				//MasterDAO masterDAO = new MasterDAOImpl();	
				
				boolean result = masterDAO.modifyAccountDtl(accountFlagList,checkbox,crSchemeMasterVo);
				if(result){				
					msg="M";
					 request.setAttribute("msg",msg);
				}else{
					msg="E";
					request.setAttribute("msg",msg);
					
				}

		      return mapping.findForward("modifyAcDetail");

			}
}
