package com.scz.actions;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.scz.actions.PoolIdMakerProcessAction;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.dao.DownloadPoolUploadDataDAO;
import com.scz.daoHibImpl.DownloadPoolUploadDataHibImplService;
import com.scz.vo.DownloadPoolDataVO;

public class DownloadPoolDataProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PoolIdMakerProcessAction.class.getName());
	
	public ActionForward downloadPoolUploads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	
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
		
		DynaValidatorForm DownloadPoolDataProcessActionForm = (DynaValidatorForm)form;
		DownloadPoolDataVO downloadPoolVO = new DownloadPoolDataVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(downloadPoolVO, DownloadPoolDataProcessActionForm);
		
		String type = downloadPoolVO.getDownloadType();
		DownloadPoolUploadDataDAO downloadPoolHib = new DownloadPoolUploadDataHibImplService();
		if(type!=null && type.equalsIgnoreCase("P")){
			HSSFWorkbook workbook = downloadPoolHib.downloadPoolData(downloadPoolVO);
			
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=PoolUpload.xls");
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
		}else if(type!=null && type.equalsIgnoreCase("B")){
			HSSFWorkbook workbook = downloadPoolHib.downloadBankData(downloadPoolVO);
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=BankUpload.xls");
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
		}
		else if(type!=null && type.equalsIgnoreCase("R")){
			HSSFWorkbook workbook = downloadPoolHib.downloadRePayData(downloadPoolVO);
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=RepaymentUpload.xls");
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
		}
		
		return null;
	}
}
