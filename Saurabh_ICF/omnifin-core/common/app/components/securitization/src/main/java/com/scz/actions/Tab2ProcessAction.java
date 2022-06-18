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

import com.connect.DaoImplInstanceFactory;
import com.login.roleManager.UserObject;
import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.scz.vo.Tab2VO;

	public class Tab2ProcessAction extends DispatchAction {
		private static final Logger logger = Logger.getLogger(PoolIdMakerProcessAction.class.getName());

		public static ActionForward saveTab2Data(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

					logger.info(" In the newPoolIdMaker-");
					HttpSession session1 = request.getSession();
					UserObject userobj=(UserObject)session1.getAttribute("userobject");
					if(userobj==null){
						logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					
					DynaValidatorForm Tab2ProcessActionForm = (DynaValidatorForm)form;
					Tab2VO tab2VO = new Tab2VO();
					org.apache.commons.beanutils.BeanUtils.copyProperties(tab2VO, Tab2ProcessActionForm);
					
					Session session = HibernateSessionFactory.currentSession();
					Transaction transaction = session.beginTransaction();
					session.save(tab2VO);
					transaction.commit();
				
					// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					PoolIDDAO dao=new PoolIDDAOImpl();
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
					  request.setAttribute("temp", "temp");
					  request.setAttribute("poolNo", poolNo);
					  request.setAttribute("gridList", mainList);
					  request.setAttribute("mssg", "Data Save Successfully");
					return mapping.findForward("saveTab2Data");
			}
		
		public static ActionForward tab2OpenAction(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			logger.info("Inside tab2OpenAction method .....");
			
			Session session = HibernateSessionFactory.currentSession();
			//Transaction transaction = session.beginTransaction();
			
			String id=request.getParameter("id");
			Criteria criteria =  session.createCriteria(Tab2VO.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(id)));
			List updateListData = criteria.list();
			
			Tab2VO tab2VO = new Tab2VO();
			// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO dao=new PoolIDDAOImpl();
			logger.info("Implementation class: "+dao.getClass());
			HttpSession session1 = request.getSession();
			String temp = String.valueOf(session1.getAttribute("temp"));
			int poolNo=0;
			if(temp==null){
				poolNo = Integer.parseInt(dao.getPoolNo());
			}else{
				poolNo=Integer.parseInt(temp.trim());
			}
			
			ArrayList mainList = getGrid(poolNo);
			
			  HibernateSessionFactory.closeSession();
			  request.setAttribute("temp", "temp");
			  request.setAttribute("updateList", updateListData);
			  request.setAttribute("poolNo", poolNo);
			  request.setAttribute("gridList", mainList);
			
			return mapping.findForward("saveTab2Data");
		}
		public static ActionForward updateTab2Data(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

					logger.info(" In the newPoolIdMaker-");
					HttpSession session1 = request.getSession();
					UserObject userobj=(UserObject)session1.getAttribute("userobject");
					if(userobj==null){
						logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					
					DynaValidatorForm Tab2ProcessActionForm = (DynaValidatorForm)form;
					Tab2VO tab2VO = new Tab2VO();
					org.apache.commons.beanutils.BeanUtils.copyProperties(tab2VO, Tab2ProcessActionForm);
					
					int hid = tab2VO.getHid();
					tab2VO.setId(hid);
					tab2VO.setHid(0);
					Session session = HibernateSessionFactory.currentSession();
					Transaction transaction = session.beginTransaction();
					session.saveOrUpdate(tab2VO);
					transaction.commit();
				
					// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					PoolIDDAO dao=new PoolIDDAOImpl();
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
					  request.setAttribute("temp", "temp");
					  request.setAttribute("poolNo", poolNo);
					  request.setAttribute("gridList", mainList);
					  request.setAttribute("mssg", "Data Modify Successfully");
					return mapping.findForward("saveTab2Data");
			}
		
		public static ActionForward deleteTab2Data(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {

					logger.info(" In the newPoolIdMaker-");
					HttpSession session1 = request.getSession();
					UserObject userobj=(UserObject)session1.getAttribute("userobject");
					if(userobj==null){
						logger.info(" in newPoolIdMaker method of PoolIdMakerProcessAction action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					
					DynaValidatorForm Tab2ProcessActionForm = (DynaValidatorForm)form;
					Tab2VO tab2VO = new Tab2VO();
					org.apache.commons.beanutils.BeanUtils.copyProperties(tab2VO, Tab2ProcessActionForm);
					
					int hid = tab2VO.getHid();
					tab2VO.setId(hid);
					Session session = HibernateSessionFactory.currentSession();
					Transaction transaction = session.beginTransaction();
					session.delete(tab2VO);
					transaction.commit();
				
					// PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
					PoolIDDAO dao=new PoolIDDAOImpl();
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
					request.setAttribute("temp", "temp");
					request.setAttribute("gridList", mainList);
					request.setAttribute("mssg", "Data Delete Successfully");
					return mapping.findForward("saveTab2Data");
			}
		
		public static ArrayList getGrid(int poolId){
			Session session = HibernateSessionFactory.currentSession();
			//Transaction transaction = session.beginTransaction();
			Tab2VO tab2VO = new Tab2VO();
			
			Criteria criteria =  session.createCriteria(Tab2VO.class);
			criteria.add(Restrictions.eq("poolID", poolId));
			criteria.addOrder(Order.desc("poolID"));
			  List list = criteria.list();
			  ArrayList mainList = new ArrayList();
			  tab2VO = new Tab2VO();
			  Iterator it = list.iterator();
			  int i=1;
			  tab2VO.setTotalRecord(list.size());
			  while(it.hasNext()){
				  Tab2VO ob = (Tab2VO) it.next();
				  tab2VO.setSrNo("<a href=Tab2ProcessAction.do?method=tab2OpenAction&id="+ob.getId()+">"+i+"</a>");
				  tab2VO.setPoolID(ob.getPoolID());
				  tab2VO.setLbxinstituteID(ob.getLbxinstituteID());
				  tab2VO.setInvestmentRatio(ob.getInvestmentRatio());
				  tab2VO.setInterestRate(ob.getInterestRate());
				  tab2VO.setDistributionPriority(ob.getDistributionPriority());
				  i++;
				  mainList.add(tab2VO);
				  tab2VO = new Tab2VO();
			  }
			  HibernateSessionFactory.closeSession();
			return mainList;
		}
		
	}
