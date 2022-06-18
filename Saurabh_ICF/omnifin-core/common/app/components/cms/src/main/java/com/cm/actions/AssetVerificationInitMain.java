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
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class AssetVerificationInitMain extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AssetVerificationInitMain.class.getName());	 
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
					logger.info("here in saveAssetRepCap method of  AssetVerificationInitMain action the session is out----------------");
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
	            ArrayList arrList = dao.repCapAsset(assetVerificationVO,assetVerificationID,makerID);
				
			    boolean status = dao.saveAssetRepCap(assetVerificationVO,assetVerificationID);
			    if(status){
			    	
			    	request.setAttribute("dss", "S");
			    	request.setAttribute("assetId", assetVerificationID);
			    	
			    }else{
			    	request.setAttribute("dss", "N");	
			    }
			      
	            request.setAttribute("list", arrList);
			    return mapping.getInputForward();
		}
	 
	 public ActionForward forwardAssetRepCap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in forwardAssetRepCap"); 
			    
			    HttpSession session =  request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID ="";
				String bDate ="";
				if(userobj!=null){
					makerID = userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in forwardAssetRepCap method of AssetVerificationInitMain action the session is out----------------");
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
	            ArrayList arrList = dao.repCapAsset(assetVerificationVO,assetVerificationID,makerID);
  
	            String check ="";
	            check=ConnectionDAO.singleReturn("select ASSET_LOCATION from cr_asset_verification_dtl where ASSET_VERIFICATION_ID='"+assetVerificationID+"'");
	            
				ArrayList fList =new ArrayList();
				fList.add(assetVerificationVO);
				
				logger.info("check---------------"+check);
	            
	            if(check != null && !check.equalsIgnoreCase(""))
	            {
			    boolean status = dao.forwardAssetRepCap(assetVerificationVO,assetVerificationID);
			    if(status){
			    	
			    	request.setAttribute("dss", "F");
			    	
			    }else{
			    	request.setAttribute("dss", "N");
			    }
	            }
	            else
	            {
	            	request.setAttribute("dss", "M");	
	            				    	
	            }
	            request.setAttribute("fList", fList);
	            request.setAttribute("list", arrList);
		    	return mapping.getInputForward();
		}

}
