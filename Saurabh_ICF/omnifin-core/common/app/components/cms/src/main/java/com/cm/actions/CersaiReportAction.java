/**
 * 
 */
package com.cm.actions;

/**
 * @author pranaya.gajpure
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.struts.upload.FormFile;

import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
//import com.cm.vo.InterestWorkingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.ConnectionDAO;


public class CersaiReportAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(GenerateReportDispatchAction.class.getName());	
	
	public ActionForward cersaiUpload(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyName="";
		String bDate="";
		String userId = "";
		if(userobj==null){
			logger.info("In generateInterestWorking() method of InterestWorkingAction, the session is out----");
			return mapping.findForward("sessionOut");
		}
		else{
			companyName=userobj.getConpanyName();
			bDate=userobj.getBusinessdate();
			userId=userobj.getUserId();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null){
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		logger.info("strFlag------------"+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		
		try{
			AssetForCMVO cersaiVO = new AssetForCMVO();
			DynaValidatorForm CersaiReportDynaValidForm = (DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(cersaiVO,CersaiReportDynaValidForm);
			
			FormFile ffl=cersaiVO.getCersaiFile();
			String fileName=ffl.getFileName();
			


		      if ((fileName.contains("CERSAI_entry")) || (fileName.contains("Risk_Category")) || (fileName.contains("CGTMSE_Upload")))
		      {
		        if (fileName.contains("CERSAI_entry")) {
		          fileName = "CERSAI_entry.xls";
		        } else if (fileName.contains("Risk_Category")) {
		          fileName = "Risk_Category.xls";
		        } else if (fileName.contains("CGTMSE_Upload")) {
		          fileName = "CGTMSE_Upload.xls";
		        } else {
		          request.setAttribute("CheckFileName", "CheckFileName");
		          return mapping.findForward("success");
		        }
				assetInsuranceDAO dao =(assetInsuranceDAO) DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY) ;
				boolean uploadStatus = dao.docUploadExcel(request,ffl);
				boolean vstatus=dao.uploadCersaiReport(fileName,userId);
				if(vstatus) 
					request.setAttribute("vstatus","vstatus");
				else
					request.setAttribute("sstatus","sstatus");
			}else{
				request.setAttribute("fails","fails");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return mapping.findForward("success");
	}
	
	public ActionForward cersaiDownload(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyName="";
		String bDate="";
		if(userobj==null){
			logger.info("In generateInterestWorking() method of InterestWorkingAction, the session is out----");
			return mapping.findForward("sessionOut");
		}
		else{
			companyName=userobj.getConpanyName();
			bDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null){
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		logger.info("strFlag------------"+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		
		try{
			String fileName="CERSAI_entry.xls";
			String UploadType = CommonFunction.checkNull((String)request.getParameter("fileName"));
			if(UploadType.equalsIgnoreCase("C")){
				fileName="CERSAI_entry.xls";
			}else if(UploadType.equalsIgnoreCase("K")){
				fileName="Risk_Category.xls";
			} else if(UploadType.equalsIgnoreCase("CGTMSE")){
				fileName="CGTMSE_Upload.xls";
			}
			String vQuery1="select parameter_value from parameter_mst where parameter_key='CERSAI_UPLOAD_PATH'";
			String vPath=ConnectionDAO.singleReturn(vQuery1);
			vQuery1=null;
			String strPath=vPath+"/"+fileName;
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition","attachement;filename="+fileName+"");
			
			FileInputStream fin=new FileInputStream(new File(strPath));
			ServletOutputStream out=response.getOutputStream();
			
			byte[] outputByte=new byte[4096];

			while(fin.read(outputByte,0,4096)!=-1){
				out.write(outputByte,0,4096);
			}
			
			fin.close();
			out.close();
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}
}
