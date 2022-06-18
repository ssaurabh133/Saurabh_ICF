package com.scz.actions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
// import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.scz.DateFormator;
import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.vo.PoolIdMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PoolIdAuthorProcessAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(PoolIdAuthorProcessAction.class.getName());	
	  
	public ActionForward searchPoolIdAuthor(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		    logger.info("In searchPoolIdAuthor  ");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String makerID=null;
			if(userobj!=null){
				makerID = userobj.getUserId();
			}else{
				logger.info(" in searchPoolIdAuthor method of PoolIdAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
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
			 logger.info("current page link .........pool id author. "+request.getParameter("d-1344872-p"));
				
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
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			  
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
			poolIdMakerVO.setMakerID(makerID);
			
			ArrayList<PoolIdMakerVO> poolIdAuthorListGrid = dao.searchPoolIdAuthorGrid(poolIdMakerVO);	
			request.setAttribute("true","true");
			
			request.setAttribute("authordetailList", poolIdAuthorListGrid);
			request.setAttribute("poolIdAuthor","poolIdAuthor");
			if((poolIdAuthorListGrid.size())==0)
			{
				request.setAttribute("datalist","datalist");
			}
			logger.info("poolIdAuthorListGrid    Size:---"+poolIdAuthorListGrid.size());
			return mapping.findForward("poolIdAuthorSearch");	
		
		}


	 public ActionForward getPoolSearched(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		   logger.info("In getPoolSearchedData  ");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info(" in getPoolSearched method of PoolIdAuthorProcessAction action the session is out----------------");
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
			session.removeAttribute("authorPoolIdSavedList");
			session.removeAttribute("myList");
			session.removeAttribute("poolID");
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
				
				logger.info("current page link .........:::??>>>>>>> "+request.getParameter("d-446528-p"));
				
				int currentPageLink = 0;
				if(request.getParameter("d-446528-p")==null || request.getParameter("d-446528-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-446528-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-446528-p"));	
			
			//CreditManagementDAO dao = new CreditManagementDAOImpl();
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			
			  PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			  ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
			// ConvertUtils.register(new DateConverter(null), Date.class);
			  //ConvertUtilsBean convertUtilsBean = BeanUtilsBean.getInstance().getConvertUtils();
			  //convertUtilsBean.register(false, true, -1);
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			
			int poolID = poolIdMakerVO.getPoolID();
			
			poolIdMakerVO.setCurrentPageLink(currentPageLink);
			logger.info("poolID;-"+poolID);
			poolIdMakerVO.setMakerID(userobj.getUserId());
			
			String temp = String.valueOf(session.getAttribute("viewer"));
			if(temp!=null){
				if(temp.equalsIgnoreCase("viewer")){
				poolIdMakerVO.setTemp("viewer");
			}
			}
			ArrayList<PoolIdMakerVO> authorPoolIdSavedList = dao.savedPoolIdAuthorData(poolIdMakerVO,poolID);			
			session.setAttribute("authorPoolIdSavedList", authorPoolIdSavedList);
			
			/*ArrayList mainList  = dao.getAuthorPoolData(poolIdMakerVO,poolID);
			session.setAttribute("myList", mainList);*/
			
			
			Iterator iterator =  authorPoolIdSavedList.iterator();
			while (iterator.hasNext()) {
				PoolIdMakerVO object = (PoolIdMakerVO) iterator.next();
				String s = object.getCreditEnhancement();
				String s1 = object.getMultipleInvestor();
				if(s.equals("Y")){
					session.setAttribute("credit", s);
				}
				if(s1.equals("Y")){
					session.setAttribute("multi", s1);
				}
			}
			session.setAttribute("temp", poolID);
			session.setAttribute("poolIdAuthor", "poolIdAuthor");
			session.setAttribute("poolID", poolID);
//			logger.info("mainList    Size:---"+mainList.size());
//			if((authorPoolIdSavedList.size())==0)
//			{
//				request.setAttribute("datalist","datalist");
//			}
			logger.info("authorPoolIdSavedList    Size:---"+authorPoolIdSavedList.size());
			/*String LoanId[]= new String[mainList.size()];
			PoolIdMakerVO vo1 = new PoolIdMakerVO();
			for(int i=0;i<mainList.size();i++){
			vo1=(PoolIdMakerVO) mainList.get(i);
			
			logger.info("check getLoanID"+ vo1.getLoanID());
			LoanId[i]=(CommonFunction.checkNull(vo1.getLoanID()));
			logger.info("check LoanId"+ LoanId[i]);
			
			}*/
			//session.setAttribute("LoanId",LoanId);
//			logger.info("check session--"+ LoanId);
			
			if(!CommonFunction.checkNull(request.getParameter("d-446528-p")).equalsIgnoreCase("")){
				return mapping.findForward("poolIdAuthorShowPageNxt");
			}else{
				return mapping.findForward("poolIdAuthorShowData");			
			}
			
		
		}
	 public ActionForward authorScreen(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		    logger.info("authorScreen");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info(" in authorScreen method of PoolIdAuthorProcessAction action the session is out----------------");
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

			return mapping.findForward("authorScreen");
		}
	 
	 public ActionForward onSavePoolIdAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {

			    logger.info("in onSavePoolIdAuthor..........");
			    
			    HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String authorID ="";
				String businessDate ="";
				int companyId =0;
				if(userobj!=null){
					authorID = userobj.getUserId();
						businessDate=userobj.getBusinessdate();
						companyId=userobj.getCompanyId();
				}else{
					logger.info(" in onSavePoolIdAuthor method of PoolIdAuthorProcessAction action the session is out----------------");
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
				
				DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			    PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
				
				//CreditManagementDAO dao = new CreditManagementDAOImpl();  
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());

				logger.info("authorID"+authorID);
				poolIdMakerVO.setAuthorID(authorID);
				poolIdMakerVO.setAuthorDate(DateFormator.dmyToYMD(businessDate));
//				String pool=request.getParameter("poolID");
//				int poolID = Integer.parseInt(pool);
//				logger.info("onSaveOfPoolIdAuthor poolID;-"+poolID);
//				poolIdMakerVO.setPoolID(poolID);
				
				 String result="";
				 
				 result=dao.onSaveOfPoolIdAuthor(poolIdMakerVO,companyId);
				 
				 logger.info("Out put get from DAOImpl  result  :  "+result);				  
				 if(CommonFunction.checkNull(result).trim().equalsIgnoreCase("S"))
				 	 request.setAttribute("msg","S");									
				 else
				 {
					 request.setAttribute("msg","E");
					 request.setAttribute("massage",result);
				 }
						
			return mapping.findForward("poolIdAuthor");
			}        
			 


}
