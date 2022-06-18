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

import com.cm.dao.assetVerificationDAO;
import com.cm.vo.AssetVerificationVO;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;

import com.lockRecord.action.LockRecordCheck;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class AssetVerificationInit extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AssetVerificationInit.class.getName());	 
	
	public ActionForward saveAssetVerification(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("In saveAssetVerification ()--------->"); 
			    
			    HttpSession session =  request.getSession();
			
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate="";
				if(userobj!=null)
				{
					makerID=userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in saveAssetVerification ()----------------");
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
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
			    //end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
				
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
				

	
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				assetVerificationVO.setMakerID(makerID);
				assetVerificationVO.setMakerDate(bDate);
				
				String loanID  = request.getParameter("loanIDList");
	            String[] loanIDList= loanID.split("/");
	            String loanNo  = request.getParameter("loanNo");
	           
	            
	            
		        String assetID  = request.getParameter("assetIDList");                    			
	            String[] assetIDList= assetID.split("/"); 
	            
	            String assetDescription  = request.getParameter("assetDescriptionList");                     			
	            String[] assetDescriptionList= assetDescription.split("/");
	            String appraiserid="";

	            logger.info("---assetVerificationVO.getAppraiser()--->"+assetVerificationVO.getAppraiser());
	            logger.info("----assetVerificationVO.getLbxextApprHID()----->"+assetVerificationVO.getLbxextApprHID());
	      	  logger.info("----assetVerificationVO.getLbxUserId()----->"+assetVerificationVO.getLbxUserId());
	  
	            String loanid=loanIDList[0];
	            
	            ArrayList list=new ArrayList();
	            String result="";
	            assetVerificationVO.setLbxLoanNo(loanid);
	            assetVerificationVO.setLoanNo(loanNo);
	 
	             logger.info("----getLbxLoanNo----->"+assetVerificationVO.getLbxLoanNo());
	           
	            logger.info("----getLoanNo---->"+assetVerificationVO.getLoanNo());
	            result = dao.saveAssetVerification(loanIDList,assetIDList,assetDescriptionList,assetVerificationVO);
			    
			    logger.info("result is----"+result);
			   
			   
			    if(!result.equals("saved"))
			    {
			    	request.setAttribute("assetsnotsaved", result);
			    	 request.setAttribute("statusflag", "save");
			    }
			    if(result.equals("saved"))
			    {
			    	request.setAttribute("dss", 'S');
			    	 request.setAttribute("requestVo",  assetVerificationVO);
			    	 request.setAttribute("statusflag", "forward");
			    }
				
			
			    return mapping.findForward("success");
		}
	 
	 public ActionForward searchAssetVerRepCap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in searchAssetVerRepCap");
			    
					
			    HttpSession session =  request.getSession();
			
				UserObject userobj=(UserObject)session.getAttribute("userobject");

				String userid="";
						String bDate="";
						String branchId="";
						if(userobj!=null)
						{
							userid=userobj.getUserId();
							bDate=userobj.getBusinessdate();
							branchId=userobj.getBranchId();
						}else{
							logger.info("here in searchAssetVerRepCap  method of AssetVerificationInit action the session is out----------------");
							return mapping.findForward("sessionOut");
						}
					
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();
				Object sessionId = session.getAttribute("sessionID");
				String userId="";
				
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

				logger.info("current page link .......... "+request.getParameter("d-49520-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
				assetVerificationVO.setCurrentPageLink(currentPageLink);
				//change by sachin
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
	//end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
			
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);


				userId=assetVerificationVO.getLbxUserId();


				if(userId.trim().length()== 0)
				{
					userId=userid;
				}
				assetVerificationVO.setReportingToUserId(userId);
				
				assetVerificationVO.setBranchId(branchId);
				assetVerificationVO.setMakerID(userid);
				assetVerificationVO.setMakerDate(bDate);
				
			    ArrayList arrList = dao.searchAssetVerRepCap(assetVerificationVO);
//			    if(result){
//			    	
//			    	request.setAttribute("dss", "S");
//			    	
//			    }else{
//			    	request.setAttribute("dss", "N");	
//			    }
			     
			     request.setAttribute("list", arrList);
			     return mapping.findForward("searchSuccess");
		}
	 
	 public ActionForward repCapAsset(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in repCapAsset"); 
			    
			    HttpSession session =  request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				String userId ="";
				if(userobj!=null){
					makerID = userobj.getUserId();
					bDate=userobj.getBusinessdate();
					userId = userobj.getUserId();
				}else{
					logger.info("here in repCapAsset method of  AssetVerificationInit action the session is out----------------");
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
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
	//end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
			
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
		
					
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
			
				assetVerificationVO.setMakerID(makerID);
				logger.info("makerID...."+makerID);
				assetVerificationVO.setMakerDate(bDate);
				
				String assetVerificationID = request.getParameter("assetVerificationID");
	            logger.info("assetVerificationID"+assetVerificationID);
	            
	            logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
	    		String functionId="";

	    		
	    		if(session.getAttribute("functionId")!=null)
	    		{
	    			functionId=session.getAttribute("functionId").toString();
	    		}
	    		
	    		
	    		//ServletContext context=getServlet().getServletContext();
	    		if(context!=null)
	    		{
	    		flag = LockRecordCheck.lockCheck(userId,functionId,assetVerificationID,context);
	    		logger.info("Flag ........................................ "+flag);
	    		if(!flag)
	    		{
	    			logger.info("Record is Locked");			
	    			request.setAttribute("dss", "Locked");
	    			request.setAttribute("recordId", assetVerificationID);
	    			//request.setAttribute("userId", userId);
	    			return mapping.findForward("searchSuccess");
	    		}
	    		}
			    ArrayList arrList = dao.repCapAsset(assetVerificationVO,assetVerificationID,makerID);
			    request.setAttribute("list", arrList);
			    request.setAttribute("list1", "list1");
			    
			    return mapping.findForward("repCapAsset");
		}
	 
	 public ActionForward saveAssetRepCap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in saveAssetRepCap"); 
			   
			    HttpSession session =  request.getSession();
			
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				if(userobj!=null){
					makerID = userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in saveAssetRepCap method of  AssetVerificationInit action the session is out----------------");
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
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
	//end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
				
				//UserObject userObj=(UserObject)session.getAttribute("userobject");

				
				
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				assetVerificationVO.setMakerID(makerID);
				assetVerificationVO.setMakerDate(bDate);
				
				String assetVerificationID = request.getParameter("assetVerificationID");
	            logger.info("assetVerificationID"+assetVerificationID);
	            
			    boolean status = dao.saveAssetRepCap(assetVerificationVO,assetVerificationID);
			    if(status){
			    	
			    	request.setAttribute("dss", "S");
			    	
			    }else{
			    	request.setAttribute("dss", "N");	
			    }
			    ArrayList arrList = dao.repCapAsset(assetVerificationVO,assetVerificationID,makerID);
			    request.setAttribute("list", arrList);
			    return mapping.findForward("saveAssetRepCap");
		}
	
	 public ActionForward searchAssetVerRepComplete(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in searchAssetVerRepComplete"); 
			    
			    HttpSession session =  request.getSession();
			
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				String userId="";
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info("here in searchAssetVerRepComplete method of AssetVerificationInit action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			    AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
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

				logger.info("current page link .......... "+request.getParameter("d-49520-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
				assetVerificationVO.setCurrentPageLink(currentPageLink);
			    
				//change by sachin
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
	//end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
				
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
//				String makerID = userObj.getUserId();
				
				assetVerificationVO.setBranchId(branchId  );
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				if(CommonFunction.checkNull(assetVerificationVO.getReportingToUserId()).equalsIgnoreCase(""))
				{ 
					assetVerificationVO.setReportingToUserId(userId);
				   //logger.info("When user id is not selected by the user:::::"+userId);
				}
				logger.info("user Id:::::"+assetVerificationVO.getReportingToUserId());			
				assetVerificationVO.setMakerID(userId);
				assetVerificationVO.setMakerDate(branchId);
				assetVerificationVO.setUserId(userId);
				
	          
			    ArrayList arrList = dao.searchAssetVerRepComplete(assetVerificationVO);
			     
			    	request.setAttribute("comp","comp");
			     request.setAttribute("list", arrList);
			     return mapping.findForward("searchSuccessComplete");
		}
	 
	 public ActionForward repCapAssetComplete(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in repCapAssetComplete"); 
			   
			    HttpSession session =  request.getSession();
			  
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				if(userobj!=null){
					makerID = userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in repCapAssetComplete method of  AssetVerificationInit action the session is out----------------");
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
			    ArrayList arrList =null;
			  //change by sachin
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
			    //end by sachin
//			    assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
				
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
		
				
				
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				assetVerificationVO.setMakerID(makerID);
				assetVerificationVO.setMakerDate(bDate);
				
				String assetVerificationID = request.getParameter("assetVerificationID");
	            logger.info("assetVerificationID"+assetVerificationID);
	            
	            logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
	    		String functionId="";
	    		
	    		
	    		if(session.getAttribute("functionId")!=null)
	    		{
	    			functionId=session.getAttribute("functionId").toString();
	    		}
	    		
	    		
	    		//ServletContext context=getServlet().getServletContext();
	    		if(context!=null)
	    		{
	    		flag = LockRecordCheck.lockCheck(makerID,functionId,assetVerificationID,context);
	    		logger.info("Flag ........................................ "+flag);
	    		if(!flag)
	    		{
	    			logger.info("Record is Locked");			
	    			request.setAttribute("dss", "Locked");
	    			request.setAttribute("recordId", assetVerificationID);
	    			//request.setAttribute("userId", userId);
	    			return mapping.findForward("searchSuccessComplete");
	    		}
	    		}
			    arrList = dao.repCapAssetComplete(assetVerificationVO,assetVerificationID);
			    if(arrList.size()>0){
	            session.setAttribute("list", arrList);
			    }
	           // session.setAttribute("tab","");
			    return mapping.findForward("repCapAssetComplete");
		}
	 
	 public ActionForward saveAssetCapRepAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in saveAssetCapRepAuthor"); 
			    
			    HttpSession session =  request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				if(userobj!=null){
					makerID = userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in saveAssetCapRepAuthor method of AssetVerificationInit action the session is out----------------");
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

			    ArrayList arrList =null;
			  //change by sachin
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
			    //end by sachin
//			    assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
				
				//UserObject userObj=(UserObject)session.getAttribute("userobject");
		

				
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				assetVerificationVO.setMakerID(makerID);
				assetVerificationVO.setMakerDate(bDate);
				
				String assetVerificationID = request.getParameter("assetVerificationID");
	            logger.info("assetVerificationID"+assetVerificationID);
	            
			    boolean status = dao.saveAssetCapRepAuthor(assetVerificationVO,assetVerificationID);
			    if(status){
			    	
			    	request.setAttribute("dss", "S");
			    	
			    }else{
			    	request.setAttribute("dss", "N");	
			    }
			    return mapping.findForward("saveAssetCapRepAuthor");
		}
	 
	 public ActionForward assetVerificationNew(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 
		 
				logger.info(" In the AssetVerificationInitiAction----------");
			    HttpSession session =  request.getSession();
			
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				if(userobj==null){
				
					logger.info("here in assetVerificationNew method of AssetVerificationInit action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
		   
				return mapping.findForward("new");
			}
	 
	 public ActionForward forwardAssetVerification(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
				throws Exception {
					
					    logger.info("in forwardAssetVerification"); 
					    
					    HttpSession session =  request.getSession();
						boolean flag=false;
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String makerID ="";
						String bDate ="";
						if(userobj!=null){
							makerID = userobj.getUserId();
							bDate=userobj.getBusinessdate();
						}else{
							logger.info("here in forwardAssetVerification method of AssetVerificationInit action the session is out----------------");
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
						assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
					    logger.info("Implementation class: "+dao.getClass()); 
			//end by sachin			    
	//					assetVerificationDAO dao = new assetVerificationDAOImpl();
						DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;
						
						//UserObject userObj=(UserObject)session.getAttribute("userobject");
			

						
						AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
						org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
						assetVerificationVO.setMakerID(makerID);
						assetVerificationVO.setMakerDate(bDate);
						
					
						
						String loanID  = request.getParameter("loanIDList");
			            String[] loanIDList= loanID.split("/");
			            
			            String loanAccNo  = request.getParameter("loanAccNoList"); 			
			            String[] loanAccNoList= loanAccNo.split("/");      
			            
				        String assetID  = request.getParameter("assetIDList");                    			
			            String[] assetIDList= assetID.split("/"); 
			            
			            String assetDescription  = request.getParameter("assetDescriptionList");                     			
			            String[] assetDescriptionList= assetDescription.split("/");
			            
			      
					    boolean result = dao.forwardAssetVerification(loanIDList,loanAccNoList,assetIDList,assetDescriptionList,assetVerificationVO);
					    if(result){
					    	
					    	request.setAttribute("dss", "F");
					    	
					    }else{
					    	request.setAttribute("dss", "N");	
					    }
					    
					     
					    return mapping.findForward("forwardSucess");
				}

	 
	 public ActionForward  assetVerificationmodify(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)throws Exception {
				 
						logger.info(" In the AssetVerificationInitiAction----- assetVerificationmodify-----");
						
						HttpSession session = request.getSession();
					
						boolean flag=false;
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String userId ="";
						
						if(userobj!=null){
							userId = userobj.getUserId();
							
						}else{
							logger.info("here in assetVerificationmodify method of AssetVerificationInit action the session is out----------------");
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
						assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
					    logger.info("Implementation class: "+dao.getClass()); 
			//end by sachin
//						assetVerificationDAO dao = new assetVerificationDAOImpl();
						DynaValidatorForm AssetVerificationInitiationSearchDynaValidatorForm = (DynaValidatorForm)form;
												
						AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
						org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationSearchDynaValidatorForm);

						String assetVerInitId = request.getParameter("assetVerId");
						logger.info("assetVerInitId...."+assetVerInitId);
						assetVerificationVO.setAssetVerificationID(assetVerInitId);
						
						logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
						String functionId="";
			

					
						if(session.getAttribute("functionId")!=null)
						{
							functionId=session.getAttribute("functionId").toString();
						}
						
						
						//ServletContext context=getServlet().getServletContext();
						if(context!=null)
			    		{
						flag = LockRecordCheck.lockCheck(userId,functionId,assetVerInitId,context);
						logger.info("Flag ........................................ "+flag);
						if(!flag)
						{
							logger.info("Record is Locked");			
							request.setAttribute("dss", "Locked");
							request.setAttribute("recordId", assetVerInitId);
							//request.setAttribute("userId", userId);
							return mapping.findForward("success");
						}
			    		}
						ArrayList arrList = dao.searchAssetVerModifyData(assetVerificationVO);
						 
						 request.setAttribute("assetVerInitId",assetVerInitId);
						
						 request.setAttribute("list", arrList);
						 
						return mapping.findForward("new");
					}
			 
			 
	 public ActionForward modifyAssetVerification(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
				throws Exception {
					
					    logger.info("in modifyAssetVerification"); 
					    HttpSession session =request.getSession();
						boolean flag=false;
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String userId ="";
						String bDate ="";
						if(userobj!=null)
						{
							userId=userobj.getUserId();
							bDate=userobj.getBusinessdate();
						}
						else{
							logger.info("here in modifyAssetVerification method of AssetVerificationInit action the session is out----------------");
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
						assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
					    logger.info("Implementation class: "+dao.getClass()); 
			//end by sachin				
//						assetVerificationDAO dao = new assetVerificationDAOImpl();
						DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;

						AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
						org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
						assetVerificationVO.setMakerID(userId);
						assetVerificationVO.setMakerDate(bDate);
						
						String assetVerInitId = request.getParameter("assetVerInitId");
						String[] assetIDList= request.getParameter("assetIDList").split("/");
						String loanId= request.getParameter("loanId");
						String loanNo= request.getParameter("loanNo");
						String appraisertype= request.getParameter("appraisertype");
						String Appraiserid= request.getParameter("Appraiserid");
						String Appraiser= request.getParameter("Appraiser");
						logger.info("assetVerInitId...."+assetVerInitId);
						logger.info("loanId...."+loanId);
						assetVerificationVO.setAppraiser(appraisertype) ;
						if(appraisertype.equalsIgnoreCase("I")){
							assetVerificationVO.setLbxUserId(Appraiserid) ;
							assetVerificationVO.setInternalAppraiser(Appraiser);
						}else{
							assetVerificationVO.setLbxextApprHID(Appraiserid) ;
							assetVerificationVO.setExternalAppraiser(Appraiser);
						}
						assetVerificationVO.setLbxLoanNo(loanId);
						assetVerificationVO.setLoanNo(loanNo);
						 request.setAttribute("requestVo",  assetVerificationVO);
						String result = dao.modifyAssetVerification(assetVerificationVO,assetVerInitId,loanId,assetIDList,appraisertype,Appraiserid,Appraiser);
						logger.info("result...."+result);
				    if(!result.equals("saved"))
				    {
				    	request.setAttribute("assetsnotsaved", result);
				    }
				    if(result.equals("saved"))
				    {
				    	request.setAttribute("dss", 'M');
				    	request.setAttribute("statusflag", "forward");
				    }
				    	
				   
				    
					     
					    return mapping.findForward("success");
				}
			 
	
	 public ActionForward modifyForwardAssetVerification(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
				throws Exception {
					
					    logger.info("in modifyForwardAssetVerification"); 
					    HttpSession session =request.getSession();
						boolean flag=false;
						UserObject userobj=(UserObject)session.getAttribute("userobject");
						String userId ="";
						String bDate ="";
						if(userobj!=null)
						{
							userId=userobj.getUserId();
							bDate=userobj.getBusinessdate();
						}else{
							logger.info("here in modifyForwardAssetVerification method of action the session is out----------------");
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
						assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
					    logger.info("Implementation class: "+dao.getClass()); 
			//end by sachin
	//					assetVerificationDAO dao = new assetVerificationDAOImpl();
						DynaValidatorForm AssetVerificationInitiationDynaValidatorForm = (DynaValidatorForm)form;

						AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
						org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationDynaValidatorForm);
				
						assetVerificationVO.setMakerID(userId);
						assetVerificationVO.setMakerDate(bDate);
						String appraiserid = request.getParameter("appraiserid");
						String assets = request.getParameter("assetIDList").toString();
						String[] assetIDList=assets.split("/");
						String loanId= request.getParameter("loanId");
						String appraiser= request.getParameter("appraiser");
						
						
						String appr = request.getParameter("appr");
						logger.info("appraiserid-------->"+appraiserid);
					
					    boolean result = dao.modifyForwaAssetVerification(assetVerificationVO,appraiserid,loanId,assetIDList,appraiser,appr);
					    
					    if(result){
					    	
					    	request.setAttribute("dss", "F");
					    	
					    }else{
					    	request.setAttribute("dss", "N");	
					    }
					    
					     
					    return mapping.findForward("success");
				}

	 public ActionForward  modifyasset(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 
				logger.info(" In the ----- modifyasset()-----");
				
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId ="";
				
				if(userobj!=null){
					userId = userobj.getUserId();
					
				}else{
					logger.info("In the ----- modifyasset()-----");
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
				assetVerificationDAO dao=(assetVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(assetVerificationDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass()); 
			    //end by sachin
//				assetVerificationDAO dao = new assetVerificationDAOImpl();
				DynaValidatorForm AssetVerificationInitiationSearchDynaValidatorForm = (DynaValidatorForm)form;
										
				AssetVerificationVO  assetVerificationVO = new AssetVerificationVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(assetVerificationVO, AssetVerificationInitiationSearchDynaValidatorForm);
				request.setAttribute("statusflag", "forward");
				String assetVerInitId = request.getParameter("assetVerId");
				
				String Appraiserid=request.getParameter("Appraiserid");
	
				String appraisaltype=request.getParameter("appraisaltype");
	
				if(appraisaltype.equalsIgnoreCase("Internal")){
					appraisaltype="I";
					assetVerificationVO.setLbxUserId(Appraiserid);
				}else{
					appraisaltype="EA";
					assetVerificationVO.setLbxextApprHID(Appraiserid);
				}
				String loanid=request.getParameter("loanid");
			
				assetVerificationVO.setAssetVerificationID(assetVerInitId);
				assetVerificationVO.setAppraiserType(appraisaltype);
			
				
				assetVerificationVO.setLoanID(loanid);
				assetVerificationVO.setLbxLoanNo(loanid);
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";
	

			
				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
	    		{
				flag = LockRecordCheck.lockCheck(userId,functionId,assetVerInitId,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("dss", "Locked");
					request.setAttribute("recordId", assetVerInitId);
					//request.setAttribute("userId", userId);
					return mapping.findForward("success");
				}
	    		}
				ArrayList arrList = dao.searchAssetVerModifyData(assetVerificationVO);
				ArrayList datagrid = dao.AssetDataGrid(assetVerificationVO);
				 request.setAttribute("assetVerInitId",assetVerInitId);
				 
				 request.setAttribute("arrList", arrList);
				 logger.info("arrList..................... "+arrList.size());
				 request.setAttribute("assetList", datagrid);
				 logger.info("datagrid..................... "+datagrid.size());
				
				return mapping.findForward("new");
			}
	 
 
}
