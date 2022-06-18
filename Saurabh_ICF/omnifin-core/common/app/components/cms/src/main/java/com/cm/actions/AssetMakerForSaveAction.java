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

import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetMakerForSaveAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AssetMakerForSaveAction.class.getName());	
	
	public ActionForward onSaveAssetMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" in onSaveAssetMaker----------");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in onSaveAssetMaker method of AssetMakerForSaveAction action the session is out----------------");
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
		UserObject sessUser=(UserObject)session.getAttribute("userobject");
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        //end by sachin
//		assetInsuranceDAO dao = new assetInsuranceDAOImpl();
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		String userId="";
		String bDate="";
		 String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		 if("".equalsIgnoreCase(loanId) )
			 loanId = assetMakervo.getLbxLoanNoHID();
		if(sessUser!=null)
		{
			userId=sessUser.getUserId();
			bDate=sessUser.getBusinessdate();
		}
		assetMakervo.setMakerId(userId);
		assetMakervo.setBusinessDate(bDate);
		logger.info("getBusinessDate():-"+assetMakervo.getBusinessDate());
		String afterWarning=CommonFunction.checkNull(request.getParameter("afterWarning"));
		assetMakervo.setAfterWarning(afterWarning);
		assetMakervo.setLoanId(loanId);
		String result="";
		 String msg="";		
			 result=dao.getSaveAssetMaker(assetMakervo);
			 logger.info("result"+result);
			 if(CommonFunction.checkNull(result).equalsIgnoreCase("Y")){
				 
				 msg="S";
				 logger.info("00msg"+msg);
				 String assetInsuranceId=dao.getAssetInsuranceId();	
				 request.setAttribute("assetInsuranceId", assetInsuranceId);
				 logger.info("onSaveAssetMaker assetInsuranceId"+assetInsuranceId);
				 assetMakervo.setAssetInsuranceId(assetInsuranceId);
				 ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
				 request.setAttribute("assetMakerList", assetMakerList);				
				 ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
				ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
				request.setAttribute("insuranceDoneByList",insuranceDoneByList);
				CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
				ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
				request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
				request.setAttribute("msg", msg);
			}else if(CommonFunction.checkNull(result).equalsIgnoreCase("N")){				
				 	msg="comAlEx";
				 	logger.info("00msg"+msg);
				 	ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
				 	assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList", assetMakerList);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("msg", msg);
					request.setAttribute("errorAtSave", msg);
			 } else if(CommonFunction.checkNull(result).equalsIgnoreCase("I")){				
				 	msg="insCheck";
				 	logger.info("00msg"+msg);
				 	ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
				 	assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList", assetMakerList);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("msg", msg);
					request.setAttribute("errorAtSave", msg);
			 }
			 else if(CommonFunction.checkNull(result).equalsIgnoreCase("W")){				
				 	msg="sameYearWarning";
				 	logger.info("00msg"+msg);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					ArrayList<AssetForCMVO> assetMakerList=new ArrayList<AssetForCMVO>();
					assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList",assetMakerList);
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("msg", msg);
					request.setAttribute("errorAtSave", msg);
			 }
			 else if(CommonFunction.checkNull(result).equalsIgnoreCase("DATAMANDATORY")){				
				 	msg="entityTypeCheck";
				 	logger.info("Asset Maker Save Msg : " + msg);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					ArrayList<AssetForCMVO> assetMakerList=new ArrayList<AssetForCMVO>();
					assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList",assetMakerList);
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("msg", msg);
					request.setAttribute("errorAtSave", msg);
			 }
			 else{
				 msg="E";
				 logger.info("00msg"+msg);
				 	ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
				 	assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList", assetMakerList);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("errorAtSave", msg);
				 request.setAttribute("msg", msg);
			 }
			 ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
			 request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
			return mapping.findForward("SUCCESS");
	}
	
	public ActionForward updateonSaveAssetMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in updateonSaveAssetMaker..........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in updateonSaveAssetMaker method of AssetMakerForSaveAction action the session is out----------------");
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
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());     
		AssetForCMVO assetMakervo = new AssetForCMVO();
		UserObject sessUser=(UserObject)session.getAttribute("userobject");
		String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		String recStatus= CommonFunction.checkNull(request.getParameter("recStatus"));
		String loanId = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
		logger.info("recStatus ::::: "+recStatus); 
		assetMakervo.setRecStatus(recStatus);
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		String userId="";
		String bDate="";
		if(sessUser!=null)
		{
			userId=sessUser.getUserId();
			bDate=sessUser.getBusinessdate();
		}
		assetMakervo.setMakerId(userId);
		assetMakervo.setBusinessDate(bDate);
		logger.info("getBusinessDate(): "+assetMakervo.getBusinessDate());
		String afterWarning=CommonFunction.checkNull(request.getParameter("afterWarning"));
		assetMakervo.setAfterWarning(afterWarning);
		String result="";
	    String msg="";
		result=dao.saveForwardDataUpdate(assetMakervo);
		logger.info("result"+result);
	if(CommonFunction.checkNull(result).equalsIgnoreCase("Y")){
			msg="S";
			logger.info("00msg"+msg);
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
			//ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
		 	//assetMakerList.add(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);				
			ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
			request.setAttribute("YearNo", yearNo);
			request.setAttribute("msg", msg);
			logger.info("result"+result);
		}else if(CommonFunction.checkNull(result).equalsIgnoreCase("N")){				
		 	msg="comAlEx";
		 	logger.info("00msg"+msg);
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
		 	//ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
		 	//assetMakerList.add(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);	
		    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			request.setAttribute("msg", msg);
	 } else if(CommonFunction.checkNull(result).equalsIgnoreCase("I")){				
		 	msg="insCheck";
		 	logger.info("00msg"+msg);
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
		 	//ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
		 	//assetMakerList.add(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);	
		    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			request.setAttribute("msg", msg);
	 }else if(CommonFunction.checkNull(result).equalsIgnoreCase("W")){				
		 	msg="sameYearWarningUpdate";
		 	logger.info("00msg"+msg);
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
		 	//ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
		 	//assetMakerList.add(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);	
		    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			request.setAttribute("msg", msg);
	 }	
	 else if(CommonFunction.checkNull(result).equalsIgnoreCase("DATAMANDATORY")){				
				 	msg="entityTypeCheck";
				 	logger.info("Asset Maker Save Msg : " + msg);
				    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
					request.setAttribute("insuranceDoneByList",insuranceDoneByList);
					CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
					ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
					ArrayList<AssetForCMVO> assetMakerList=new ArrayList<AssetForCMVO>();
					assetMakerList.add(assetMakervo);
					request.setAttribute("assetMakerList",assetMakerList);
					request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
					ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
					request.setAttribute("YearNo", yearNo);
					request.setAttribute("msg", msg);
			 }
		else{
		 msg="E";
		 logger.info("00msg"+msg);
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
		 	//ArrayList<AssetForCMVO> assetMakerList= new ArrayList<AssetForCMVO>();
		 	//assetMakerList.add(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);	
		    ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		    request.setAttribute("msg", msg);
	 }
		 ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
		 request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
		return mapping.findForward("SUCCESS");
  }
	
	public ActionForward onSaveForwardofAssetMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in onSaveForwardofAssetMaker..........");
		
		HttpSession session =  request.getSession();
		String userId="";
		String bDate="";
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null){
			
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();			
		}
		else
		{
			logger.info("here in onSaveForwardofAssetMaker method of AssetMakerForSaveAction action the session is out----------------");
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

	
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
	
		assetMakervo.setMakerId(userId);
		assetMakervo.setBusinessDate(bDate);
		logger.info("getBusinessDate():-"+assetMakervo.getBusinessDate());
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String recStatus= CommonFunction.checkNull(request.getParameter("recStatus"));
        String loanId = CommonFunction.checkNull(request.getParameter("lbxLoanNoHID"));
		logger.info("LbxEntity ::::: "+assetMakervo.getLbxEntity()); 
		assetMakervo.setRecStatus(recStatus);
		assetMakervo.setLoanId(loanId);
		String afterWarning=CommonFunction.checkNull(request.getParameter("afterWarning"));
		assetMakervo.setAfterWarning(afterWarning);
		 String result="";
		 String msg="";		 		 
		     
				 result=dao.saveForwardDataUpdate(assetMakervo);
				 logger.info("result"+result);
				 if(CommonFunction.checkNull(result).equalsIgnoreCase("Y")){
					 msg="SF";
					 logger.info("00msg"+msg);
					 ArrayList<AssetForCMVO> assetMakerList=new ArrayList<AssetForCMVO>();
					 assetMakerList.add(assetMakervo);
					 request.setAttribute("assetMakerList", assetMakerList);					 
				 }else if(CommonFunction.checkNull(result).equalsIgnoreCase("I")){
					    msg="insCheck";
					 	logger.info("00msg"+msg);
				 }
                 else if(CommonFunction.checkNull(result).equalsIgnoreCase("W")){
                	 msg="sameYearWarningForward";
         		 	 logger.info("00msg"+msg);
				 }
                 else if(CommonFunction.checkNull(result).equalsIgnoreCase("N")){
                		msg="comAlEx";
            		 	logger.info("00msg"+msg);
				 }
				 else if(CommonFunction.checkNull(result).equalsIgnoreCase("DATAMANDATORY")){				
				 	msg="entityTypeCheck";
				 	logger.info("Asset Maker Save Msg : " + msg);
			 }
			    else{
				msg="E";			
			    logger.info("msg1"+msg);			   
			    }	 
			request.setAttribute("updat", "update");
			ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
			request.setAttribute("assetMakerList", assetMakerList);	
			ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
			request.setAttribute("insuranceDoneByList",insuranceDoneByList);
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
			ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
			request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
			request.setAttribute("msg", msg);
			logger.info("In saveForwardData,,,,,"+result);
			ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
			request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
			return mapping.findForward("Forward");
    }        

	public ActionForward cancelAsset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		logger.info(" in cancelAsset----------");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in cancelAsset method of AssetMakerForSaveAction action the session is out----------------");
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
		UserObject sessUser=(UserObject)session.getAttribute("userobject");
    	assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		org.apache.commons.beanutils.BeanUtils.copyProperties(assetMakervo, AssetMakerDynaValidatorForm);
		String userId="";
		String bDate="";
		if(sessUser!=null)
		{
			userId=sessUser.getUserId();
			bDate=sessUser.getBusinessdate();
		}
		assetMakervo.setMakerId(userId);
		assetMakervo.setBusinessDate(bDate);
		String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		boolean result=false;
		 String msg="";
		 
			 result=dao.cancelAssets(assetMakervo);
			 logger.info("result"+result);
			 if(result)
			 {
				 msg="D";
				 logger.info("00msg"+msg);
			 }
			 else
			 {
				 msg="E";
				 logger.info("msg1"+msg);
			 }
			 
		request.setAttribute("msg", msg);
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		request.setAttribute("insuranceDoneByList",insuranceDoneByList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		logger.info("result"+result);
		
			return mapping.getInputForward();
	}
	
}