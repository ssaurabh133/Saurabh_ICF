package com.scz.actions;

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


//import com.business.PoolBussiness.PoolBussinessSessionBeanRemote;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.PoolIdMakerVO;
import com.scz.dao.PoolIdAddEditDAO;
import com.scz.daoImplMYSQL.PoolIdAddEditDAOImpl;


public class PoolIdAddEditProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PoolIdAddEditProcessAction.class.getName());	
	
	 public ActionForward searchPoolIdAddEdit(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		    logger.info("In searchPoolIdAddEdit  ");
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info(" in searchPoolIdAddEdit method of PoolIdAddEditProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			session.removeAttribute("authorPoolIdSavedList");
			session.removeAttribute("List");
			session.removeAttribute("poolID");
			boolean flag=false;
			
			Object sessionId = session.getAttribute("sessionID");
			PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
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
			
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			//edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh
			// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
			PoolIdAddEditDAO dao = new PoolIdAddEditDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			
			
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			
			ArrayList<PoolIdMakerVO> poolIdAddEditListGrid = dao.searchPoolIdAddEditGrid(poolIdMakerVO);
			
			request.setAttribute("poolIdAddEditList", poolIdAddEditListGrid);
			if((poolIdAddEditListGrid.size())==0)
			{
				request.setAttribute("datalist","datalist");
			}
			logger.info("poolIdAddEditListGrid    Size:---"+poolIdAddEditListGrid.size());
			return mapping.findForward("poolIdAddEditSearch");	
		
		}
	 
	 
	 
	 
	 
	 
	 
	 
	  public ActionForward searchedPoolEditData(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		   logger.info("In searchedPoolEditData  ");
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info(" in searchedPoolEditData method of PoolIdAddEditProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			session.removeAttribute("authorPoolIdSavedList");
			session.removeAttribute("List");
			session.removeAttribute("poolID");
			boolean flag=false;
			
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
			}logger.info("In searchedPoolEditData  ");
			
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			//edit by=========================================================================== amritesh
			//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			//edit by=========================================================================== amritesh
			// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
			PoolIdAddEditDAO dao = new PoolIdAddEditDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			
			
			
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			  PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			int poolID = poolIdMakerVO.getPoolID();
			logger.info("poolID;-"+poolID);
			ArrayList<PoolIdMakerVO> poolIdEditSavedList = dao.savedPoolIdEditData(poolIdMakerVO,poolID);			
			request.setAttribute("poolIdEditSavedList", poolIdEditSavedList);
			ArrayList editList  = dao.getPoolAddEditData(poolID);
			request.setAttribute("editList", editList);
		
			if((poolIdEditSavedList.size())==0)
			{
				request.setAttribute("datalist","datalist");
			}
			logger.info("poolIdMakerListGrid    Size:---"+poolIdEditSavedList.size());
			return mapping.findForward("poolIdEditShow");	
		
		}
	  
	  
	  
	  public ActionForward openWindowPoolIDEdit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the openWindowPoolIDEdit----------");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in openWindowPoolIDEdit method of PoolIdAddEditProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				String poolID= request.getParameter("poolID");
	            request.setAttribute("poolID",poolID); 
	            request.setAttribute("forAddEdit","forAddEdit"); 
				return mapping.findForward("openWindowPoolIDEdit");
			}
	  
	  public ActionForward saveLoanInPoolIDEdit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
					logger.info("In saveLoanInPoolIDEdit");
					HttpSession session = request.getSession();
					UserObject userobj = (UserObject) session.getAttribute("userobject");
					 String userId ="";
						String businessDate ="";
						int companyId =0;
						if(userobj!=null){
							userId = userobj.getUserId();
								businessDate=userobj.getBusinessdate();
								companyId=userobj.getCompanyId();
						}else{
							logger.info(" in saveLoanInPoolIDEdit method of PoolIdAddEditProcessAction action the session is out----------------");
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
					String msg="";
					
					 PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
					 DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
					 org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
					   
						poolIdMakerVO.setMakerID(userId);
						poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
					   
						int poolID = Integer.parseInt(request.getParameter("poolID"));
						logger.info("poolID--"+poolID);
						poolIdMakerVO.setPoolID(poolID);
						String loanID = request.getParameter("lbxLoanNoHID");
						logger.info("loanID--"+loanID);
						poolIdMakerVO.setLbxLoanNoHID(loanID);;
						// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
						PoolIdAddEditDAO dao = new PoolIdAddEditDAOImpl();
						logger.info("Implementation class: "+dao.getClass());
					    boolean insert = dao.insertLoanforPoolIdEdit(poolIdMakerVO,companyId);
					    
				    
				    if(insert){
				    	msg = "S";
				    	 logger.info("00msg-"+msg);
				    	
					//	ArrayList<PoolIdMakerVO> poolIdEditSavedList = service.savedPoolIdEditData(poolIdMakerVO,poolID);			
					//	request.setAttribute("poolIdEditSavedList", poolIdEditSavedList);
						
				    }else{
				    	msg = "E";
				    	
				    }
				    request.setAttribute("msg", msg);
				    logger.info("after if 00msg-"+msg);
				    ArrayList editList  = dao.getPoolAddEditData(poolID);
					request.setAttribute("editList", editList);
				    
					 request.setAttribute("poolID",poolID);
			           return mapping.findForward("saveLoanInPoolID");
		}
		
	           public ActionForward selectForPoolEdit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the selectForPoolEdit----------");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in selectForPoolEdit method of PoolIdAddEditProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				 PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
				int poolID = Integer.parseInt(request.getParameter("poolID"));
				// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
				PoolIdAddEditDAO dao = new PoolIdAddEditDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				ArrayList editList  = dao.getPoolAddEditData(poolID);
				request.setAttribute("editList", editList);
			    
			 //	request.setAttribute("poolIdMaker", "poolIdMaker");	
			    request.setAttribute("poolNo", poolID);
			
			    ArrayList<PoolIdMakerVO> poolIdEditSavedList = dao.savedPoolIdEditData(poolIdMakerVO,poolID);			
				request.setAttribute("poolIdEditSavedList", poolIdEditSavedList);
					
				return mapping.findForward("Show");
			}
}
