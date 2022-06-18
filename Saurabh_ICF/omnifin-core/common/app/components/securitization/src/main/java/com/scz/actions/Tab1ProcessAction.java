package com.scz.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.vo.Tab1VO;
import com.connect.DaoImplInstanceFactory;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.roleManager.UserObject;

public class Tab1ProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(Tab1ProcessAction.class.getName());

	public static ActionForward saveTab1Data(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

				logger.info(" In the saveTab1Data-");
				HttpSession session1 = request.getSession();
				UserObject userobj=(UserObject)session1.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				DynaValidatorForm Tab1ProcessActionForm = (DynaValidatorForm)form;
				Tab1VO tab1VO = new Tab1VO();
				org.apache.commons.beanutils.BeanUtils.copyProperties(tab1VO, Tab1ProcessActionForm);
				
				Session session = HibernateSessionFactory.currentSession();
				Transaction transaction = session.beginTransaction();
				session.save(tab1VO);
				transaction.commit();
				
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				String temp = String.valueOf(session1.getAttribute("temp"));
				int poolNo=0;
				if(temp==null){
					poolNo = Integer.parseInt(dao.getPoolNo());
				}else{
					poolNo=Integer.parseInt(temp.trim());
				}
				
				ArrayList mainList = getGrid(poolNo);
				request.setAttribute("poolNo", poolNo);
				request.setAttribute("gridList", mainList);
				request.setAttribute("mssg", "Data Saved Successfully");
				
				return mapping.findForward("saveTab1Data");
		}
	
	public static ActionForward deleteTab1Data(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the deleteTab1Data-");
				HttpSession session1 = request.getSession();
				UserObject userobj=(UserObject)session1.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				DynaValidatorForm Tab1ProcessActionForm = (DynaValidatorForm)form;
				Tab1VO tab1VO = new Tab1VO();
				org.apache.commons.beanutils.BeanUtils.copyProperties(tab1VO, Tab1ProcessActionForm);
				
				int hid = tab1VO.getHid();
				tab1VO.setId(hid);
				Session session = HibernateSessionFactory.currentSession();
				Transaction transaction = session.beginTransaction();
				session.delete(tab1VO);
				transaction.commit();
			
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				String temp = String.valueOf(session1.getAttribute("temp"));
				int poolNo=0;
				if(temp==null){
					poolNo = Integer.parseInt(dao.getPoolNo());
				}else{
					poolNo=Integer.parseInt(temp.trim());
				}		
				
				ArrayList mainList = getGrid(poolNo);
				request.setAttribute("poolNo", poolNo);
				request.setAttribute("gridList", mainList);
				request.setAttribute("mssg", "Data Delete Successfully");
				return mapping.findForward("saveTab1Data");
		}
	
	public static ActionForward updateTab1Data(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

				logger.info(" In the updateTab1Data-");
				HttpSession session1 = request.getSession();
				UserObject userobj=(UserObject)session1.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				
				DynaValidatorForm Tab1ProcessActionForm = (DynaValidatorForm)form;
				Tab1VO tab1VO = new Tab1VO();
				org.apache.commons.beanutils.BeanUtils.copyProperties(tab1VO, Tab1ProcessActionForm);
				
				int hid = tab1VO.getHid();
				tab1VO.setId(hid);
				tab1VO.setHid(0);
				Session session = HibernateSessionFactory.currentSession();
				Transaction transaction = session.beginTransaction();
				session.saveOrUpdate(tab1VO);
				transaction.commit();
			
				// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
				PoolIDDAO dao= new PoolIDDAOImpl();
				logger.info("Implementation class: "+dao.getClass());
				String temp = String.valueOf(session1.getAttribute("temp"));
				int poolNo=0;
				if(temp==null){
					poolNo = Integer.parseInt(dao.getPoolNo());
				}else{
					poolNo=Integer.parseInt(temp.trim());
				}
				
				ArrayList mainList = getGrid(poolNo);
				
				  HibernateSessionFactory.closeSession();
				  request.setAttribute("poolNo", poolNo);
				  request.setAttribute("gridList", mainList);
				  request.setAttribute("mssg", "Data Modify Successfully");
				return mapping.findForward("saveTab1Data");
		}
	
	public static ActionForward tab1OpenAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("In the tab1OpenAction.");
		
		Session session = HibernateSessionFactory.currentSession();
		//Transaction transaction = session.beginTransaction();
		
		String id=request.getParameter("id");
		Criteria criteria =  session.createCriteria(Tab1VO.class);
		criteria.add(Restrictions.eq("id", Integer.parseInt(id)));
		List updateListData = criteria.list();
		
		// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		PoolIDDAO dao= new PoolIDDAOImpl();
		logger.info("Implementation class: "+dao.getClass());
		HttpSession session1 = request.getSession();
		String temp = String.valueOf(session1.getAttribute("temp"));
		int poolNo=0;
		if(temp==null){
			poolNo = Integer.parseInt(dao.getPoolNo());
		}else{
			poolNo=Integer.parseInt(temp.trim());
		}
		
		ArrayList gridList = getGrid(poolNo);
		
		  HibernateSessionFactory.closeSession();
		  request.setAttribute("updateList", updateListData);
		  request.setAttribute("poolNo", poolNo);
		  request.setAttribute("gridList", gridList);
		  
		return mapping.findForward("saveTab1Data");
	}
	
	public static ArrayList getGrid(int poolId){
		logger.info("In the getGrid for finding the main grid. ");
		Session session = HibernateSessionFactory.currentSession();
		//Transaction transaction = session.beginTransaction();
		Tab1VO tab1VO = new Tab1VO();
		
		Criteria criteria =  session.createCriteria(Tab1VO.class);
		criteria.add(Restrictions.eq("poolID", poolId));
		criteria.addOrder(Order.desc("poolID"));
		  List list = criteria.list();
		  ArrayList mainList = new ArrayList();
		  Iterator it = list.iterator();
		  int i=1;
		  tab1VO.setTotalRecord(list.size());
		  while(it.hasNext()){
			  Tab1VO ob = (Tab1VO) it.next();
			  tab1VO.setSrNo("<a href=Tab1ProcessAction.do?method=tab1OpenAction&id="+ob.getId()+">"+i+"</a>");
			  tab1VO.setPoolID(ob.getPoolID());
			  tab1VO.setCreditEnhanceType(ob.getCreditEnhanceType());
			  tab1VO.setCreditEnhanceDocRefNo(ob.getCreditEnhanceDocRefNo());
			  tab1VO.setCreditEnhanceAmount(ob.getCreditEnhanceAmount());
			  i++;
			  mainList.add(tab1VO);
			  tab1VO = new Tab1VO();
		  }
		  HibernateSessionFactory.closeSession();
		return mainList;
	}
}
