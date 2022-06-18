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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

//import com.business.PoolBussiness.PoolBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.PoolIDDAO;
import com.cm.dao.PoolIdAddEditDAO;
import com.cm.vo.PoolIdMakerVO;

public class PoolIdMakerProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PoolIdMakerProcessAction.class.getName());	
	
	
	   public ActionForward newPoolIdMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

					logger.info(" In the newPoolIdMaker-");
					HttpSession session = request.getSession();
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					if(userobj==null){
						logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					session.removeAttribute("authorPoolIdSavedList");
					session.removeAttribute("List");
					session.removeAttribute("poolID");
					request.setAttribute("poolIdMaker", "poolIdMaker");
					request.setAttribute("forNew","");
					//CreditManagementDAO dao = new CreditManagementDAOImpl();
					//edit by=========================================================================== amritesh
					//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					//edit by=========================================================================== amritesh		
					PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass());
					String poolNo = dao.getPoolNo();				
					
					if(poolNo.equalsIgnoreCase("")){
						int p = 1;
						request.setAttribute("poolNo", p);
					}else{
						int p = Integer.parseInt(poolNo);
					    p = p + 1;
					    logger.info("^^^^^^"+p);
					    request.setAttribute("poolNo", p);
					}
					 
					return mapping.findForward("success");
			}
	   
	   

	   
	   public ActionForward searchPoolIdMaker(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		    logger.info("In searchPoolIdMaker  ");
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerID="";
			String strFlag="";
			
			if(userobj==null){
				logger.info(" in searchPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}else{
				makerID=userobj.getUserId();
			}
			
			Object sessionId = session.getAttribute("sessionID");
			PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			//for check User session start
			ServletContext context = getServlet().getServletContext();
				
			
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
			logger.info("current page link .......... "+request.getParameter("d-1344872-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-1344872-p")==null || request.getParameter("d-1344872-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-1344872-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-1344872-p"));
			
			poolIdMakerVO.setCurrentPageLink(currentPageLink);	
			//edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh
			PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			  
			
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			poolIdMakerVO.setMakerID(makerID);
			ArrayList<PoolIdMakerVO> poolIdMakerListGrid = dao.searchPoolIdMakerGrid(poolIdMakerVO);
			
			request.setAttribute("poolIdMakerList", poolIdMakerListGrid);
			if((poolIdMakerListGrid.size())==0)
			{
				request.setAttribute("datalist","datalist");
			}
			logger.info("poolIdMakerListGrid    Size:---"+poolIdMakerListGrid.size());
			return mapping.findForward("poolIdMakerSearch");	
		
		}    

	
	   
	   public ActionForward getPoolSearchedData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
				throws Exception {
		    
		    logger.info("In getPoolSearchedData  ");
			
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String strFlag="";	
			
			if(userobj==null){
				logger.info(" in getPoolSearchedData method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
			
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
				logger.info("In getPoolSearchedData  ");
				
				logger.info("current page link ..........+++++++++:::::>>> "+request.getParameter("d-4008017-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
				}
				
				logger.info("current page link ................ get   ::::::"+request.getParameter("d-4008017-p"));
				//edit by=========================================================================== amritesh
				//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				//edit by=========================================================================== amritesh
				PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
			
			    DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			    PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			String poolID = poolIdMakerVO.getPoolID();
			poolIdMakerVO.setCurrentPageLink(currentPageLink);
			logger.info("poolID;-"+poolID);
			ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolID);			
			request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);
			ArrayList mainList  = dao.getPoolData(poolID,poolIdMakerVO);
			request.setAttribute("mainList", mainList);			
			request.setAttribute("poolNo",poolID);
		
			if((poolIdSavedDataList.size())==0)
			{
				request.setAttribute("datalist","datalist");
			}
			logger.info("poolIdMakerListGrid    Size:---"+poolIdSavedDataList.size());
			
			request.setAttribute("poolIdMaker", "poolIdMaker");
			return mapping.findForward("poolIdMakerShowData");	
		
		}
	  
	   
	   
public ActionForward submitPoolIdUpload(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		   logger.info("Inside Processing Action of submitPoolIdUpload");
		   
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			boolean status=false;			
	    	boolean uploadStatus=false;	
			
			String makerID =null;
			String businessDate =null;
			String strFlag=null;
			String poolID = null;
	    	String flag=null;
	    	String filePathWithName=null;
	    	String procStatus=null;
	    	
			int currentPageLink = 0;
	    	int count=1;

			if(userobj!=null){
					makerID = userobj.getUserId();
					businessDate=userobj.getBusinessdate();
					
			}else{
				logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		   
			Object sessionId = session.getAttribute("sessionID");
		    ServletContext context = getServlet().getServletContext();
			
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

			logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
			
			if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
			
		   
		   PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
		   DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
		   org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
		 //edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh
      	  PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		   
      	   logger.info("Inside Processing Action of submitPoolIdUpload");
      	 
			poolIdMakerVO.setMakerID(makerID);
			poolIdMakerVO.setMakerDate(businessDate);
			poolIdMakerVO.setCurrentPageLink(currentPageLink);
		   
			logger.info("Implementation class: "+dao.getClass());
		   

			poolID = poolIdMakerVO.getPoolID();
			logger.info("poolID in action:-"+poolID);
		
			
	    	if(!CommonFunction.checkNull(poolIdMakerVO.getDocFile()).equalsIgnoreCase(""))
	    	{	    	
	    		uploadStatus=dao.docUploadForExcel(request,(FormFile)poolIdMakerVO.getDocFile());
				filePathWithName=request.getAttribute("filePathWithName").toString();
				
	    		poolIdMakerVO.setFileName(request.getAttribute("fileName").toString());
	    		poolIdMakerVO.setDocPath(request.getAttribute("filePath").toString());
	    		poolIdMakerVO.setFilePathWithName(filePathWithName);
	    		
	    		flag=(String)request.getAttribute("message");
	    		logger.info("flag .. "+flag);
	    		
	    		if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
				    request.setAttribute("sms","");
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
					request.setAttribute("smsno","");

				logger.info("uploadStatus .. "+uploadStatus);

//				 TO CHECK TUSHAR				
//				if(uploadStatus)
//				count=service.countPoolLine(poolIdMakerVO);
				
			
				logger.info("in submitPoolIdUpload() no of row in uploaded excel : "+count);
				if(count==0)
				{
					 request.setAttribute("zeroCount","");
					 request.setAttribute("poolNo",poolID);
				}
				else if(count>1000)
				{				
					 request.setAttribute("maxCount","");	
					 request.setAttribute("mainList", "");
					 request.setAttribute("poolNo",poolID);
				}
				else if(count==-1)
				{	
					request.setAttribute("smsk","");	
					request.setAttribute("mainList", "");
					request.setAttribute("fileIsBlank", "");
					request.setAttribute("poolNo",poolID);
				}
				else if(count==-2)
				{
					 request.setAttribute("smks","");
					 request.setAttribute("mainList", "");
					 request.setAttribute("poolNo",poolID);
				}
				else
				{ 
					logger.info("in submitPoolIdUpload()  Uploaded exce file is ok and going to process.");	
					
					
					if(uploadStatus){			
						status=dao.uploadCsv_Securitization(request,response,poolIdMakerVO.getDocFile().getFileName(),poolIdMakerVO);	
			     	    logger.info("status-----------------------"+status);
						procStatus=dao.saveSecuritization(request,poolIdMakerVO);
						logger.info("procStatus-----------------------"+procStatus);
					}
					
					if(status==true && procStatus.equalsIgnoreCase("S"))
					{	
						
						logger.info("....In startPoolProcess..Total Line..******************++++++"+status);
						request.setAttribute("inserted", "Done");	
					    ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolID);			
						request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);
						request.setAttribute("poolNo", poolID);
					}else{
						logger.info("....In startPoolProcess..Total Line..*************************"+status);
						request.setAttribute("mainList", "");
						request.setAttribute("poolNo", poolID);
						request.setAttribute("noinserted", "Done");
					}
					logger.info("status"+status);
				}
	    	}
			
	    	if(currentPageLink>1){
		    	ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolID);			
				request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);
	    	}
						    ArrayList mainList  = dao.getPoolData(poolID,poolIdMakerVO);
						    int sizeList=mainList.size();
						    					     
							request.setAttribute("mainList", mainList);
							request.setAttribute("poolNo", poolID);
							request.setAttribute("poolIdMaker", "poolIdMaker");
							
							makerID =null;
							businessDate =null;
							strFlag=null;
							poolID = null;
					    	flag=null;
					    	filePathWithName=null;
					    	procStatus=null;
					    	
					
			return mapping.findForward("submitPoolIdUpload");
		} 	
		
	
	  
		public ActionForward  deletePoolIdAddEdit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {	
			
			logger.info("In deletePoolID");
			HttpSession session = request.getSession();
			UserObject userobj = (UserObject) session.getAttribute("userobject");
			 String makerID ="";
				String businessDate ="";
				int companyId =0;
				if(userobj!=null){
					makerID = userobj.getUserId();
						businessDate=userobj.getBusinessdate();
						companyId=userobj.getCompanyId();
				}else{
					logger.info(" in deletePoolID PoolIdMakerProcessAction  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			
			String sessionId = session.getAttribute("sessionID").toString();
			PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			//edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh
      	  // PoolIDDAO service=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
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
			 DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
			 org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);

				poolIdMakerVO.setMakerID(makerID);
				poolIdMakerVO.setMakerDate(businessDate);
				String alertMsg ="";
				String poolId = request.getParameter("poolID");
				poolIdMakerVO.setPoolID(poolId);
				logger.info(" In the poolId----------"+poolId);
				//logger.info("getPoolID:-"+poolIdMakerVO.getPoolID());
				//String poolId =CommonFunction.checkNull( poolIdMakerVO.getPoolID());
				String loanIDLIST = request.getParameter("loanIDLIST"); 
				String poolIDLIST = request.getParameter("poolIDLIST");					
				String[] loanID = loanIDLIST.split("/");
				String[] poolID = poolIDLIST.split("/");
				
		         String actionType=request.getParameter("actionType");
	      		 boolean deletePoolListEdit = dao.deletePoolIDEdit(poolIdMakerVO,loanID,poolID,companyId);
	      		 if(deletePoolListEdit)
	      			 request.setAttribute("delete",deletePoolListEdit);
				 request.setAttribute("poolIdMaker", "poolIdMaker");	
				 request.setAttribute("poolNo", poolId);
				 ArrayList<PoolIdMakerVO> poolIdEditSavedList = dao.savedPoolIdEditData(poolIdMakerVO,poolId);			
				 request.setAttribute("poolIdEditSavedList", poolIdEditSavedList);
				 ArrayList editList  = dao.getPoolAddEditData(poolId);
				 request.setAttribute("editList", editList);
				 return mapping.findForward("deletePoolIDEdit");
		         
}
	
	
	
	public ActionForward deletePoolID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception {	
		
				logger.info("In deletePoolID");
				HttpSession session = request.getSession();
				UserObject userobj = (UserObject) session.getAttribute("userobject");
				 String makerID ="";
					String businessDate ="";
					int companyId =0;
					if(userobj!=null){
						makerID = userobj.getUserId();
							businessDate=userobj.getBusinessdate();
							companyId=userobj.getCompanyId();
					}else{
						logger.info(" in deletePoolID PoolIdMakerProcessAction  action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
				
				String sessionId = session.getAttribute("sessionID").toString();
				PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
				//CreditManagementDAO dao = new CreditManagementDAOImpl();
				 //edit by=========================================================================== amritesh
				//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				//edit by=========================================================================== amritesh	
				PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());				
				//PoolIdAddEditDAO dao1=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
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
				logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
				 DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
				 org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);

					poolIdMakerVO.setMakerID(makerID);
					poolIdMakerVO.setMakerDate(businessDate);
					String alertMsg ="";
					String poolId = request.getParameter("poolID");
					poolIdMakerVO.setPoolID(poolId);
					poolIdMakerVO.setCurrentPageLink(currentPageLink);
					logger.info(" In the poolId----------"+poolId);
					//logger.info("getPoolID:-"+poolIdMakerVO.getPoolID());
					//String poolId =CommonFunction.checkNull( poolIdMakerVO.getPoolID());
					String loanIDLIST = request.getParameter("loanIDLIST"); 
					String poolIDLIST = request.getParameter("poolIDLIST");					
					String[] loanID = loanIDLIST.split("/");
					String[] poolID = poolIDLIST.split("/");
					
 		        String actionType=request.getParameter("actionType");
		       	if(actionType.equalsIgnoreCase("edit"))
			    {
					 boolean deletePoolListEdit = dao.deletePoolIDEdit(poolIdMakerVO,loanID,poolID,companyId);
					 request.setAttribute("poolIdMaker", "poolIdMaker");	
					 request.setAttribute("poolNo", poolId);
					 ArrayList<PoolIdMakerVO> poolIdEditSavedList = dao.savedPoolIdEditData(poolIdMakerVO,poolId);			
					 request.setAttribute("poolIdEditSavedList", poolIdEditSavedList);
					 ArrayList editList  = dao.getPoolAddEditData(poolId);
					 request.setAttribute("editList", editList);
					 return mapping.findForward("deletePoolIDEdit");
			   }
			   else 
			   {
					boolean deletePoolList = dao.deletePoolID(loanID,poolID);
					logger.info("deletePoolList:--"+deletePoolList);
					request.setAttribute("poolIdMaker", "poolIdMaker");	
					request.setAttribute("poolNo", poolId);
					if(deletePoolList){
						request.setAttribute("deleteOk", "Y");
					}
					
					ArrayList mainList  = dao.getPoolData(poolId,poolIdMakerVO);
					request.setAttribute("mainList", mainList);
					ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolId);			
					request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);
					return mapping.findForward("deletePoolID");
			}
				
		        
	}
 
	public ActionForward openWindowForaddPoolID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		

			logger.info(" In the openWindowForaddPoolID----------");			
			HttpSession session = request.getSession();
			UserObject userobj = (UserObject) session.getAttribute("userobject");
		
				if(userobj==null){
				
					logger.info(" in openWindowForaddPoolID PoolIdMakerProcessAction  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			String poolID= request.getParameter("poolID");
            request.setAttribute("poolID",poolID);  
            request.setAttribute("forPoolIdMaker","forPoolIdMaker"); 
			return mapping.findForward("openWindowForaddPoolID");
		}
	
	
	public ActionForward retriveValueByLoanforPool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

			logger.info(" In the retriveValueByLoanforPool----------");
			HttpSession session = request.getSession();
			UserObject userobj = (UserObject) session.getAttribute("userobject");
		
				if(userobj==null){
				
					logger.info(" in retriveValueByLoanforPool PoolIdMakerProcessAction  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
			String lbxLoanNoHID= request.getParameter("lbxLoanNoHID");
			
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			 //edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh	
			PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		 	ArrayList mainList  = dao.retriveValueByLoanforPool(lbxLoanNoHID);
			
			request.setAttribute("mainList", mainList);
                  
			return mapping.findForward("retriveValueByLoanforPool");
		}
	
	
	
	
	public ActionForward saveLoanInPoolID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				logger.info("In saveLoanInPoolID");
				HttpSession session = request.getSession();
				UserObject userobj = (UserObject) session.getAttribute("userobject");
			 	String makerID ="";
				String businessDate ="";
				
				if(userobj!=null){
					makerID = userobj.getUserId();
					businessDate=userobj.getBusinessdate();
						
				}else{
					logger.info(" in saveLoanInPoolID merthod of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				String sessionId = session.getAttribute("sessionID").toString();

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
				String alertMsg="";
				
				 PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
				   DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
				 org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
				   

					poolIdMakerVO.setMakerID(makerID);
					poolIdMakerVO.setMakerDate(businessDate);
				   
					String poolID = request.getParameter("poolID");
					logger.info("poolID--"+poolID);
					//CreditManagementDAO service = new CreditManagementDAOImpl();
					 //edit by=========================================================================== amritesh
					//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
					//edit by=========================================================================== amritesh	
					PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass());
				    boolean insert = dao.insertLoanforPoolId(poolIdMakerVO,poolID);
				    
			    
			    if(insert){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    
			    ArrayList mainList  = dao.getPoolData(poolID,poolIdMakerVO);
				request.setAttribute("mainList", mainList);
			  
				 request.setAttribute("poolID",poolID);
		        return mapping.findForward("saveLoanInPoolID");
	}
	

	
	
	
	public ActionForward selectForPool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info(" In the selectForPool----------");
		
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
	
			if(userobj==null){
			
				logger.info(" in selectForPool PoolIdMakerProcessAction  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			logger.info("current page link ..........::::::::::::::::::: "+request.getParameter("d-4008017-p"));
			
			int currentPageLink = 0;
			if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
			}
			
			logger.info("current page link ................ "+request.getParameter("d-4008017-p"));

			
			 PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			 poolIdMakerVO.setCurrentPageLink(currentPageLink);
			String poolID = request.getParameter("poolID");
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			 //edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh	
			PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		 	ArrayList mainList  = dao.getPoolData(poolID,poolIdMakerVO);
		 	request.setAttribute("poolIdMaker", "poolIdMaker");	
		    request.setAttribute("poolNo", poolID);
			request.setAttribute("mainList", mainList);
			ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolID);
			request.setAttribute("poolIdSavedDataList", poolIdSavedDataList); 
			return mapping.findForward("deletePoolID");
		}
	
	
	public ActionForward forwardPoolIdMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

			logger.info(" In the forwardPoolIdMaker----------");
			HttpSession session = request.getSession();
			UserObject userobj = (UserObject) session.getAttribute("userobject");
			
			if(userobj==null){
			
				logger.info(" in forwardPoolIdMaker PoolIdMakerProcessAction  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}

			session.removeAttribute("authorPoolIdSavedList");
			session.removeAttribute("List");
			session.removeAttribute("poolID");
		
			String alertMsg="";
			String poolID = request.getParameter("poolID");
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			 //edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh	
		    PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			boolean status = dao.forwardPoolIdMaker(poolID);
			
			  
		    if(status){
		    	alertMsg = "Y";
		    	request.setAttribute("alertMsg", alertMsg);
		    
		    }else{
		    	alertMsg = "N";
		    	request.setAttribute("alertMsg", alertMsg);
		    }
		    ArrayList poolIdSavedDataList=new ArrayList();
		    request.setAttribute("poolIdSavedDataList",poolIdSavedDataList);
		    ArrayList myList=new ArrayList();
		    request.setAttribute("myList",myList);
		    
		
			return mapping.findForward("forwardPoolId");
		}



	
}	