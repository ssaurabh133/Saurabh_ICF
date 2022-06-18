package com.cp.actions;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.RmDAO;
import com.cp.vo.RmChangeVo;

public class RmChangeDispatchAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(RmChangeDispatchAction.class.getName());
	
	public ActionForward searchRmChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			return mapping.findForward("sessionOut");
		}
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
		RmChangeVo rmChangeVo = new RmChangeVo();
		DynaValidatorForm rmChangeFormBean= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(rmChangeVo, rmChangeFormBean);	
		
		RmDAO rmDao=(RmDAO)DaoImplInstanceFactory.getDaoImplInstance(RmDAO.IDENTITY);
        logger.info("Implementation class: "+rmDao.getClass()); 	// changed by asesh
        //RmDAO rmDao = new RmDAOImpl();
		ArrayList<RmChangeVo> detailList = rmDao.searchRmData(rmChangeVo);
		ArrayList<RmChangeVo> header=new ArrayList();
	    header.add(rmChangeVo);
	    String dealNo=rmChangeVo.getDealNo();
	    String lbxDealNo=rmChangeVo.getLbxDealNo();
	    String customerName =rmChangeVo.getCustomerName();
	    String lbxRM=rmChangeVo.getLbxRM();
	    String rm = rmChangeVo.getRm();
	    
	    request.setAttribute("lbxRM",lbxRM);
	    request.setAttribute("rm",rm);
	    
	    request.setAttribute("dealNo",dealNo);
	    request.setAttribute("lbxDealNo",lbxDealNo);
	    request.setAttribute("customerName",customerName);
	    request.setAttribute("header",header);
		
		request.setAttribute("searchRmList", detailList);
		rmChangeVo=null;
		rmDao=null;
		form.reset(mapping, request);
		return mapping.findForward("searchRmPage");
	}
	
	public ActionForward saveRmChangeDetails(ActionMapping mapping, ActionForm form,			
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			HttpSession session = request.getSession();
		
			ServletContext context = getServlet().getServletContext();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null)
			{
				return mapping.findForward("sessionOut");
			}
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
			String userId=null;
			String bDate=null;
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			DynaValidatorForm rmChangeFormBean= (DynaValidatorForm)form;
			RmChangeVo rmChangeVo = new RmChangeVo();
			rmChangeVo.setMakerId(userId);
			rmChangeVo.setMakerDate(bDate);
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(rmChangeVo, rmChangeFormBean);	
			
			RmDAO rmDao=(RmDAO)DaoImplInstanceFactory.getDaoImplInstance(RmDAO.IDENTITY);
	        logger.info("Implementation class: "+rmDao.getClass()); 	// changed by asesh
			
			String checkbox[] = request.getParameterValues("chkCases");
		
			
			String sms="";
			if(checkbox==null )
			{
				sms="F";
				request.setAttribute("sms",sms);
			}		
			else
			{
	             boolean status = rmDao.insertRmChangeData(rmChangeVo,checkbox);
				
				logger.info("Inside RM Change Action.....displaying status...."+status);
				if(status)
				{
					if(checkbox!=null && !checkbox.equals("")){
						sms="S";
						request.setAttribute("sms",sms);
					}else{
					sms="S";
					request.setAttribute("sms",sms);
					}
				}
				else
				{
					sms="E";
					request.setAttribute("sms",sms);
				}
				
			  //  ArrayList<RmChangeVo> list = rmDao.searchRmMakerAuthor(rmChangeVo, "P");
			   
			    ArrayList<RmChangeVo> header=new ArrayList();
			    header.add(rmChangeVo);
			    String lbxRM=rmChangeVo.getLbxRM();
			    String ro = rmChangeVo.getRo();
			    String lbxRO=rmChangeVo.getLbxRO();
			    String rm = rmChangeVo.getRm();
			    String maker = rmChangeVo.getMaker();
			    String lbxMaker=rmChangeVo.getLbxMaker();
			    String relationshipManager = rmChangeVo.getRelationshipManager();
			    String lbxRelationship = rmChangeVo.getLbxRelationship();
			    String relationshipOfficer = rmChangeVo.getRelationshipOfficer();
			    String lbxRelationshipO = rmChangeVo.getLbxRelationshipO();
			    
			    String showUserDescSearch = rmChangeVo.getShowUserDescSearch();
			    String lbxUserId = rmChangeVo.getLbxUserId();
			    request.setAttribute("lbxRM",lbxRM);
			    request.setAttribute("lbxRO",lbxRO);
			    request.setAttribute("lbxMaker",lbxMaker);
			    request.setAttribute("rm",rm);
			    request.setAttribute("ro",ro);
			    request.setAttribute("maker",maker);
			    request.setAttribute("relationshipManager",relationshipManager);
			    request.setAttribute("lbxRelationship",lbxRelationship);
			    request.setAttribute("relationshipOfficer",relationshipOfficer);
			    request.setAttribute("lbxRelationshipO",lbxRelationshipO);
			    request.setAttribute("showUserDescSearch",showUserDescSearch);
			    request.setAttribute("lbxUserId",lbxUserId);
			    request.setAttribute("header", header);
				//request.setAttribute("searchRmList", list);
			}
						
			//request.setAttribute("sms",sms);
			checkbox=null;
			rmDao=null;
			rmChangeVo=null;
			 form.reset(mapping, request);
			return mapping.findForward("saveRm");
		}	

	public ActionForward forwardRmData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in forwardRmData........(forwardRmData)");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null)
				{
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
				
				RmChangeVo vo = new RmChangeVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
			
				DynaValidatorForm rmChangeFormBean= (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, rmChangeFormBean);

				RmDAO rmDao=(RmDAO)DaoImplInstanceFactory.getDaoImplInstance(RmDAO.IDENTITY);
		        logger.info("Implementation class: "+rmDao.getClass()); 	// changed by asesh
				String checkbox[] = request.getParameterValues("chkCases");
			
				
				boolean status = rmDao.forwardRM(vo,checkbox);
				if(status)
				{
					request.setAttribute("sms","M");
				}
				else
				{
					request.setAttribute("sms","E");
				}
			    
			    vo=null;
			    rmDao=null;
			    checkbox=null;
			    form.reset(mapping, request);
				return mapping.findForward("rmForward");
			}

	public ActionForward searchRmAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			return mapping.findForward("sessionOut");
		}
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
		RmChangeVo rmChangeVo = new RmChangeVo();
		if(userobj!=null)
		{
			rmChangeVo.setMakerId(userobj.getUserId());
			rmChangeVo.setMakerDate(userobj.getBusinessdate());
		}
		DynaValidatorForm rmChangeFormBean= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(rmChangeVo, rmChangeFormBean);	
		
		RmDAO rmDao=(RmDAO)DaoImplInstanceFactory.getDaoImplInstance(RmDAO.IDENTITY);
        logger.info("Implementation class: "+rmDao.getClass()); 	// changed by asesh
		ArrayList<RmChangeVo> detailList = rmDao.searchRmMakerAuthor(rmChangeVo,"F");
		String lbxRM=rmChangeVo.getLbxRM();
	    String rm = rmChangeVo.getRm();
		
	    String dealNo=rmChangeVo.getDealNo();
	    String lbxDealNo = rmChangeVo.getLbxDealNo();
	    String customerName =rmChangeVo.getCustomerName();
	    request.setAttribute("lbxRM",lbxRM);
	    request.setAttribute("rm",rm);
	 
	    request.setAttribute("dealNo",dealNo);
	    request.setAttribute("lbxDealNo", lbxDealNo);
	    request.setAttribute("customerName",customerName);
		request.setAttribute("searchRmAuthor", detailList);
		rmChangeVo=null;
		rmDao=null;
		 form.reset(mapping, request);
		return mapping.findForward("searchAuthor");
	}
	
	public ActionForward saveRmAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				logger.info(" in saveRmAuthor.....");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null)
				{
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
				
				RmChangeVo vo = new RmChangeVo();
				if(userobj!=null)
				{
					vo.setMakerId(userobj.getUserId());
					vo.setMakerDate(userobj.getBusinessdate());
				}
				
				DynaValidatorForm rmChangeFormBean = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(vo, rmChangeFormBean);
				RmDAO leDAO=(RmDAO)DaoImplInstanceFactory.getDaoImplInstance(RmDAO.IDENTITY);
		        logger.info("Implementation class: "+leDAO.getClass()); 	// changed by asesh
				//RmDAO leDAO = new RmDAOImpl();
				String checkbox[] = request.getParameterValues("chkCases");
				String sms="";
				String decesion = vo.getDecison();
				boolean st = leDAO.saveRmChangeAuthor(vo, checkbox);
				
				if(st)
				{
					if(decesion.equalsIgnoreCase("A")){
						request.setAttribute("sms","A");
					}
					else if(decesion.equalsIgnoreCase("P")){
						request.setAttribute("sms","P");
					}
					else{
						request.setAttribute("sms","X");
					}
				}
				else
				{
					sms="E";
					request.setAttribute("sms",sms);
				}
			    
			    vo=null;
			    checkbox=null;
			    leDAO=null;
			    decesion=null;
			    form.reset(mapping, request);
				return mapping.findForward("saveRmAuthor");
			}
}


