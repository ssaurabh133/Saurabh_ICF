package com.scz.actions;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.dao.PoolIdAddEditDAO;
import com.scz.daoImplMYSQL.PoolIdAddEditDAOImpl;
import com.scz.vo.PoolIdMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.poolIdUploadButFlagVO;

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
					
					/*session.removeAttribute("authorPoolIdSavedList");
					session.removeAttribute("List");
					session.removeAttribute("poolID");*/
				
					// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					PoolIDDAO dao= new PoolIDDAOImpl();
					String businessDate = userobj.getBusinessdate();
					
					String cutoffDate = dao.getCutOffDate(businessDate);
					
					logger.info("Implementation class: "+dao.getClass());
					//For Getting a Pool ID 
					int poolNo = Integer.parseInt(dao.getPoolNo());
					
					
					session.setAttribute("temp", String.valueOf(poolNo));
					session.setAttribute("poolNo", poolNo);
					session.setAttribute("cutoffDate", cutoffDate);
					session.setAttribute("creationDate", userobj.getBusinessdate());
					session.setAttribute("newTab", "newTab");
					session.setAttribute("poolIdMaker", "poolIdMaker");
					session.setAttribute("forNew","");
					return mapping.findForward("success");
			}
	   
	   public ActionForward downloadBankUploadFormat(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		    logger.info("In searchPoolIdMaker  ");
			HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2="";
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
			
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao= new PoolIDDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			
			//calls for proc...
			
			
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			
			in.add(poolIdMakerVO.getPoolID());
			in.add(DateFormator.dmyToYMD(poolIdMakerVO.getAssignDate()));
			in.add(makerID);
			out.add(s1);
			out.add(s2);
			logger.info("IN para " +in + "OUT------------"+out);
			try
			{
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Bank_Upload_Format",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("After calling SCZ_Bank_Upload_Format proc S1 and S2 "+s1+" - "+s2);
			}catch (Exception e) {
				logger.info("An Error..."+e);
			}
			// calls for download format...
			poolIdMakerVO.setMakerID(makerID);
			HSSFWorkbook workbook = dao.getBankUploadFormat(poolIdMakerVO);
			
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=BankUploadFormat.xls");
	            out1=response.getOutputStream();
	        	workbook.write(out1);
	         } 
	        catch (Exception e) 
	        { 
	        	e.printStackTrace();
	        } 
	        finally
	        {  
	        	HibernateSessionFactory.closeSession();
	        } 
			
			return null;	
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
			
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao= new PoolIDDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;
		 	ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
			
			// ConvertUtilsBean convertUtilsBean = BeanUtilsBean.getInstance().getConvertUtils();
			// convertUtilsBean.register(false, true, -1);
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
	
	   public ActionForward getPoolSearchedData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			    throws Exception
			  {
			    logger.info("In getPoolSearchedData  ");

			    HttpSession session = request.getSession();
			    UserObject userobj = (UserObject)session.getAttribute("userobject");
			    String strFlag = "";

			    if (userobj == null) {
			      logger.info(" in getPoolSearchedData method of PoolIdMakerProcessAction action the session is out----------------");
			      return mapping.findForward("sessionOut");
			    }

			    Object sessionId = session.getAttribute("sessionID");

			    ServletContext context = getServlet().getServletContext();

			    if (sessionId != null)
			    {
			      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
			    }

			    logger.info("strFlag .............. " + strFlag);
			    if (!strFlag.equalsIgnoreCase(""))
			    {
			      if (strFlag.equalsIgnoreCase("sameUserSession"))
			      {
			        context.removeAttribute("msg");
			        context.removeAttribute("msg1");
			      }
			      else if (strFlag.equalsIgnoreCase("BODCheck"))
			      {
			        context.setAttribute("msg", "B");
			      }
			      return mapping.findForward("logout");
			    }
			    logger.info("In getPoolSearchedData  ");

			    logger.info("current page link ..........+++++++++:::::>>> " + request.getParameter("d-4008017-p"));

			    int currentPageLink = 0;
			    if ((request.getParameter("d-4008017-p") == null) || (request.getParameter("d-4008017-p").equalsIgnoreCase("0")))
			    {
			      currentPageLink = 1;
			    }
			    else
			    {
			      currentPageLink = Integer.parseInt(request.getParameter("d-4008017-p"));
			    }

			    logger.info("current page link ................ get   ::::::" + request.getParameter("d-4008017-p"));

			    PoolIDDAO dao = new PoolIDDAOImpl();
			    logger.info("Implementation class: " + dao.getClass());

			    DynaValidatorForm PoolIDMakerDynaValidatorForm = (DynaValidatorForm)form;

			    PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			    ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);

			    BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			    int poolID = poolIdMakerVO.getPoolID();
			    poolIdMakerVO.setCurrentPageLink(currentPageLink);
			    logger.info("poolID rudra;-" + poolID);

			    String assignmentPercentage = "";
			    assignmentPercentage = poolIdMakerVO.getAssignmentPercentage();
			    poolIdMakerVO.setAssignmentPercentage(assignmentPercentage);
			    request.setAttribute(assignmentPercentage, poolIdMakerVO);
			    logger.info("assignmentPercentage1111111.." + assignmentPercentage);

			    ArrayList poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO, poolID);
			    session.setAttribute("poolIdSavedDataList", poolIdSavedDataList);

			    session.setAttribute("poolNo", Integer.valueOf(poolID));
			    session.getAttribute("assignmentPercentage:::" + assignmentPercentage);

			    Iterator iterator = poolIdSavedDataList.iterator();
			    while (iterator.hasNext()) {
			      PoolIdMakerVO object = (PoolIdMakerVO)iterator.next();
			      String s = object.getCreditEnhancement();
			      String s1 = object.getMultipleInvestor();
			      if (s.equals("Y")) {
			        session.setAttribute("credit", s);
			      }
			      if (s1.equals("Y")) {
			        session.setAttribute("multi", s1);
			      }
			    }

			    logger.info("poolIdMakerListGrid    Size:---" + poolIdSavedDataList.size());

			    String flag = getUploadFlag(poolIdMakerVO.getPoolID(), "P");
			    if (flag.equals("Y")) {
			      session.setAttribute("poolBut", flag);
			    }
			    String flag1 = getUploadFlag(poolIdMakerVO.getPoolID(), "B");
			    if (flag1.equals("Y")) {
			      session.setAttribute("bankBut", flag1);
			    }
			    String flag2 = getUploadFlag(poolIdMakerVO.getPoolID(), "R");
			    if (flag2.equals("Y")) {
			      session.setAttribute("rePayBut", flag2);
			    }

			    session.setAttribute("poolIdMaker", "poolIdMaker");
			    session.setAttribute("temp", Integer.valueOf(poolID));
			    String ss = request.getParameter("aa");
			    logger.info("request.getParameter(aa)----------" + request.getParameter("aa"));

			    if (ss != null) {
			      if (ss.equalsIgnoreCase("temp"))
			        return mapping.findForward("submitPoolIdUpload");
			    }
			    else {
			      return mapping.findForward("poolIdMakerShowData");
			    }
			    return mapping.findForward("poolIdMakerShowData");
			  }
	  
	   
	   public ActionForward savePoolIdData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		   logger.info(" In the saveTab1Data-");
			HttpSession session1 = request.getSession();
			UserObject userobj=(UserObject)session1.getAttribute("userobject");
	   
			String makerID=null;
			String businessDate=null;
			if(userobj==null){
				logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}else{
				makerID = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}
			
			PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			
			Session session = HibernateSessionFactory.currentSession();
			
			
			Criteria criteria =  session.createCriteria(PoolIdMakerVO.class).add(Restrictions.eq("poolName", CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim()));
			List<PoolIdMakerVO> list = criteria.list();
			if(list!=null && list.size()>0)
			{
			request.setAttribute("APE", "AlreadyPoolExists");
			HibernateSessionFactory.closeSession();
			return mapping.findForward("submitPoolIdUpload");
			}
			else{
				String assignDate=poolIdMakerVO.getAssignDate();
				String cutOffDate=poolIdMakerVO.getCutOffDate();
				String poolCreationDate =poolIdMakerVO.getPoolCreationDate();
				
				poolIdMakerVO.setAssignDate1(DateFormator.dmyToSQL(assignDate));
				poolIdMakerVO.setCutOffDate1(DateFormator.dmyToSQL(cutOffDate));
				poolIdMakerVO.setPoolCreationDate1(DateFormator.dmyToSQL(poolCreationDate));
				
				poolIdMakerVO.setRecStatus("P");
				poolIdMakerVO.setMakerID(makerID);
				poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
				poolIdMakerVO.setAuthorDate1(DateFormator.dmyToSQL(businessDate));
				  String assignmentPercentage = "";
				    assignmentPercentage = poolIdMakerVO.getAssignmentPercentage();
				    poolIdMakerVO.setAssignmentPercentage(assignmentPercentage);
				    logger.info("assignmentPercentage:....2" + assignmentPercentage);

				
				//added by seema
				/*String assignmentPercentage="";
			    assignmentPercentage = poolIdMakerVO.getAssignmentPercentage();
				poolIdMakerVO.setAssignmentPercentage(assignmentPercentage);	//seema
				logger.info("assignmentPercentage:....2" + assignmentPercentage);*/
				//end
				Transaction transaction = session.beginTransaction();
				session.save(poolIdMakerVO);
				transaction.commit();
			}
			
			
			List poolIdDatalist = getSavedPoolIdData(poolIdMakerVO.getPoolID());
			Iterator iterator =  poolIdDatalist.iterator();
			while (iterator.hasNext()) {
				PoolIdMakerVO object = (PoolIdMakerVO) iterator.next();
				String s = object.getCreditEnhancement();
				String s1 = object.getMultipleInvestor();
				if(s.equals("Y")){
					session1.setAttribute("credit", s);
				}
				
				if(s1.equals("Y")){
					session1.setAttribute("multi", s1);
				}
				
			}
			HibernateSessionFactory.closeSession();
			session1.removeAttribute("newTab");
			session1.setAttribute("poolIdSavedDataList", poolIdDatalist);
			request.setAttribute("msg", "msg");
		   return mapping.findForward("submitPoolIdUpload");	
	   }
	   
	   public ActionForward updatePoolIdData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		   logger.info(" In the saveTab1Data-");
			HttpSession session1 = request.getSession();
			UserObject userobj=(UserObject)session1.getAttribute("userobject");
			
			String makerID=null;
			String businessDate=null;
			if(userobj==null){
				logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}else{
				makerID = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}
			
			PoolIdMakerVO poolIdMakerVO = new PoolIdMakerVO();
			DynaValidatorForm PoolIDMakerDynaValidatorForm=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolIdMakerVO, PoolIDMakerDynaValidatorForm);
			
			
			String assignDate=poolIdMakerVO.getAssignDate();
			String cutOffDate=poolIdMakerVO.getCutOffDate();
			String poolCreationDate =poolIdMakerVO.getPoolCreationDate();
			   String assignmentPercentage = "";
			    assignmentPercentage = poolIdMakerVO.getAssignmentPercentage();
			    poolIdMakerVO.setAssignmentPercentage(assignmentPercentage);
			    logger.info("assignmentPercentage:....3" + assignmentPercentage);
			
		
			poolIdMakerVO.setAssignDate1(DateFormator.dmyToSQL(assignDate));
			poolIdMakerVO.setCutOffDate1(DateFormator.dmyToSQL(cutOffDate));
			poolIdMakerVO.setPoolCreationDate1(DateFormator.dmyToSQL(poolCreationDate));
			
			poolIdMakerVO.setRecStatus("P");
			poolIdMakerVO.setMakerID(makerID);
			poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
			poolIdMakerVO.setAuthorDate1(DateFormator.dmyToSQL(businessDate));
			/*poolIdMakerVO.setAssignmentPercentage(assignmentPercentage);	//seema
			logger.info("assignmentPercentage:::::" + poolIdMakerVO.getAssignmentPercentage());*/
			
			
			
			String crFlag=poolIdMakerVO.getCreditEnhancement();
			String mulInvstr=poolIdMakerVO.getMultipleInvestor();
			
			if(crFlag.equalsIgnoreCase("ON")){
				poolIdMakerVO.setCreditEnhancement("Y");
			}else{
				poolIdMakerVO.setCreditEnhancement("N");
			}
			if(mulInvstr.equalsIgnoreCase("on")){
				poolIdMakerVO.setMultipleInvestor("Y");
			}else{
				poolIdMakerVO.setMultipleInvestor("N");
			}
			
			
			poolIdMakerVO.setPoolID(poolIdMakerVO.getHid());
			logger.info("Id Is ..."+ poolIdMakerVO.getHid()+"   poolIdMakerVO.getPoolID():   "+poolIdMakerVO.getPoolID());
			Transaction transaction = null;
		    Session session = HibernateSessionFactory.currentSession();
			
			
			Criteria criteria =  session.createCriteria(PoolIdMakerVO.class).add(Restrictions.eq("poolID", poolIdMakerVO.getPoolID()));
			List<PoolIdMakerVO> list = criteria.list();
			criteria=null;
			PoolIdMakerVO vo = list==null ? null : list.get(0);
			if(vo!=null)
			{
				if(!CommonFunction.checkNull(vo.getPoolName()).trim().equalsIgnoreCase(CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim()))
				{
					criteria = session.createCriteria(PoolIdMakerVO.class).add(Restrictions.eq("poolName", poolIdMakerVO.getPoolName()));
					List<PoolIdMakerVO> list1 = criteria.list();
					if(list1!=null && list1.size()>0)
					{
					request.setAttribute("APE", "AlreadyPoolExists");
					HibernateSessionFactory.closeSession();
					
					return mapping.findForward("submitPoolIdUpload");
					}
					else{	
						 transaction = session.beginTransaction();
					session.merge(poolIdMakerVO);
					transaction.commit();
					}
					
				}
				else{	
					 transaction = session.beginTransaction();
				session.merge(poolIdMakerVO);
				transaction.commit();
				}
			}
			
			
			
			String cc = poolIdMakerVO.getCreditEnhancement();
			String cc1 = poolIdMakerVO.getMultipleInvestor();
			
			List<PoolIdMakerVO> poolIdDatalist = getSavedPoolIdData(poolIdMakerVO.getPoolID());
			logger.info("poolIdDatalist size "+poolIdDatalist.size());
			PoolIdMakerVO vo1 = new PoolIdMakerVO();
			vo1 = list.get(0);
			
			vo1.setAssignDate(DateFormator.ymdToDmy(vo1.getAssignDate1().toString()));
			vo1.setPoolCreationDate(DateFormator.ymdToDmy(vo1.getPoolCreationDate1().toString()));
			vo1.setCutOffDate(DateFormator.ymdToDmy(vo1.getCutOffDate1().toString()));
			logger.info("   poolID---"+vo1.getPoolID()+"     poolCreationDate---"+vo1.getPoolCreationDate()+"    cutOffDate---"+vo1.getCutOffDate());
			list.set(0, vo1);
			Iterator iterator =  poolIdDatalist.iterator();
			while (iterator.hasNext()) {
				PoolIdMakerVO object = (PoolIdMakerVO) iterator.next();
				String s = object.getCreditEnhancement();
				String s1 = object.getMultipleInvestor();
				
				if(s.equals("Y")){
					session1.setAttribute("credit", s);
			}
				else{
					try{
						// rudra
						session = HibernateSessionFactory.currentSession();
						transaction = session.beginTransaction();
						String hql = "DELETE FROM Tab1VO WHERE poolID= :poolID";
						Query query = session.createQuery(hql);
						query.setParameter("poolID", object.getPoolID());
						query.executeUpdate();
						transaction.commit();
						session1.removeAttribute("credit");
					}catch (Exception e) {
						transaction.rollback();
						logger.info("error msg is..."+e);
	   }
				}
				logger.info("hello"+object.getPoolID());
				if(s1.equals("Y")){
					session1.setAttribute("multi", s1);
				}
				else{
					try{
						session = HibernateSessionFactory.currentSession();
						transaction = session.beginTransaction();
						String hql = "DELETE FROM Tab2VO WHERE poolID=:poolID";
						Query query = session.createQuery(hql);
						query.setParameter("poolID", object.getPoolID());
						query.executeUpdate();
						transaction.commit();
						session1.removeAttribute("multi");
					}catch (Exception e) {
						transaction.rollback();
						logger.info("error msg is..."+e);
					}
				}
			}

			HibernateSessionFactory.closeSession();
	   
			session1.removeAttribute("newTab");
			
			session1.setAttribute("poolIdSavedDataList", list);
			
			request.setAttribute("msg", "msg");
		   return mapping.findForward("submitPoolIdUpload");	
	   }
	   
	   public static List getSavedPoolIdData(int id){
		  logger.info("getSavedPoolIdData--------");
		 
   			Session session = HibernateSessionFactory.currentSession();
			//Transaction transaction = session.beginTransaction();
			Criteria criteria =  session.createCriteria(PoolIdMakerVO.class);
			criteria.add(Restrictions.eq("poolID",id));
			List<PoolIdMakerVO> list = criteria.list();
			logger.info("poolIdDatalist size "+list.size());
			PoolIdMakerVO vo2 = new PoolIdMakerVO();
			vo2 = list.get(0);
			logger.info("   poolID---"+vo2.getPoolID()+"     poolCreationDate---"+vo2.getPoolCreationDate1()+"    cutOffDate---"+vo2.getCutOffDate1());
			logger.info("   poolID---"+vo2.getPoolID()+"     poolCreationDate---"+vo2.getPoolCreationDate()+"    cutOffDate---"+vo2.getCutOffDate());
			return list;
		   
	   }
	   
	   public static String getUploadFlag(int pool_id, String upload_type){
			String flag="N";  
		    Session session = HibernateSessionFactory.currentSession();
			//Transaction transaction = session.beginTransaction();
			Query qry = session.createQuery("select p.upload_flag from poolIdUploadButFlagVO p where pool_id=:pool_id and upload_type=:upload_type");
			qry.setParameter("pool_id", pool_id);
			qry.setParameter("upload_type", upload_type);
	   
			List list =qry.list();
			Iterator it=list.iterator();
			while (it.hasNext()) {
				flag = it.next().toString();
			}
			HibernateSessionFactory.closeSession();
		   return flag;
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
			int poolID = 0;
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
		
      	 //  PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		   PoolIDDAO dao= new PoolIDDAOImpl();
		   
      	   logger.info("Inside Processing Action of submitPoolIdUpload");
			poolIdMakerVO.setMakerID(makerID);
			poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
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
						// in this method we have to process with KTR file Rudra
						status=dao.uploadCsv_Securitization(request,response,poolIdMakerVO.getDocFile().getFileName(),poolIdMakerVO);	
			     		
			     	    logger.info("status-----------------------"+status);
						
						// Calls the procedure...
						
						//procStatus=dao.saveSecuritization(request,poolIdMakerVO);
						
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
						   
	    	logger.info("getting main data list ");
						    ArrayList mainList  = dao.getPoolData(poolID,poolIdMakerVO);
						   
						    int sizeList=mainList.size();
						    					     
							session.setAttribute("mainList", mainList);
							request.setAttribute("poolNo", poolID);
							request.setAttribute("poolIdMaker", "poolIdMaker");
							session.setAttribute("temp", poolID);
							makerID =null;
							businessDate =null;
							strFlag=null;
							poolID = 0;
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
			// PoolIdAddEditDAO dao=(PoolIdAddEditDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIdAddEditDAO.IDENTITY);
			PoolIdAddEditDAO dao= new PoolIdAddEditDAOImpl();
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
				poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
				String alertMsg ="";
				int poolId = Integer.parseInt(request.getParameter("poolID"));
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
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
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
					poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
					String alertMsg ="";
					int poolId = Integer.parseInt(request.getParameter("poolID"));
					poolIdMakerVO.setPoolID(poolId);
					poolIdMakerVO.setCurrentPageLink(currentPageLink);
					logger.info(" In the poolId----------"+poolId);
					
					boolean deletePoolList = dao.deletePoolID(poolId);
					
					logger.info("deletePoolList:--"+deletePoolList);
					request.setAttribute("poolIdMaker", "poolIdMaker");	
					request.setAttribute("poolNo", poolId);
					if(deletePoolList){
						request.setAttribute("deleteOk", "Y");
						session.removeAttribute("poolIdSavedDataList");
						 session.removeAttribute("poolBut");
						    session.removeAttribute("bankBut");
						    session.removeAttribute("rePayBut");
						    session.removeAttribute("credit");
						    session.removeAttribute("multi");
					}
					
					/*ArrayList<PoolIdMakerVO> poolIdSavedDataList = dao.savedPoolIdMakerData(poolIdMakerVO,poolId);			
					request.setAttribute("poolIdSavedDataList", poolIdSavedDataList);*/
					return mapping.findForward("deletePoolID");
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
			
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao= new PoolIDDAOImpl();
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
					poolIdMakerVO.setMakerDate1(DateFormator.dmyToSQL(businessDate));
				   
					int poolID = Integer.parseInt(request.getParameter("poolID"));
					logger.info("poolID--"+poolID);
					
					// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					PoolIDDAO dao= new PoolIDDAOImpl();
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
			int poolID = Integer.parseInt(request.getParameter("poolID"));
			
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao= new PoolIDDAOImpl();
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
		
			int poolID = Integer.parseInt(request.getParameter("poolID"));
		    // PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao= new PoolIDDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			
			ArrayList list=dao.getUploadDetails(poolID);
			
			Iterator it=list.iterator();
			
			boolean pBool=false/*, rBool=false, bBool=false*/;
			while (it.hasNext()) {
				poolIdUploadButFlagVO object = (poolIdUploadButFlagVO) it.next();
				String flag = object.getUpload_type();
				if(flag.equalsIgnoreCase("P")){
					pBool=true;
				}//discuss disscuss with yogesh sir   
				/*else if(flag.equalsIgnoreCase("R")){
					rBool=true;
				}else{
					bBool=true;
				}*/
			}
			String msg="";
			if(!pBool){
				msg=msg+"Please Upload Pool Data.";
			}
			/*if(!rBool){
				msg=msg+" Please Upload Re-Payment Data.";
			}
			if(!bBool){
				msg=msg+" Please Upload Bank Upload Data.";
			}*/
			logger.info("Message is "+ msg);
			if(!msg.equalsIgnoreCase("")){
				request.setAttribute("msg1", msg);
				return mapping.findForward("submitPoolIdUpload");
			}
			
			boolean status = dao.forwardPoolIdMaker(poolID);
			
		    if(status){
		    	request.setAttribute("alertMsg", "Y");
		    }else{
		    	request.setAttribute("alertMsg", "N");
		    }
		    ArrayList poolIdSavedDataList=new ArrayList();
		    request.setAttribute("poolIdSavedDataList",poolIdSavedDataList);
		    ArrayList myList=new ArrayList();
		    request.setAttribute("myList",myList);
		    session.removeAttribute("poolBut");
		    session.removeAttribute("bankBut");
		    session.removeAttribute("rePayBut");
		    session.removeAttribute("credit");
		    session.removeAttribute("multi");
		    session.removeAttribute("poolIdSavedDataList");
			logger.info("alertMsg"+request.getAttribute("alertMsg"));
			return mapping.findForward("forwardPoolId");
		}

}	